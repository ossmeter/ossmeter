/*******************************************************************************
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.ossmeter.platform.client.api;

import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.engine.connector.ConnectorHelper;

public class Activator implements BundleActivator {

    private Component component;

    public void start(BundleContext context) throws Exception {
//    	context.addFrameworkListener(new FrameworkListener() {
//			@Override
//			public void frameworkEvent(FrameworkEvent event) {
//				System.err.println(event);
//				if (event.getType() == FrameworkEvent.STARTED) {
//					
//					List<ConnectorHelper<Server>> servers = Engine.getInstance().getRegisteredServers();
//					System.out.println("Server connectors - "+servers.size());
//					for (ConnectorHelper<Server> connectorHelper : servers) {
//					    System.out.println("connector = "+connectorHelper.getClass());
//					}
//					
//				}
//			}
//		});	
    	component = new Component();
    	
    	Server server = new Server(Protocol.HTTP, 8182);
    	component.getServers().add(server);
    	
    	final Application app = new ApiApplication();
    	component.getDefaultHost().attachDefault(app);
    	try {
    		component.start();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    public void stop(BundleContext context) throws Exception {
        if (component != null && component.isStarted()) component.stop();
    }

}