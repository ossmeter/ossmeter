package org.ossmeter.metricprovider.historic.redmineimporter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.List;

import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.redmine.RedmineProject;
import org.ossmeter.repository.model.redmine.importer.RedmineImporter;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class RedmineImporterProvider implements IHistoricalMetricProvider {

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.historic.redmineimporter";
	
	protected MetricProviderContext context;
	
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
	public Pongo measure(Project project) {
		RedmineProject ep = null;
		Mongo mongo;
		try {
			RedmineImporter epi = new RedmineImporter("http://mancoosi.di.univaq.it/redmine/","369fb37d8ca43f186505f588a14809a294aea732","juri","juri");
			mongo = new Mongo();
			PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
			Platform platform = new Platform(mongo);
			ep = epi.importProject(project.getShortName(), platform);
			mongo.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ep;
	}
}
