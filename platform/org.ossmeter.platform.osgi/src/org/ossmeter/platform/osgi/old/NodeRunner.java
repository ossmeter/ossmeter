package org.ossmeter.platform.osgi.old;

import java.util.List;

import org.ossmeter.platform.osgi.executors.MasterScheduler;
import org.ossmeter.platform.osgi.executors.SlaveScheduler;

public class NodeRunner {
	
	protected List<Object> workers;
	
	protected IScheduler scheduler;
	
	public void run() {
		if (isMaster()) {
			scheduler = new MasterScheduler();
		} else {
//			scheduler = new SlaveScheduler();
		}
		
		// FIXME: This above is probably not the way to go. This class needs
		// to be the service class essentially. All nodes advertise their Slave abilities. Voting
		// occurs. Master starts the distribution of tasks. Master can also be slave (but with a reduced
		// load/threadpool size.
	}
	
	public boolean isMaster() {
		return false;
	}
}
