package org.ossmeter.metricprovider.rascal;

import java.util.List;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.IMetricProviderManager;

public class RascalMetricProviderManager implements IMetricProviderManager {
	@Override
	public List<IMetricProvider> getMetricProviders() {
		
		
		return RascalManager.getInstance().getMetricProviders();
	}
}
