package org.ossmeter.metricprovider.generic.numberofactivecommittersperdaypernewsgroup;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.generic.numberofactivecommittersperdaypernewsgroup.model.DailyNewsgroupData;
import org.ossmeter.metricprovider.generic.numberofactivecommittersperdaypernewsgroup.model.DailyNoa;
import org.ossmeter.metricprovider.activecommitters.ActiveCommittersMetricProvider;
import org.ossmeter.metricprovider.activecommitters.model.ActiveCommitters;
import org.ossmeter.metricprovider.activecommitters.model.NewsgroupData;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.NntpNewsGroup;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class NumberOfActiveCommittersPerDayPerNewsgroupProvider implements IHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.generic.numberofactivecommittersperdaypernewsgroup";

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
			  ActiveCommitters activeCommitters = ((ActiveCommittersMetricProvider)used).adapt(context.getProjectDB(project));
			 for (NewsgroupData newsgroup: activeCommitters.getNewsgroups()) {
				 DailyNewsgroupData dailyNewsgroupData = new DailyNewsgroupData();
				 dailyNewsgroupData.setUrl_name(newsgroup.getUrl_name());
				 dailyNewsgroupData.setNumberOfActiveCommitters(newsgroup.getNumberOfActiveCommiters());
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
		return Arrays.asList(ActiveCommittersMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}
}
