package org.ossmeter.metricprovider.trans.importer.eclipse;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.List;

import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.logging.OssmeterLogger;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.eclipse.EclipseProject;
import org.ossmeter.repository.model.eclipse.importer.EclipseProjectImporter;
import org.ossmeter.repository.model.importer.exception.ProjectUnknownException;

import com.googlecode.pongo.runtime.PongoDB;
import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.DB;
import com.mongodb.Mongo;



public class EclipseImporterProvider implements ITransientMetricProvider{
	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.trans.eclipseimporter";
	
	protected MetricProviderContext context;
	OssmeterLogger logger;
	public EclipseImporterProvider()
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("GitHub Importer");
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
		return "eclipseimporter";
	}

	@Override
	public String getFriendlyName() {
		// TODO Auto-generated method stub
		return "Eclipse importer";
	}

	@Override
	public String getSummaryInformation() {
		// TODO Auto-generated method stub
		return "This provider imports the prpoject metadata from the Eclipse forge by calling the corresponding importer.";
	}

	@Override
	public boolean appliesTo(Project project) {
		return (project instanceof EclipseProject) ? true : false;
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
		// TODO Auto-generated method stub
		EclipseProject ep = null;
		Mongo mongo;
		try {
			EclipseProjectImporter epi = new EclipseProjectImporter();
			mongo = new Mongo();
			PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());

			Platform platform = new Platform(mongo);
			ep = epi.importProject(project.getShortName(), platform);
			if (ep.getExecutionInformation().getInErrorState() )
				project.getExecutionInformation().setInErrorState(true);
			mongo.close();
		} catch (UnknownHostException e) {
			logger.error("Error launch RedmineImporterProvider " + e.getMessage());
		}catch (ProjectUnknownException e) {
			logger.error("Error launch RedmineImporterProvider " + e.getMessage());
		} catch (MalformedURLException e) {
			logger.error("Error launch RedmineImporterProvider " + e.getMessage());
		} catch (IOException e) {
			logger.error("Error launch RedmineImporterProvider " + e.getMessage());
		}
	}
}
