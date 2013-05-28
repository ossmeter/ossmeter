package org.ossmeter.platform.delta.bugtrackingsystem;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.ossmeter.platform.util.ExtensionPointHelper;

public class ExtensionPointBugTrackingSystemManager extends PlatformBugTrackingSystemManager {
	
	protected final String bugTrackingSystemExtensionPointId = "";
	
	public List<IBugTrackingSystemManager> getBugTrackingSystemManagers() {
		if (bugTrackingSystemManagers == null) {
			bugTrackingSystemManagers = new ArrayList<IBugTrackingSystemManager>();
			
			for (IConfigurationElement confElement : 
				ExtensionPointHelper.getConfigurationElementsForExtensionPoint(bugTrackingSystemExtensionPointId)) {
				try {
					bugTrackingSystemManagers.add((IBugTrackingSystemManager) 
							confElement.createExecutableExtension("communicationChannelManager"));
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
		
		return bugTrackingSystemManagers;
		
	}
}
