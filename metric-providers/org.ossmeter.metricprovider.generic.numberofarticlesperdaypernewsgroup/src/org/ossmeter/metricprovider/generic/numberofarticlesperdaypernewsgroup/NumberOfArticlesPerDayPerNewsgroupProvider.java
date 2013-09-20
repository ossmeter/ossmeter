package org.ossmeter.metricprovider.generic.numberofarticlesperdaypernewsgroup;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.generic.numberofarticlesperdaypernewsgroup.model.DailyNewsgroupData;
import org.ossmeter.metricprovider.generic.numberofarticlesperdaypernewsgroup.model.DailyNoa;
import org.ossmeter.metricprovider.numberofarticles.NoaMetricProvider;
import org.ossmeter.metricprovider.numberofarticles.model.NewsgroupData;
import org.ossmeter.metricprovider.numberofarticles.model.Noa;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class NumberOfArticlesPerDayPerNewsgroupProvider implements IHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.generic.numberofarticlesperdaypernewsgroup";

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
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
		}
		return false;
	}

	@Override
	public Pongo measure(Project project) {

		DailyNoa dailyNoa = new DailyNoa();
		for (IMetricProvider used : uses) {
			 Noa usedNoa = ((NoaMetricProvider)used).adapt(context.getProjectDB(project));
			 for (NewsgroupData newsgroup: usedNoa.getNewsgroups()) {
				 DailyNewsgroupData dailyNewsgroupData = new DailyNewsgroupData();
				 dailyNewsgroupData.setUrl_name(newsgroup.getUrl_name());
				 dailyNewsgroupData.setNumberOfArticles(newsgroup.getNumberOfArticles());
				 dailyNoa.getNewsgroups().add(dailyNewsgroupData);
			 }
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

	@Override
	public String getShortIdentifier() {
		return "numberofarticlesperdaypernewsgroup";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Articles Per Day Per Newsgroup Provider";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of articles per day for each newsgroup separately.";
	}
}
