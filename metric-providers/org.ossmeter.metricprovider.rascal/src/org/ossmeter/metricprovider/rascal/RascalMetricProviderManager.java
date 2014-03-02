package org.ossmeter.metricprovider.rascal;

import java.util.List;

import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.IMetricProviderManager;

public class RascalMetricProviderManager implements IMetricProviderManager {

	@Override
	public List<IMetricProvider> getMetricProviders() {
		// Get instance of service, return registered metrics.
		RascalMetricServiceTracker tracker = RascalMetricServiceTracker.getInstance();
		
		System.out.println("Number of rascal mps tracked: " + tracker.getRegisteredRascalMetrics().size());
		return tracker.getRegisteredRascalMetrics();
	}

}
