package org.ossmeter.metricprovider.historic.sourceforgeimporter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.List;

import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.sourceforge.SourceForgeProject;
import org.ossmeter.repository.model.sourceforge.importer.SourceforgeProjectImporter;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class SourceForgeImporterProvider implements IHistoricalMetricProvider {
	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.historic.sourceforgeimporter";
	
	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;
	
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
		// TODO Auto-generated method stub
		return "SourceForge importer";
	}

	@Override
	public String getSummaryInformation() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;

	}

	@Override
	public Pongo measure(Project project) {
		SourceForgeProject ep = null;
		Mongo mongo;
		try {
			SourceforgeProjectImporter epi = new SourceforgeProjectImporter();
			mongo = new Mongo();
			PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
			//Lo posso prendere da qualche altra parte
			Platform platform = new Platform(mongo);
			ep = epi.importProject(project.getShortName(), platform);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ep;
	}

}
