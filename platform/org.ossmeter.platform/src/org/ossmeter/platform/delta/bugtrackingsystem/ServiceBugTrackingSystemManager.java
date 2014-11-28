/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
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
