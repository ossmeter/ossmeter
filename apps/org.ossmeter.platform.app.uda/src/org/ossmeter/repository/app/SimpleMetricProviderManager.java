package org.ossmeter.repository.app;

import java.util.ArrayList;
import java.util.List;

import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.IMetricProviderManager;

public class SimpleMetricProviderManager implements IMetricProviderManager {
	
	protected List<IMetricProvider> metricProviders = new ArrayList<IMetricProvider>();
	
	@Override
	public List<IMetricProvider> getMetricProviders() {
		return metricProviders;
	}

}
