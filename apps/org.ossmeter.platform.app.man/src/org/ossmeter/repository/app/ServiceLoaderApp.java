package org.ossmeter.repository.app;

import org.ossmeter.platform.delta.communicationchannel.ServiceCommunicationChannelManager;
import org.ossmeter.platform.delta.vcs.ServiceVcsManager;

public class ServiceLoaderApp {

	public static void main(String[] args) throws Exception {
		ServiceLoaderSetup.copyMetaInfToBins();

		ServiceLoaderMetricProviderManager mpManager = new ServiceLoaderMetricProviderManager();
	
		new App().run(mpManager, new ServiceVcsManager(), new ServiceCommunicationChannelManager());
	}
}
