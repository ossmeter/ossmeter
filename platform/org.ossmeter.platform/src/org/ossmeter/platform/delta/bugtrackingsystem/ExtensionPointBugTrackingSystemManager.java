package org.ossmeter.platform.delta.bugtrackingsystem;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.ossmeter.platform.util.ExtensionPointHelper;

public class ExtensionPointBugTrackingSystemManager extends PlatformBugTrackingSystemManager {
	
	protected final String bugTrackingSystemExtensionPointId = "org.ossmeter.platform.managers.bugtracking";
	
	public List<IBugTrackingSystemManager> getBugTrackingSystemManagers() {
		if (bugTrackingSystemManagers == null) {
			bugTrackingSystemManagers = new ArrayList<IBugTrackingSystemManager>();
			
			for (IConfigurationElement confElement : ExtensionPointHelper.getConfigurationElementsForExtensionPoint(bugTrackingSystemExtensionPointId)) {
				try {
					bugTrackingSystemManagers.add((IBugTrackingSystemManager) confElement.createExecutableExtension("manager"));
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
			System.err.println("Registered bug tracking managers: ");
			for (IBugTrackingSystemManager man : bugTrackingSystemManagers) {
				System.err.println("\t" + man.getClass());
			}
		}
		
		return bugTrackingSystemManagers;
		
	}
}
