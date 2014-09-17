package org.ossmeter.repository.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.IMetricProviderManager;

public class ServiceLoaderMetricProviderManager implements IMetricProviderManager {
	
	static ServiceLoader<IMetricProvider> mpLoader = ServiceLoader.load(IMetricProvider.class);
	
	@Override
	public List<IMetricProvider> getMetricProviders() {
		List<IMetricProvider> providers = new ArrayList<IMetricProvider>();
		Iterator<IMetricProvider> it = mpLoader.iterator();
		while (it.hasNext()) {
			providers.add(it.next());
		}
		
		System.out.println("MPs: " + providers);
		return providers;
	}
}
