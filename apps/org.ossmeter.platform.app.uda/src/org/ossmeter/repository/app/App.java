    package org.ossmeter.repository.app;

import java.io.FileNotFoundException;
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
		//TESTED
		addEclipseProjects(platform);
		//addSourceForgeProjects(platform);
		//addGitHubRepositories(platform);
		//addGoogleCodeRepositories(platform);
		//addRedmineProjects(platform);
		//DA TESTARE
		//platform.run(); 
	}
	
	private void addSourceForgeProjects(Platform platform) {
		
		SourceforgeProjectImporter importer = new SourceforgeProjectImporter();
		//importer.importProjectByUrl("http://sourceforge.net/projects/tortoisesvn/?source=directory-featured",platform);	
		importer.importAll(platform);
		
		platform.getProjectRepositoryManager().getProjectRepository().sync();
	}
	private void addRedmineProjects(Platform platform) {
		
		RedmineImporter importer;
		try {
			importer = new RedmineImporter();
			importer.importProjects(platform, 5);	
			platform.getProjectRepositoryManager().getProjectRepository().sync();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void addEclipseProjects(Platform platform) {
		EclipseProjectImporter importer = new EclipseProjectImporter();
		importer.importAll(platform);
		platform.getProjectRepositoryManager().getProjectRepository().sync();
	}
	
	private void addGitHubRepositories(Platform platform) {
		GitHubImporter importer = new GitHubImporter();	
		importer.importProjects(platform, 5);
		//importer.importProjectByUrl("https://github.com/canadaduane/house", platform);
		platform.getProjectRepositoryManager().getProjectRepository().sync();
	}
	private void addGoogleCodeRepositories(Platform platform) {
		GoogleCodeImporter importer = new GoogleCodeImporter();	
		importer.importProjectByUrl("https://code.google.com/p/firetray/", platform);
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
