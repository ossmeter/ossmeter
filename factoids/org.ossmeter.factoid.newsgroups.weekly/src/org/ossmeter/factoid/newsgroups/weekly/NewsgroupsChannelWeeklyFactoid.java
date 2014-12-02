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
package org.ossmeter.factoid.newsgroups.weekly;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.trans.newsgroups.dailyrequestsreplies.DailyRequestsRepliesTransMetricProvider;
import org.ossmeter.metricprovider.trans.newsgroups.dailyrequestsreplies.model.DayArticles;
import org.ossmeter.metricprovider.trans.newsgroups.dailyrequestsreplies.model.NewsgroupsDailyRequestsRepliesTransMetric;
import org.ossmeter.platform.AbstractFactoidMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.factoids.Factoid;
import org.ossmeter.platform.factoids.StarRating;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

public class NewsgroupsChannelWeeklyFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "NewsgroupChannelWeekly";
	}

	@Override
	public String getFriendlyName() {
		return "Newsgroup Channel Weekly";
		// This method will NOT be removed in a later version.
	}

	@Override
	public String getSummaryInformation() {
		return "summaryblah"; // This method will NOT be removed in a later version.
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
		}
		return false;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(DailyRequestsRepliesTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
//		factoid.setCategory(FactoidCategory.BUGS);
		factoid.setName(getFriendlyName());

		NewsgroupsDailyRequestsRepliesTransMetric dailyRequestsRepliesTransMetric = 
				((DailyRequestsRepliesTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		float uniformPercentageOfComments = ( (float) 100 ) / 7;
		float maxPercentageOfArticles = 0,
			  minPercentageOfArticles = 0,
			  weekDaysSum = 0,
			  weekEndSum = 0;
		
		for (DayArticles dayArticles: dailyRequestsRepliesTransMetric.getDayArticles()) {
			if ( dayArticles.getPercentageOfArticles() > maxPercentageOfArticles )
				maxPercentageOfArticles = dayArticles.getPercentageOfArticles();
			if ( dayArticles.getPercentageOfArticles() < minPercentageOfArticles )
				minPercentageOfArticles = dayArticles.getPercentageOfArticles(); 
			if ( dayArticles.getName().equals("Saturday") || dayArticles.getName().equals("Sunday") )
				weekEndSum += dayArticles.getPercentageOfArticles();
			else
				weekDaysSum += dayArticles.getPercentageOfArticles();
		}
		
		if ( maxPercentageOfArticles < 2 * uniformPercentageOfComments ) {
			factoid.setStars(StarRating.FOUR);
		} else if ( maxPercentageOfArticles < 3 * uniformPercentageOfComments ) {
			factoid.setStars(StarRating.THREE);
		} else if ( maxPercentageOfArticles < 4 * uniformPercentageOfComments ) {
			factoid.setStars(StarRating.TWO);
		} else {
			factoid.setStars(StarRating.ONE);
		}

		StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("The number of articles, requests or replies, ");
		if ( maxPercentageOfArticles - minPercentageOfArticles < uniformPercentageOfComments )
			stringBuffer.append("does not depend");
		else
			stringBuffer.append("largely depends");
		stringBuffer.append(" on the day of the week.\nThere is");
		if ( Math.abs( ( weekDaysSum / 5 ) - ( weekEndSum / 2 ) ) < uniformPercentageOfComments ) 
			stringBuffer.append(" no ");
		else
			stringBuffer.append(" ");
		stringBuffer.append("significant difference between the number of comments during working days and weekends.\n");

		factoid.setFactoid(stringBuffer.toString());
	
	}

}
