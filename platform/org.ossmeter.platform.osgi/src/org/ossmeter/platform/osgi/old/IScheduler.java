package org.ossmeter.platform.osgi.old;

import org.ossmeter.platform.osgi.executors.SchedulerStatus;

public interface IScheduler {

	/**
	 * No longer accept new jobs. Once finished stop.
	 */
	public boolean finish();
	
	/**
	 * Execute jobs/scheduling.
	 */
	public void run();
	
	public SchedulerStatus getStatus();
	
	
}
