package org.ossmeter.platform.admin;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.ossmeter.platform.admin"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	private Component component;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		component = new Component();
		component.getServers().add(Protocol.HTTP, 8183);
		component.getClients().add(Protocol.FILE);
		
		Application application = new AdminApplication();
		
		component.getDefaultHost().attach(application);
		component.start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		component.stop();
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
