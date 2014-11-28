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
package org.ossmeter.platform.delta.communicationchannel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;


public class ServiceCommunicationChannelManager extends PlatformCommunicationChannelManager {
	
	public ServiceCommunicationChannelManager() {
		getCommunicationChannelManagers();
	}
	
	public List<ICommunicationChannelManager> getCommunicationChannelManagers() {
		if (communicationChannelManagers == null) {
			communicationChannelManagers = new ArrayList<ICommunicationChannelManager>();
			
			ServiceLoader<ICommunicationChannelManager> vcsLoader = ServiceLoader.load(ICommunicationChannelManager.class);
			Iterator<ICommunicationChannelManager> it = vcsLoader.iterator();
			
			while(it.hasNext()) {
				communicationChannelManagers.add(it.next());
			}
		}
		System.err.println("CommunicationChannelManagers: " + communicationChannelManagers);
		return communicationChannelManagers;
	}

}
