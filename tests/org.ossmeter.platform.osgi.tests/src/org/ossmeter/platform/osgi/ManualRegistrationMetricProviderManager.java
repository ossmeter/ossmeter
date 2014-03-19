package org.ossmeter.platform.osgi;

import java.util.ArrayList;
import java.util.List;

import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.IMetricProviderManager;

public class ManualRegistrationMetricProviderManager implements
		IMetricProviderManager {

	protected List<IMetricProvider> mps;
	
	public ManualRegistrationMetricProviderManager() {
		this.mps = new ArrayList<>();
	
	}
	
	public void addMetricProvider(IMetricProvider mp) {
		this.mps.add(mp);
	}
	
	@Override
	public List<IMetricProvider> getMetricProviders() {
		return mps;
	}

}
