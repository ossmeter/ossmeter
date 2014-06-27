package org.ossmeter.metricprovider.historic.googlecodeimporter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.List;

import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.googlecode.GoogleCodeProject;
import org.ossmeter.repository.model.googlecode.importer.GoogleCodeImporter;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class GoogleCodeImporterProvider implements IHistoricalMetricProvider {

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.historic.googlecodeimporter";
	
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
		return "googlecodeimporter";
	}

	@Override
	public String getFriendlyName() {
		// TODO Auto-generated method stub
		return "GoogleCode importer";
	}

	@Override
	public String getSummaryInformation() {
		// TODO Auto-generated method stub
		return "This provider enable to update a projects calling a importProject from google code importer";
	}

	@Override
	public boolean appliesTo(Project project) {
		return (project instanceof GoogleCodeProject) ? true : false;
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
		GoogleCodeProject ep = null;
		Mongo mongo;
		try {
			GoogleCodeImporter epi = new GoogleCodeImporter();
			mongo = new Mongo();
			PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
			//Lo posso prendere da qualche altra parte
			Platform platform = new Platform(mongo);
			ep = epi.importProject("https://code.google.com/p/" + project.getName() + "/", platform);
			
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
