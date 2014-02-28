package org.ossmeter.platform.osgi.executors;

import org.ossmeter.platform.osgi.old.IScheduler;

public class MasterScheduler implements IScheduler {

	@Override
	public void run() {

	}
	
	@Override
	public boolean finish() {
		return true;
	}
	
	@Override 
	public SchedulerStatus getStatus(){
		return null;
	}

}
