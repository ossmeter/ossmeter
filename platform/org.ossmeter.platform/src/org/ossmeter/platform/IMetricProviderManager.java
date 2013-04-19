package org.ossmeter.platform;

import java.util.List;

import com.googlecode.pongo.runtime.PongoDB;

public interface IMetricProviderManager {
	
	public List<IMetricProvider> getMetricProviders();

}
