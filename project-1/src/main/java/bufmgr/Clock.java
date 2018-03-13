package bufmgr;

public class Clock extends Replacer{

	int tick;
	protected Clock(BufMgr bufmgr) {
		super(bufmgr);
		tick = 0;
	}

	@Override
	public void newPage(FrameDesc fdesc) {
		// TODO Auto-generated method stub
	}

	@Override
	public void freePage(FrameDesc fdesc) {
		// TODO Auto-generated method stub
	}

	@Override
	public void pinPage(FrameDesc fdesc) {
		fdesc.pincnt++;
        fdesc.state = 1;
	}

	@Override
	public void unpinPage(FrameDesc fdesc) {
		fdesc.pincnt--;
	}

	@Override
	public int pickVictim() {
		// worst case scenario is do a complete loop 2 times
        for(int keep_looping = frametab.length * 2; keep_looping > 0; keep_looping--){

            if(frametab[tick].state == 0){
                return tick;
            }else if(frametab[tick].pincnt == 0){
                frametab[tick].state = 0;
            }

            // increase tick to move to the next frame
            tick = (tick + 1) % frametab.length;
        }

		return INVALID_PAGEID;
	}

}
