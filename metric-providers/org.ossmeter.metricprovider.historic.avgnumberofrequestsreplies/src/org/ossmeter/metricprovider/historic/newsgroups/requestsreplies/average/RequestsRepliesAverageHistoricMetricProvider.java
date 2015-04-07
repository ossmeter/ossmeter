/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.average;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.average.model.NewsgroupsRequestsRepliesAverageHistoricMetric;
import org.ossmeter.metricprovider.trans.newsgroups.activeusers.ActiveUsersTransMetricProvider;
import org.ossmeter.metricprovider.trans.newsgroups.activeusers.model.NewsgroupData;
import org.ossmeter.metricprovider.trans.newsgroups.activeusers.model.NewsgroupsActiveUsersTransMetric;
import org.ossmeter.metricprovider.trans.newsgroups.activeusers.model.User;
import org.ossmeter.platform.AbstractHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;
import org.ossmeter.repository.model.sourceforge.Discussion;

import com.googlecode.pongo.runtime.Pongo;

public class RequestsRepliesAverageHistoricMetricProvider extends AbstractHistoricalMetricProvider{
	public final static String IDENTIFIER = "org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.average";

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
			if (communicationChannel instanceof Discussion) return true;
		}
		return false;
	}

	@Override
	public Pongo measure(Project project) {

		if (uses.size()!=1) {
			System.err.println("Metric: avgnumberofrequestsreplies failed to retrieve " + 
								"the two transient metrics it needs!");
			System.exit(-1);
		}

		 NewsgroupsActiveUsersTransMetric usedUsers = 
				 ((ActiveUsersTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		int numberOfArticles = 0,
			numberOrRequests = 0,
			numberOrReplies = 0;
		for (User user: usedUsers.getUsers()) {
			numberOfArticles += user.getArticles();
			numberOrReplies += user.getReplies();
			numberOrRequests += user.getRequests();
		}
		int days = 0;
		for (NewsgroupData newsgroup: usedUsers.getNewsgroups()) {
			if (days < newsgroup.getDays())
				days = newsgroup.getDays();
		}
		
		float avgArticlesPerDay = 0,
			  avgRepliesPerDay = 0,
			  avgRequestsPerDay = 0;
		
		if (days>0) {
			avgArticlesPerDay = ((float) numberOfArticles) / days;
			avgRepliesPerDay = ((float) numberOrReplies) / days;
			avgRequestsPerDay = ((float) numberOrRequests) / days;
		}

		NewsgroupsRequestsRepliesAverageHistoricMetric avgRRThread = new NewsgroupsRequestsRepliesAverageHistoricMetric();
		avgRRThread.setAverageArticlesPerDay(avgArticlesPerDay);
		avgRRThread.setAverageRepliesPerDay(avgRepliesPerDay);
		avgRRThread.setAverageRequestsPerDay(avgRequestsPerDay);
		return avgRRThread;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(ActiveUsersTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "newsgroupsrequestsreplies";
	}

	@Override
	public String getFriendlyName() {
		return "Average Number of Articles, Requests and Replies Per Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This class computes the average number of articles, " +
				"request and reply newsgroup articles per day.";
	}

}
