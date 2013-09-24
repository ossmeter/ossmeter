package org.ossmeter.metricprovider.rascal;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.IMetricProviderManager;
import org.ossmeter.platform.util.ExtensionPointHelper;

public class RascalMetricProviderManager implements IMetricProviderManager {

	protected final String extensionId = "org.ossmeter.metricprovider.rascal";
	
	@Override
	public List<IMetricProvider> getMetricProviders() {
		System.out.println("Rascal manager");
		List<IMetricProvider> metricProviders = new ArrayList<IMetricProvider>();
		
		for(IConfigurationElement configurationElement : ExtensionPointHelper.getConfigurationElementsForExtensionPoint(extensionId)){
			String rascalUri = configurationElement.getAttribute("rascalUri");
			boolean isHistoric = Boolean.valueOf(configurationElement.getAttribute("isHistoric"));

			if (isHistoric) {
				metricProviders.add(new RascalHistoricMetricProvider(URI.create(rascalUri)));
			} else {
				metricProviders.add(new RascalTransientMetricProvider(URI.create(rascalUri)));
			}
		}
		
		return metricProviders;
	}

}
