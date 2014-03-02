package org.ossmeter.metricprovider.rascal;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.ossmeter.platform.IMetricProvider;

public class RascalMetricServiceTracker implements ServiceTrackerCustomizer<IMetricProvider, IMetricProvider> {

	private static RascalMetricServiceTracker instance;
	
	List<IMetricProvider> metricProviders;
	
	// To prevent instantiation
	private RascalMetricServiceTracker() {
		metricProviders = new ArrayList<IMetricProvider>();
	}
	
	public List<IMetricProvider> getRegisteredRascalMetrics() {
		return metricProviders;
	}
	
	@Override
	public IMetricProvider addingService(ServiceReference<IMetricProvider> reference) {

		IMetricProvider metric = Rasctivator.getContext().getService(reference);
		metricProviders.add(metric);
		
		return metric;
	}

	@Override
	public void modifiedService(ServiceReference<IMetricProvider> reference, IMetricProvider service) {
		// TODO Nothing to do?
	}

	@Override
	public void removedService(ServiceReference<IMetricProvider> reference, IMetricProvider service) {
		metricProviders.remove(service);
	}

	public static RascalMetricServiceTracker getInstance() {
		if (instance == null) {
			instance = new RascalMetricServiceTracker();
		}
			
		return instance;
	}
}
