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

		
	}

	@Override
	public void unpinPage(FrameDesc fdesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int pickVictim() {
		// TODO Auto-generated method stub

		// when ticking tick = (tick + 1) % frametab.length;
		return 0;
	}

}
