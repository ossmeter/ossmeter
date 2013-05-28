package org.ossmeter.platform.delta.bugtrackingsystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;


public class ServiceBugTrackingSystemManager extends PlatformBugTrackingSystemManager {
	
	public ServiceBugTrackingSystemManager() {
		getBugTrackingSystemManagers();
	}
	
	public List<IBugTrackingSystemManager> getBugTrackingSystemManagers() {
		if (bugTrackingSystemManagers == null) {
			bugTrackingSystemManagers = new ArrayList<IBugTrackingSystemManager>();
			
			ServiceLoader<IBugTrackingSystemManager> vcsLoader = ServiceLoader.load(IBugTrackingSystemManager.class);
			Iterator<IBugTrackingSystemManager> it = vcsLoader.iterator();
			
			while(it.hasNext()) {
				bugTrackingSystemManagers.add(it.next());
			}
		}
		System.err.println("BugTrackingSystemManagers: " + bugTrackingSystemManagers);
		return bugTrackingSystemManagers;
	}

}
