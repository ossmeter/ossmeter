    package org.ossmeter.repository.app;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.ossmeter.platform.ExtensionPointMetricProviderManager;
import org.ossmeter.platform.IMetricProviderManager;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.delta.vcs.ExtensionPointVcsManager;
import org.ossmeter.platform.delta.vcs.PlatformVcsManager;
import org.ossmeter.repository.model.redmine.importer.RedmineImporter;
//import org.ossmeter.repository.model.github.GitHubRepository;
//import org.ossmeter.repository.model.github.GitHubUser;
import org.ossmeter.repository.model.sourceforge.SourceForgeProject;
import org.ossmeter.repository.model.sourceforge.importer.SourceforgeProjectImporter;

import org.ossmeter.repository.model.eclipse.importer.*;
import org.ossmeter.repository.model.eclipse.*;
import org.ossmeter.repository.model.github.GitHubRepository;
import org.ossmeter.repository.model.github.importer.GitHubImporter;
import org.ossmeter.repository.model.googlecode.importer.GoogleCodeImporter;
import org.ossmeter.repository.model.*;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class App implements IApplication {
	
	public void run(IMetricProviderManager metricProviderManager, PlatformVcsManager platformVcsManager) throws Exception {
		Mongo mongo = new Mongo();
		
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		
		
		Platform platform = new Platform(mongo);
		platform.setMetricProviderManager(metricProviderManager);
		platform.setPlatformVcsManager(platformVcsManager);
		//TESTATI OK
		addEclipseProjects(platform);
		addSourceForgeProjects(platform);
		addGitHubRepositories(platform);
		addGoogleCodeRepositories(platform);
		addRedmineProjects(platform);
		
		//DA TESTARE
		
		
		
		
		
		//platform.run(); 
	}
	
	private void addSourceForgeProjects(Platform platform) {
		
		SourceforgeProjectImporter importer = new SourceforgeProjectImporter();
		importer.importProjects(platform, 5);	
		
		platform.getProjectRepositoryManager().getProjectRepository().sync();
	}
	private void addRedmineProjects(Platform platform) {
		
		RedmineImporter importer = new RedmineImporter("http://mancoosi.di.univaq.it/redmine/","369fb37d8ca43f186505f588a14809a294aea732","juri","juri");
		importer.importProjects(platform, 5);	
		
		platform.getProjectRepositoryManager().getProjectRepository().sync();
	}
	
	private void addEclipseProjects(Platform platform) {
		EclipseProjectImporter importer = new EclipseProjectImporter();	
		importer.importProjects(platform, 5);
		platform.getProjectRepositoryManager().getProjectRepository().sync();
	}
	
	private void addGitHubRepositories(Platform platform) {
		GitHubImporter importer = new GitHubImporter("ffab283e2be3265c7b0af244e474b28430351973");	
		importer.importProjects(platform, 5);
	//	platform.getProjectRepositoryManager().getProjectRepository().sync();
	}
	private void addGoogleCodeRepositories(Platform platform) {
		GoogleCodeImporter importer = new GoogleCodeImporter();	
		importer.importProjects(platform, 5);
		platform.getProjectRepositoryManager().getProjectRepository().sync();
	}




		
	@Override
	public Object start(IApplicationContext context) throws Exception {
		run(new ExtensionPointMetricProviderManager(), new ExtensionPointVcsManager());
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		//nothing to do		
	}
}
