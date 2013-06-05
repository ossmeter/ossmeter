package org.ossmeter.metricprovider.generic.overalldailynumberofarticles;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.generic.overalldailynumberofarticles.model.DailyBugzillaData;
import org.ossmeter.metricprovider.generic.overalldailynumberofarticles.model.DailyNoa;
import org.ossmeter.metricprovider.numberofarticles.NoaMetricProvider;
import org.ossmeter.metricprovider.numberofarticles.model.NewsgroupData;
import org.ossmeter.metricprovider.numberofarticles.model.Noa;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class OverallDailyNumberOfArticlesProvider implements IHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.generic.overalldailynumberofarticles";

	protected MetricProviderContext context;
	
	/**
	 * List of MPs that are used by this MP. These are MPs who have specified that 
	 * they 'provide' data for this MP.
	 */
	protected List<IMetricProvider> uses;
	
	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}
	
	@Override
	public boolean appliesTo(Project project) {
		return true; // FIXME: This should really check whether there are any providers
					 // for this MP. Otherwise it'll create an empty DB for every project.
					 // This is not possible in the current implementation because the 'uses'
					 // property is set AFTER this method is called.
	}

	@Override
	public Pongo measure(Project project) {

		DailyNoa dailyNoa = new DailyNoa();
		for (IMetricProvider used : uses) {
			 Noa usedNoa = ((NoaMetricProvider)used).adapt(context.getProjectDB(project));
			 int numberOfArticles = 0;
			 for (NewsgroupData newsgroup: usedNoa.getNewsgroups()) {
				 numberOfArticles += newsgroup.getNumberOfArticles();
			 }
			 DailyBugzillaData dailyNewsgroupData = new DailyBugzillaData();
			 dailyNewsgroupData.setNumberOfArticles(numberOfArticles);
			 dailyNoa.getNewsgroups().add(dailyNewsgroupData);
		}
		return dailyNoa;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(NoaMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}
}
