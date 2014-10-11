package org.ossmeter.metricprovider.trans.importer.sourceforge;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.List;

import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.logging.OssmeterLogger;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectExecutionInformation;
import org.ossmeter.repository.model.importer.exception.WrongUrlException;
import org.ossmeter.repository.model.sourceforge.SourceForgeBugTrackingSystem;
import org.ossmeter.repository.model.sourceforge.SourceForgeProject;
import org.ossmeter.repository.model.sourceforge.importer.SourceforgeProjectImporter;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoDB;
import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.DB;
import com.mongodb.Mongo;
public class SourceForgeImporterProvider  implements ITransientMetricProvider {

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.trans.sourceforgeimporter";
	
	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;

	private OssmeterLogger logger;
	
	public SourceForgeImporterProvider()
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.importer.sourceforge");
		logger.addConsoleAppender(OssmeterLogger.DEFAULT_PATTERN);
	}
	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return IDENTIFIER;
	}

	@Override
	public String getShortIdentifier() {
		// TODO Auto-generated method stub
		return "sourceforgeimporter";
	}

	@Override
	public String getFriendlyName() {
		return "SourceForge importer";
	}

	@Override
	public String getSummaryInformation() {
		return "This provider enable to update a projects calling a importProject from sourceforge importer";
	}

	@Override
	public boolean appliesTo(Project project) {
		return (project instanceof SourceForgeProject) ? true : false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;

	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return null;
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;

	}

	@Override
	public PongoDB adapt(DB db) {
		return null;
	}

	@Override
	public void measure(Project project, ProjectDelta delta, PongoDB db) {
		SourceForgeProject ep = null;
		Mongo mongo;
		try {
			SourceforgeProjectImporter epi = new SourceforgeProjectImporter();
			mongo = new Mongo();
			PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
			Platform platform = new Platform(mongo);
			try {
				ep = epi.importProject(project.getShortName(), platform);
			} catch (WrongUrlException e) {
				project.getExecutionInformation().setInErrorState(false);
			}
			if (ep == null)
			{
				if(project.getExecutionInformation() == null)
					project.setExecutionInformation(new ProjectExecutionInformation());
				project.getExecutionInformation().setInErrorState(true);
			}
			mongo.close();
		} catch (UnknownHostException e) {
			logger.error("Sourceforge metric provider exception:");
			if(project.getExecutionInformation() == null)
				project.setExecutionInformation(new ProjectExecutionInformation());
			project.getExecutionInformation().setInErrorState(true);
		} catch (IOException e) {
			logger.error("Sourceforge metric provider exception:");
			if(project.getExecutionInformation() == null)
				project.setExecutionInformation(new ProjectExecutionInformation());
			project.getExecutionInformation().setInErrorState(true);
		}
		
		
	}

}
