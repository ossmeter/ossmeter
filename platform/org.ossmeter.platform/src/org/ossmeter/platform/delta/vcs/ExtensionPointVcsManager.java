package org.ossmeter.platform.delta.vcs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.ossmeter.platform.util.ExtensionPointHelper;

public class ExtensionPointVcsManager extends PlatformVcsManager {
	
	protected final String vcsManagerExtensionPointId = "";
	
	public List<IVcsManager> getVcsManagers() {
		if (vcsManagers == null) {
			vcsManagers = new ArrayList<IVcsManager>();
			
			for (IConfigurationElement confElement : ExtensionPointHelper.getConfigurationElementsForExtensionPoint(vcsManagerExtensionPointId)) {
				try {
					vcsManagers.add((IVcsManager) confElement.createExecutableExtension("vcsManager"));
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
		return vcsManagers;
	}
}
