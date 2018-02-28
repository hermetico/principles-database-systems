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
        //TODO why do we need this?
	}

	@Override
	public void freePage(FrameDesc fdesc) {
		// TODO Auto-generated method stub
        //TODO why do we need this?
	}

	@Override
	public void pinPage(FrameDesc fdesc) {
        fdesc.state = 1;
		
	}

	@Override
	public void unpinPage(FrameDesc fdesc) {
        // TODO Auto-generated method stub
		//TODO why do we need this?
		
	}

	@Override
	public int pickVictim() {
		// TODO Auto-generated method stub

		// when ticking tick = (tick + 1) % frametab.length;
        int keep_looping = frametab.length * 2;

        while (keep_looping > 0){

            if(frametab[tick].state == 0){
                return tick;
            }else if(frametab[tick].pincnt == 0){
                frametab[tick].state = 0;
            }

            tick = (tick + 1) % frametab.length;
        }

		return -1;
	}

}
