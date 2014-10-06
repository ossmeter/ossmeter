package org.ossmeter.platform.client.api;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.restlet.Request;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.*;

public class Activator implements BundleActivator {

    private Component component;

    public void start(BundleContext context) throws Exception {
    	
    	component = new Component();
    	component.getServers().add(Protocol.HTTP, 8182);
    	
    	final ApiApplication apiApplication = new ApiApplication();
    	component.getDefaultHost().attachDefault(apiApplication);
    	
    	component.start();
    }

    public void stop(BundleContext context) throws Exception {
        component.stop();
    }

}