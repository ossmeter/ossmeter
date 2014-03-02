package org.ossmeter.metricprovider.rascal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.ossmeter.platform.IMetricProvider;

public class Rasctivator implements BundleActivator {

	private static BundleContext context;
	
	protected ServiceTracker<IMetricProvider, IMetricProvider> rascalTracker;

	static BundleContext getContext() {
		return context;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Rasctivator.context = bundleContext;
		
		rascalTracker = new ServiceTracker<>(bundleContext, 
				IMetricProvider.class, RascalMetricServiceTracker.getInstance());
		
		rascalTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Rasctivator.context = null;
		
		rascalTracker.close();
	}

}
