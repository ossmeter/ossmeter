package org.ossmeter.metricprovider.rascal;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.ossmeter.platform.logging.OssmeterLoggerFactory;

public class Rasctivator implements BundleActivator {
	private static final Logger LOGGER = new OssmeterLoggerFactory().makeNewLoggerInstance("rascalLogger");
  private static BundleContext context;
	
	static BundleContext getContext() {
		return context;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Rasctivator.context = bundleContext;
		
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
        .getExtensionPoint("ossmeter.rascal.metricprovider");
                   
    if (extensionPoint != null) {
    	for (IExtension element : extensionPoint.getExtensions()) {
	      String name = element.getContributor().getName();
	      Bundle bundle = Platform.getBundle(name);
	      RascalManager.getInstance().registerRascalMetricProvider(bundle);
	    }
    }
    
    extensionPoint = Platform.getExtensionRegistry().getExtensionPoint("ossmeter.rascal.extractor");
    
    if (extensionPoint != null) {
    	for (IExtension element : extensionPoint.getExtensions()) {
    		String name = element.getContributor().getName();
    		Bundle bundle = Platform.getBundle(name);
    		RascalManager.getInstance().registerExtractor(bundle);
    	}
    }
    
	}
	
	public static void logException(Object message, Throwable cause) {
	  LOGGER.error(message, cause);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Rasctivator.context = null;
	}

}
