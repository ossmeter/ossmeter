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
package org.ossmeter.platform.client.api;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.restlet.Component;
import org.restlet.data.Protocol;

public class Activator implements BundleActivator {

    private Component component;
//    private JettyServerHelper jetty;

    public void start(BundleContext context) throws Exception {
    	
    	   	
    	component = new Component();
    	component.getServers().add(Protocol.HTTP, 8182);
    	
//    	jetty = new HttpServerHelper(server);
    	
    	component.getContext().getParameters().add("maxThreads", "512");
    	component.getContext().getParameters().add("maxTotalConnections", "100");
    	
    	final ApiApplication apiApplication = new ApiApplication();
    	component.getDefaultHost().attachDefault(apiApplication);
    	
    	component.start();
    	
//    	jetty.start();
    }

    public void stop(BundleContext context) throws Exception {
        component.stop();
//        jetty.stop();
    }

}