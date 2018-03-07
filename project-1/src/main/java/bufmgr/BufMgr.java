package bufmgr;

import java.util.HashMap;
import global.GlobalConst;
import global.Minibase;
import global.Page;
import global.PageId;

/**
 * <h3>Minibase Buffer Manager</h3> The buffer manager reads disk pages into a
 * main memory page as needed. The collection of main memory pages (called
 * frames) used by the buffer manager for this purpose is called the buffer
 * pool. This is just an array of Page objects. The buffer manager is used by
 * access methods, heap files, and relational operators to read, write,
 * allocate, and de-allocate pages.
 */
@SuppressWarnings("unused")
public class BufMgr implements GlobalConst {
	
	/** Actual pool of pages (can be viewed as an array of byte arrays). */
	protected Page[] bufpool;

	/** Array of descriptors, each containing the pin count, dirty status, etc. */
	protected FrameDesc[] frametab;

	/** Maps current page numbers to frames; used for efficient lookups.
	 *  if a frame is not in the map, the page associated with the integer
	 *  is not in the pool.
	 * */

	protected HashMap<Integer, FrameDesc> pagemap;

	/** The replacement policy to use. */
	protected Replacer replacer;

	/**
	 * Constructs a buffer manager with the given settings.
	 *
	 * @param numbufs: number of pages in the buffer pool
	 */

	public BufMgr(int numbufs) {
		// initialize the buffer pool and frame table
		bufpool = new Page[numbufs];
		frametab = new FrameDesc[numbufs];
		for (int i = 0; i < numbufs; i++) {
			bufpool[i] = new Page();
			frametab[i] = new FrameDesc(i);
		}

		// initialize the specialized page map and replacer
		pagemap = new HashMap<Integer, FrameDesc>(numbufs);
		replacer = new Clock(this);
	}

	/**
	 * Allocates a set of new pages, and pins the first one in an appropriate
	 * frame in the buffer pool.
	 *
	 * @param firstpg
	 *            holds the contents of the first page 
	 * @param run_size
	 *            number of new pages to allocate
	 * @return page id of the first new page
	 * @throws IllegalArgumentException
	 *             if PIN_MEMCPY and the page is pinned
	 * @throws IllegalStateException
	 *             if all pages are pinned (i.e. pool exceeded)
	 */
	public PageId newPage(Page firstpg, int run_size) {

		// allocate the run
		PageId firstid = Minibase.DiskManager.allocate_page(run_size);

		// try to pin the first page
		try {pinPage(firstid, firstpg, PIN_MEMCPY);}
		catch (RuntimeException exc) {
			// roll back because pin failed
			for (int i = 0; i < run_size; i++) {
				firstid.pid += 1;
				Minibase.DiskManager.deallocate_page(firstid);
			}
			// re-throw the exception
			throw exc;
		}
		// notify the replacer and return the first new page id
		replacer.newPage(pagemap.get(firstid.pid));
		return firstid;
	}

	/**
	 * Deallocates a single page from disk, freeing it from the pool if needed.
	 * Call Minibase.DiskManager.deallocate_page(pageno) to deallocate the page before return.
	 * 
	 * @param pageno
	 *            identifies the page to remove
	 * @throws IllegalArgumentException
	 *             if the page is pinned
	 */
	public void freePage(PageId pageno) throws IllegalArgumentException {

		if(pagemap.containsKey(pageno.getPID())){
			FrameDesc frame = pagemap.get(pageno.getPID());

			if(frame.pincnt > 0){
				//System.out.println("!!! Freepage requested on page " + pageno.getPID() + " with pincount = " + frame.pincnt);
				throw new IllegalArgumentException("Freepage requested on page " + pageno.getPID() + " with pincount = " + frame.pincnt);
			}
			pagemap.remove(pageno.getPID());
			replacer.freePage(frame);
			frame.reset();
		}

		// deallocates the page
		Minibase.DiskManager.deallocate_page(pageno);

	}

	/**
	 * Pins a disk page into the buffer pool. If the page is already pinned,
	 * this simply increments the pin count. Otherwise, this selects another
	 * page in the pool to replace, flushing the replaced page to disk if 
	 * it is dirty.
	 * 
	 * (If one needs to copy the page from the memory instead of reading from 
	 * the disk, one should set skipRead to PIN_MEMCPY. In this case, the page 
	 * shouldn't be in the buffer pool. Throw an IllegalArgumentException if so. )
	 * 
	 * 
	 * @param pageno
	 *            identifies the page to pin
	 * @param page
	 *            if skipread == PIN_MEMCPY, works as as an input param, holding the contents to be read into the buffer pool
	 *            if skipread == PIN_DISKIO, works as an output param, holding the contents of the pinned page read from the disk
	 * @param skipRead
	 *            PIN_MEMCPY(true) (copy the input page to the buffer pool); PIN_DISKIO(false) (read the page from disk)
	 * @throws IllegalArgumentException
	 *             if PIN_MEMCPY and the page is pinned
	 * @throws IllegalStateException
	 *             if all pages are pinned (i.e. pool exceeded)
	 */
	public void pinPage(PageId pageno, Page page, boolean skipRead) {
	    //System.out.println("Pining page " + pageno.getPID() + " skipRead = " + skipRead);

		FrameDesc frame;

		if((frame = pagemap.get(pageno.getPID())) != null){
			// the page is already in the map
			if(skipRead){
				//System.out.println("!!! Page " + pageno.getPID() + " pinned but skipRead = " + skipRead);
				throw new IllegalArgumentException("Page " + pageno.getPID() + " pinned but skipRead = " + skipRead);
			}

			page.setPage(bufpool[frame.index]);
		}else{
			// the page is not in the map, it means it has to be copied into the pool
			int desc_id = replacer.pickVictim();

			if(desc_id == INVALID_PAGEID){
				//System.out.println("!!! All pages are pinned! :(");
				throw new IllegalStateException("All pages are pinned! :(");
			}

			frame = frametab[desc_id];

			if(frame.dirty){
				flushPage(frame.pageno);
			}

			pagemap.remove(frame.pageno.getPID());
			frame.reset();
			frame.pageno.copyPageId(pageno);

			if(!skipRead){
				Minibase.DiskManager.read_page(frame.pageno, bufpool[frame.index]);
			}
			else{
				bufpool[frame.index].copyPage(page);
			}

			page.setPage(bufpool[frame.index]);
			pagemap.put(pageno.getPID(), frame);

		}

		// notifies the replacer
		replacer.pinPage(frame);
	}

	/**
	 * Unpins a disk page from the buffer pool, decreasing its pin count.
	 * 
	 * @param pageno
	 *            identifies the page to unpin
	 * @param dirty
	 *            UNPIN_DIRTY if the page was modified, UNPIN_CLEAN otherrwise
	 * @throws IllegalArgumentException
	 *             if the page is not present or not pinned
	 */
	public void unpinPage(PageId pageno, boolean dirty) throws IllegalArgumentException {
        //System.out.println("Unpining page " + pageno.getPID());
		FrameDesc frame;
		if((frame = pagemap.get(pageno.getPID())) == null){
			//System.out.println("!!! Unpin page requested for page " + pageno.toString() + ", but page not in map");
			throw new IllegalArgumentException("Unpin page requested for page " + pageno.toString() + ", but page not in map");
		}else if(frame.pincnt == 0){
			//System.out.println("!!! Unpin page requested for page " + pageno.toString() + ", but pincount already 0");
			throw new IllegalArgumentException("Unpin page requested for page " + pageno.toString() + ", but pincount already 0");
		}

		frame.dirty = dirty;
		replacer.unpinPage(frame);
	}

	/**
	 * Immediately writes a page in the buffer pool to disk, if dirty.
	 */
	public void flushPage(PageId pageno) {
		//System.out.println("Flushing page " + pageno.getPID());
		//TODO: we assume the page is already in the map, perhaps we can check first
		FrameDesc frame = pagemap.get(pageno.getPID());
		if(frame.dirty) {
			Minibase.DiskManager.write_page(pageno, bufpool[frame.index]);
		}

	}

	/**
	 * Immediately writes all dirty pages in the buffer pool to disk.
	 */
	public void flushAllPages() {
		System.out.println("Unpining all pages");
		// loops over the pagemap
		// we assume that a frame in the frametab that is not in
		// the map should be empty, then, no need to flush
		for (FrameDesc frame: pagemap.values())
		{
			flushPage(frame.pageno);
		}


	}

	/**
	 * Gets the total number of buffer frames.
	 */
	public int getNumBuffers() {
		System.out.println("Returning num of buffers");
		return frametab.length;
	}

	/**
	 * Gets the total number of unpinned buffer frames.
	 */
	public int getNumUnpinned() {
		//TODO: check if we can mantain a counter to avoid a loop here

		int count = 0;
		for (int i=0; i<frametab.length; i++)
		{
			if(frametab[i].pincnt==0)
			{
				count++;
			}
		}
		return count;
	}

} // public class BufMgr implements GlobalConst
