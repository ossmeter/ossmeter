package org.ossmeter.metricprovider.trans.importer.redmine;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.logging.OssmeterLogger;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.importer.exception.RepoInfoNotFound;
import org.ossmeter.repository.model.redmine.RedmineProject;
import org.ossmeter.repository.model.redmine.importer.RedmineImporter;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoDB;
import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class RedmineImporterProvider implements ITransientMetricProvider {
	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.trans.redmineimporter";
	
	protected MetricProviderContext context;
	OssmeterLogger logger;
	public RedmineImporterProvider()
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("Redmine Importer");
		logger.addConsoleAppender(OssmeterLogger.DEFAULT_PATTERN);
	}
	/**
	 * List of MPs that are used by this MP. These are MPs who have specified that 
	 * they 'provide' data for this MP.
	 */
	protected List<IMetricProvider> uses;
	
	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return IDENTIFIER;
	}

	@Override
	public String getShortIdentifier() {
		// TODO Auto-generated method stub
		return "redmineimporter";
	}

	@Override
	public String getFriendlyName() {
		// TODO Auto-generated method stub
		return "Redmine importer";
	}

	@Override
	public String getSummaryInformation() {
		// TODO Auto-generated method stub
		return "This provider enable to update a projects calling a importProject from redmine importer";
	}

	@Override
	public boolean appliesTo(Project project) {
		return (project instanceof RedmineProject) ? true : false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;

	}

	@Override
	public List<String> getIdentifiersOfUses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public PongoDB adapt(DB db) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void measure(Project project, ProjectDelta delta, PongoDB db) {
		RedmineProject ep = null;
		try {
			RedmineImporter epi = new RedmineImporter();
			Mongo mongo = new Mongo();
			PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
			Platform platform = new Platform(mongo);
			ep = epi.importProject(project.getShortName(), platform);//, "http://mancoosi.di.univaq.it/redmine/","juri","juri", "369fb37d8ca43f186505f588a14809a294aea732");
			if (ep.getExecutionInformation().getInErrorState() )
				project.getExecutionInformation().setInErrorState(true);
			mongo.close();
		} catch (UnknownHostException e) {
			logger.error("Error launch EclipseImporterProvider " );
		} catch (IOException e) {
			logger.error("Error launch EclipseImporterProvider " );
		} catch (RepoInfoNotFound e) {
			logger.error("Error launch EclipseImporterProvider " );
		}
		
	}
}
