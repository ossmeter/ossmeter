package org.ossmeter.rascal.debug;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.Platform;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class RascalDebugApp implements IApplication {
	
	@Override
	public Object start(IApplicationContext context) throws Exception {

		Mongo mongo = new Mongo();
		
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		
		Platform platform = new Platform(mongo);

		Thread.sleep(5000); // Give things a chance to register
		
		// Check that the platform has detected the dummy MPs
		System.out.println("Registered metric providers:");
		for (IMetricProvider imp : platform.getMetricProviderManager().getMetricProviders()) {
			System.out.println("\t**"+imp.getIdentifier());
		}
		
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		//nothing to do	
	}
}
