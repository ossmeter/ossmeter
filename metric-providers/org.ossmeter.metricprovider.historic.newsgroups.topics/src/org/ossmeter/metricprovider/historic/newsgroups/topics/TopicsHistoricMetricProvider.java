package org.ossmeter.metricprovider.historic.newsgroups.topics;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.newsgroups.topics.model.NewsgroupTopicsHistoricMetric;
import org.ossmeter.metricprovider.historic.newsgroups.topics.model.NewsgrpTopic;
import org.ossmeter.metricprovider.trans.topics.TopicsTransMetricProvider;
import org.ossmeter.metricprovider.trans.topics.model.NewsgroupTopic;
import org.ossmeter.metricprovider.trans.topics.model.TopicsTransMetric;
import org.ossmeter.platform.AbstractHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class TopicsHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.ossmeter.metricprovider.historic.newsgroups.topics";

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
		for (CommunicationChannel communicationchannel: project.getCommunicationChannels()) {
			if (communicationchannel instanceof NntpNewsGroup) return true;
		}
		return false;
	}

	@Override
	public Pongo measure(Project project) {
		NewsgroupTopicsHistoricMetric topics = new NewsgroupTopicsHistoricMetric();
		if (uses.size()==1) {
			TopicsTransMetric usedTopics = ((TopicsTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 for (NewsgroupTopic newsgroupTopic: usedTopics.getNewsgroupTopics()) {
				 NewsgrpTopic topic = new NewsgrpTopic();
				 topics.getNewsgrpTopics().add(topic);
				 topic.setUrl(newsgroupTopic.getUrl());
				 topic.setLabel(newsgroupTopic.getLabel());
				 topic.setNumberOfDocuments(newsgroupTopic.getNumberOfDocuments());
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
		return "newsgrouptopics";
	}

	@Override
	public String getFriendlyName() {
		return "Labels Of Newsgroup Topics Per Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the labels of topics (thematci clusters) in articles submitted every day.";
	}
}
