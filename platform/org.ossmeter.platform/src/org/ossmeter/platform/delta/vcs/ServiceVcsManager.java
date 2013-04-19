package org.ossmeter.platform.delta.vcs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class ServiceVcsManager extends PlatformVcsManager {
	
	public ServiceVcsManager() {
		getVcsManagers();
	}
	
	public List<IVcsManager> getVcsManagers() {
		if (vcsManagers == null) {
			vcsManagers = new ArrayList<IVcsManager>();
			
			ServiceLoader<IVcsManager> vcsLoader = ServiceLoader.load(IVcsManager.class);
			Iterator<IVcsManager> it = vcsLoader.iterator();
			
			while(it.hasNext()) {
				vcsManagers.add(it.next());
			}
		}
		System.err.println("VcsManagers: " + vcsManagers);
		return vcsManagers;
	}
}
