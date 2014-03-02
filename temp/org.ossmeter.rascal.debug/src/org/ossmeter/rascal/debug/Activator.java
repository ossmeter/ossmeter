package org.ossmeter.rascal.debug;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.ossmeter.platform.IMetricProvider;

public class Activator extends AbstractUIPlugin {

		// The plug-in ID
		public static final String PLUGIN_ID = "org.ossmeter.rascal.debug"; //$NON-NLS-1$

		// The shared instance
		private static Activator plugin;
		
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
			
			// Dynamically register some metric providers
			DummyMetricProvider d = new DummyMetricProvider("foo");
			DummyMetricProvider e = new DummyMetricProvider("bar");
			DummyMetricProvider f = new DummyMetricProvider("whizz");
			DummyMetricProvider g = new DummyMetricProvider("pop");
			
			context.registerService(IMetricProvider.class, d, null);
			context.registerService(IMetricProvider.class, e, null);
			context.registerService(IMetricProvider.class, f, null);
			context.registerService(IMetricProvider.class, g, null);
			
//			// OSGi Services
//			ServiceTracker tracker = new ServiceTracker<>(context, IMetricProvider.class, null);
//			tracker.open();
//			System.out.println("services: " + tracker.getServices());
//			
//			// Extension points
//			IExtensionRegistry registry = Platform.getExtensionRegistry();
//			IConfigurationElement[] configurationElements=null;
//			
//			IExtensionPoint extensionPoint = registry.getExtensionPoint("org.ossmeter.platform.metricprovider");
//			configurationElements =  extensionPoint.getConfigurationElements();
//			System.out.println("extensions: " + configurationElements.length);
//
//			// Register new service
//			context.registerService(IMetricProvider.class, new DummyMetricProvider(), null);
//			
//			// Check Services
//			tracker = new ServiceTracker<>(context, IMetricProvider.class, null);
//			tracker.open();
//			System.out.println("services: " + tracker.getServices());
//			
//			// Check extension points
//			extensionPoint = registry.getExtensionPoint("org.ossmeter.platform.metricprovider");
//			configurationElements =  extensionPoint.getConfigurationElements();
//			System.out.println("extensions: " + configurationElements.length);
			
			
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
		 */
		public void stop(BundleContext context) throws Exception {
			plugin = null;
			super.stop(context);
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
