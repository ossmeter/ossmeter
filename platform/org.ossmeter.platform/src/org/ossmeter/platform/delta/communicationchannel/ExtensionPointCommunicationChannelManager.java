package org.ossmeter.platform.delta.communicationchannel;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.ossmeter.platform.util.ExtensionPointHelper;

public class ExtensionPointCommunicationChannelManager extends PlatformCommunicationChannelManager {
	
	protected final String communicationChannelManagerExtensionPointId = "";
	
	public List<ICommunicationChannelManager> getCommunicationChannelManagers() {
		if (communicationChannelManagers == null) {
			communicationChannelManagers = new ArrayList<ICommunicationChannelManager>();
			
			for (IConfigurationElement confElement : 
				ExtensionPointHelper.getConfigurationElementsForExtensionPoint(communicationChannelManagerExtensionPointId)) {
				try {
					communicationChannelManagers.add((ICommunicationChannelManager) 
							confElement.createExecutableExtension("communicationChannelManager"));
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
		return communicationChannelManagers;
	}
}
