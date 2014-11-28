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
package org.ossmeter.factoid.bugs.weekly;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.trans.bugs.dailyrequestsreplies.DailyRequestsRepliesTransMetricProvider;
import org.ossmeter.metricprovider.trans.bugs.dailyrequestsreplies.model.BugsDailyRequestsRepliesTransMetric;
import org.ossmeter.metricprovider.trans.bugs.dailyrequestsreplies.model.DayComments;
import org.ossmeter.platform.AbstractFactoidMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.factoids.Factoid;
import org.ossmeter.platform.factoids.StarRating;
import org.ossmeter.repository.model.Project;

public class BugsChannelWeeklyFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "BugChannelWeekly";
	}

	@Override
	public String getFriendlyName() {
		return "Bug Channel Weekly";
		// This method will NOT be removed in a later version.
	}

	@Override
	public String getSummaryInformation() {
		return "summaryblah"; // This method will NOT be removed in a later version.
	}

	@Override
	public boolean appliesTo(Project project) {
	    return !project.getBugTrackingSystems().isEmpty();	   
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
		factoid.setName("Bug Channel Weekly Factoid");

		BugsDailyRequestsRepliesTransMetric dailyRequestsRepliesTransMetric = 
				((DailyRequestsRepliesTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		float uniformPercentageOfComments = ( (float) 100 ) / 7;
		float maxPercentageOfComments = 0,
			  minPercentageOfComments = 0,
			  weekDaysSum = 0,
			  weekEndSum = 0;
		
		for (DayComments dayComments: dailyRequestsRepliesTransMetric.getDayComments()) {
			if ( dayComments.getPercentageOfComments() > maxPercentageOfComments )
				maxPercentageOfComments = dayComments.getPercentageOfComments();
			if ( dayComments.getPercentageOfComments() < minPercentageOfComments )
				minPercentageOfComments = dayComments.getPercentageOfComments(); 
			if ( dayComments.getName().equals("Saturday") || dayComments.getName().equals("Sunday") )
				weekEndSum += dayComments.getPercentageOfComments();
			else
				weekDaysSum += dayComments.getPercentageOfComments();
		}
		
		if ( maxPercentageOfComments < 2 * uniformPercentageOfComments ) {
			factoid.setStars(StarRating.FOUR);
		} else if ( maxPercentageOfComments < 3 * uniformPercentageOfComments ) {
			factoid.setStars(StarRating.THREE);
		} else if ( maxPercentageOfComments < 4 * uniformPercentageOfComments ) {
			factoid.setStars(StarRating.TWO);
		} else {
			factoid.setStars(StarRating.ONE);
		}

		StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("The number of comments, requests or replies, ");
		if ( maxPercentageOfComments - minPercentageOfComments < uniformPercentageOfComments )
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
