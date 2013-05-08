package org.ossmeter.platform;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.ossmeter.platform.util.ExtensionPointHelper;

import com.googlecode.pongo.runtime.PongoCollection;

public class ExtensionMetricProviderManager implements IMetricProviderManager {
	
	protected List<IMetricProvider> metricProviders;
	protected final String metricProviderExtensionPointId = "org.ossmeter.repository.metricprovider";
	protected final String rascalMetricProviderExtensionPointId = "org.ossmeter.platform.metricprovider.rascal";
	protected List<? extends PongoCollection> metricCollections = null;
	
	public List<IMetricProvider> getMetricProviders() {
		if (metricProviders == null) {
			metricProviders = new ArrayList<IMetricProvider>();
			
			for(IConfigurationElement configurationElement : ExtensionPointHelper.getConfigurationElementsForExtensionPoint(metricProviderExtensionPointId)){
				try {
					metricProviders.add((IMetricProvider) configurationElement.createExecutableExtension("provider"));
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
			
//			for (IConfigurationElement configurationElement : ExtensionPointHelper.getConfigurationElementsForExtensionPoint(rascalMetricProviderExtensionPointId)) {
//				try {
//					String rascalUri = configurationElement.getAttribute("rascalUri");
//					boolean isHistoric = Boolean.valueOf(configurationElement.getAttribute("isHistoric"));
//					
					// TODO: Need access to the source code of RascalMetricProvider. 
					// Do we really want the platform to depend on the Rascal MP projects?? 
//					if (isHistoric) {
//						RascalHistoricMetricProvider ...
//						metricProviders.add(rascalMp);
//					} else {
//						RascalTransientMetricProvider ...
//						metricProviders.add(rascalMp);
//					}
//				} catch (CoreException e) {
//					e.printStackTrace();
//				}
//			}
			
		}
		return metricProviders;
	}	
}
