package org.ossmeter.metricprovider.historic.bugs.topics;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.bugs.topics.model.BugTopic;
import org.ossmeter.metricprovider.historic.bugs.topics.model.BugsTopicsHistoricMetric;
import org.ossmeter.metricprovider.trans.topics.TopicsTransMetricProvider;
import org.ossmeter.metricprovider.trans.topics.model.BugTrackerTopic;
import org.ossmeter.metricprovider.trans.topics.model.TopicsTransMetric;
import org.ossmeter.platform.AbstractHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class TopicsHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.ossmeter.metricprovider.historic.bugs.topics";

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
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public Pongo measure(Project project) {
		BugsTopicsHistoricMetric topics = new BugsTopicsHistoricMetric();
		if (uses.size()==1) {
			TopicsTransMetric usedTopics = ((TopicsTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 for (BugTrackerTopic bugTrackerTopic: usedTopics.getBugTrackerTopics()) {
				 BugTopic topic = new BugTopic();
				 topics.getBugTopics().add(topic);
				 topic.setBugTrackerId(bugTrackerTopic.getBugTrackerId());
				 topic.setLabel(bugTrackerTopic.getLabel());
				 topic.setNumberOfDocuments(bugTrackerTopic.getNumberOfDocuments());
			 }
		}
		return topics;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(TopicsTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "bugtopics";
	}

	@Override
	public String getFriendlyName() {
		return "Labels Of Bug Topics Per Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the labels of topics (thematci clusters) in comments submitted every day.";
	}
}
