package org.ossmeter.metricprovider.rascal;

import java.util.Collections;
import java.util.LinkedList;
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
	  IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
        .getExtensionPoint("org.ossmeter.metricprovider.rascal", "ossmeter.rascal.metricprovider");

    if (extensionPoint == null) {
      return Collections.emptyList(); // this may happen when nobody extends this point.
    }
    
    List<IMetricProvider> providers = new LinkedList<>();
    for (IExtension element : extensionPoint.getExtensions()) {
      String name = element.getContributor().getName();
      Bundle bundle = Platform.getBundle(name);
      providers.add(computeMetricProvider(bundle));
    }
	}

  private IMetricProvider computeMetricProvider(Bundle bundle) {
    // TODO Auto-generated method stub
    return null;
  }
}
