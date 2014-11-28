/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.ossmeter.platform.osgi.old;

import java.util.Dictionary;
import java.util.Properties;

import org.eclipse.ecf.osgi.services.distribution.IDistributionConstants;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.framework.ServiceRegistration;
import org.ossmeter.platform.osgi.Activator;

public class WorkerApplication_Old implements IApplication {

	protected boolean done = false;
	protected Object appLock = new Object();
	
	private ServiceRegistration workerRegistration;
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		Dictionary props = new Properties();
		// add OSGi service property indicated export of all interfaces exposed
		// by service (wildcard)
		props.put(IDistributionConstants.SERVICE_EXPORTED_INTERFACES,
				IDistributionConstants.SERVICE_EXPORTED_INTERFACES_WILDCARD);
		// add OSGi service property specifying config
		props.put(IDistributionConstants.SERVICE_EXPORTED_CONFIGS,
				"ecf.generic.server");
		// add ECF service property specifying container factory args
		props.put(
				IDistributionConstants.SERVICE_EXPORTED_CONTAINER_FACTORY_ARGUMENTS,
				"ecftcp://localhost:3788/worker");
		// register remote service
		workerRegistration = Activator.getContext().registerService(
				IWorker.class.getName(), new RemoteWorker(), props);		
		
		waitForDone();
		return IApplication.EXIT_OK;
	}


	@Override
	public void stop() {
		synchronized (appLock) {
			done = true;
			appLock.notifyAll();
		}	
	}
	
	private void waitForDone() {
		// then just wait here
		synchronized (appLock) {
			while (!done) {
				try {
					appLock.wait();
				} catch (InterruptedException e) {
					// do nothing
				}
			}
		}
	}

}
