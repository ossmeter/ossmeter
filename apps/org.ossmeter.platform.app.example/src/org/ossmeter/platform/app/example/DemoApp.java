package org.ossmeter.platform.app.example;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.ossmeter.platform.ExtensionPointMetricProviderManager;
import org.ossmeter.platform.IMetricProviderManager;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.admin.AdminApplication;
import org.ossmeter.platform.delta.bugtrackingsystem.ExtensionPointBugTrackingSystemManager;
import org.ossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.ossmeter.platform.delta.communicationchannel.ExtensionPointCommunicationChannelManager;
import org.ossmeter.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.ossmeter.platform.delta.vcs.ExtensionPointVcsManager;
import org.ossmeter.platform.delta.vcs.PlatformVcsManager;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class DemoApp implements IApplication {
	
	Mongo mongo;
	
	public void run(IMetricProviderManager metricProviderManager, PlatformVcsManager platformVcsManager,
						PlatformCommunicationChannelManager communicationChannelManager, PlatformBugTrackingSystemManager bugTrackingSystemManager) throws Exception {

		// TODO: The platform needs to have been started for the API to work.
		mongo = new Mongo();
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		Platform platform = new Platform(mongo); 
//		platform.setMetricProviderManager(metricProviderManager);
//		platform.setPlatformVcsManager(platformVcsManager);
//		platform.setPlatformCommunicationChannelManager(communicationChannelManager);
//		platform.setPlatformBugTrackingSystemManager(bugTrackingSystemManager);

		AdminApplication app = new AdminApplication();
		
		while (true) {
			try{
				Thread.sleep(50000000);
			} catch (Exception e) {
				throw e;
			}
		}
	}
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		run(new ExtensionPointMetricProviderManager(), new ExtensionPointVcsManager(), new ExtensionPointCommunicationChannelManager(), new ExtensionPointBugTrackingSystemManager());
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		//nothing to do	
		if (mongo != null) {
			mongo.close();
		}
	}
}
