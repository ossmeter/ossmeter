package org.ossmeter.platform.util;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

public class ExtensionPointHelper {
	public static IConfigurationElement[] getConfigurationElementsForExtensionPoint(String extensionPointId) {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] configurationElements=null;
		
		IExtensionPoint extensionPoint = registry.getExtensionPoint(extensionPointId);
		configurationElements =  extensionPoint.getConfigurationElements();
		
		return configurationElements;
	}
}
