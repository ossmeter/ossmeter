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
package org.ossmeter.factoid.newsgroups.users;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.newsgroups.threads.ThreadsHistoricMetricProvider;
import org.ossmeter.metricprovider.historic.newsgroups.threads.model.NewsgroupsThreadsHistoricMetric;
import org.ossmeter.metricprovider.historic.newsgroups.users.UsersHistoricMetricProvider;
import org.ossmeter.metricprovider.historic.newsgroups.users.model.DailyNewsgroupData;
import org.ossmeter.metricprovider.historic.newsgroups.users.model.NewsgroupsUsersHistoricMetric;
import org.ossmeter.platform.AbstractFactoidMetricProvider;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.factoids.Factoid;
import org.ossmeter.platform.factoids.StarRating;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class NewsgroupsChannelUsersFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "NewsgroupChannelUsers";
	}

	@Override
	public String getFriendlyName() {
		return "Newsgroup Channel Users";
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
		return Arrays.asList(UsersHistoricMetricProvider.IDENTIFIER,
							 ThreadsHistoricMetricProvider.IDENTIFIER);
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
//		factoid.setCategory(FactoidCategory.BUGS);
		factoid.setName(getFriendlyName());

		UsersHistoricMetricProvider usersProvider = null;
		ThreadsHistoricMetricProvider threadsProvider = null;

		for (IMetricProvider m : this.uses) {
			if (m instanceof UsersHistoricMetricProvider) {
				usersProvider = (UsersHistoricMetricProvider) m;
				continue;
			}
			if (m instanceof ThreadsHistoricMetricProvider) {
				threadsProvider = (ThreadsHistoricMetricProvider) m;
				continue;
			}
		}

		Date end = new Date();
		Date start = (new Date()).addDays(-30);
//		Date start=null, end=null;
//		try {
//			start = new Date("20040801");
//			end = new Date("20050801");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		List<Pongo> threadList = threadsProvider.getHistoricalMeasurements(context, project, start, end);

		List<Pongo> usersMonthList = usersProvider.getHistoricalMeasurements(context, project, start, end);
		
		start = (new Date()).addDays(-365);
		List<Pongo> usersYearList = usersProvider.getHistoricalMeasurements(context, project, start, end);
		
		StringBuffer stringBuffer = new StringBuffer();
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		
		int currentUsers = getCurrentNumberOfUsers(usersMonthList),
			currentActiveUsers = getCurrentNumberOfActiveUsers(usersMonthList);
		float currentActivePercentage = 0;
		if (currentUsers>0)
			currentActivePercentage = ( (float) 100 * currentActiveUsers ) / currentUsers; 

		stringBuffer.append("The community of the project contains ");
		stringBuffer.append(currentUsers);
		stringBuffer.append(" users, of which currently ");
		stringBuffer.append(currentActiveUsers);
		stringBuffer.append(" are active (");
		stringBuffer.append(decimalFormat.format(currentActivePercentage));
		stringBuffer.append(" %).\n");
		stringBuffer.append("Each user has contributed approximately ");
		stringBuffer.append(decimalFormat.format(getMessagesPerUser(threadList)));
		stringBuffer.append(" messages, "); 
		stringBuffer.append(decimalFormat.format(getMessagesPerRequests(threadList)));
		stringBuffer.append(" requests and ");
		stringBuffer.append(decimalFormat.format(getMessagesPerReplies(threadList)));
		stringBuffer.append(" replies.\n");

		float dailyNumberOfNewUsersInTheLastMonth = getDailyNumberOfNewUsersInDuration(usersMonthList),
			  dailyNumberOfActiveUsersInTheLastMonth = getDailyNumberOfActiveUsersInDuration(usersMonthList),
			  dailyNumberOfNewUsersInTheLastYear = getDailyNumberOfNewUsersInDuration(usersYearList),
			  dailyNumberOfActiveUsersInTheLastYear = getDailyNumberOfActiveUsersInDuration(usersYearList);
		
		stringBuffer.append("There are approximately "); 
		stringBuffer.append(decimalFormat.format(dailyNumberOfNewUsersInTheLastMonth));
		stringBuffer.append(" new users per day in the last month, while approximately ");
		stringBuffer.append(decimalFormat.format(dailyNumberOfActiveUsersInTheLastMonth));
		stringBuffer.append(" users are active.\n");
		stringBuffer.append("In the last year, there are "); 
		stringBuffer.append(decimalFormat.format(dailyNumberOfNewUsersInTheLastYear));
		stringBuffer.append(" new users per day, ");
		stringBuffer.append(decimalFormat.format(dailyNumberOfActiveUsersInTheLastYear));
		stringBuffer.append(" of which are active.\n"); 
		
		float newUsersThreshold = 0.25f,
			  activeUsersThreshold = 2.5f;
			
		if ( ( dailyNumberOfNewUsersInTheLastMonth > 8 * newUsersThreshold ) 
		  || ( dailyNumberOfActiveUsersInTheLastMonth > 8 * activeUsersThreshold )
		  || ( dailyNumberOfNewUsersInTheLastYear > 4 * newUsersThreshold ) 
		  || ( dailyNumberOfActiveUsersInTheLastYear > 4 * activeUsersThreshold ) ) {
			factoid.setStars(StarRating.FOUR);
		} else if ( ( dailyNumberOfNewUsersInTheLastMonth > 4 * newUsersThreshold ) 
				 || ( dailyNumberOfActiveUsersInTheLastMonth > 4 * activeUsersThreshold )
				 || ( dailyNumberOfNewUsersInTheLastYear > 2 * newUsersThreshold ) 
				 || ( dailyNumberOfActiveUsersInTheLastYear > 2 * activeUsersThreshold ) ) {
			factoid.setStars(StarRating.THREE);
		} else if ( ( dailyNumberOfNewUsersInTheLastMonth > 2 * newUsersThreshold ) 
			   || ( dailyNumberOfActiveUsersInTheLastMonth > 2 * activeUsersThreshold )
			   || ( dailyNumberOfNewUsersInTheLastYear > newUsersThreshold ) 
			   || ( dailyNumberOfActiveUsersInTheLastYear > activeUsersThreshold ) ) {
			factoid.setStars(StarRating.TWO);
		} else {
			factoid.setStars(StarRating.ONE);
		}

		if ( usersMonthList.size() > 0 ) {
			NewsgroupsUsersHistoricMetric usersPongo = 
					(NewsgroupsUsersHistoricMetric) usersMonthList.get(usersMonthList.size()-1);
			for (DailyNewsgroupData dailyNewsgroupData: usersPongo.getNewsgroups()) {
				int numberOfUsers = dailyNewsgroupData.getNumberOfUsers(),
					numberOfActiveUsers = dailyNewsgroupData.getNumberOfActiveUsers();
				float percentageOfActiveUsers = ( (float) 100 * numberOfActiveUsers ) / numberOfUsers;
				stringBuffer.append("Newsgroup ");
				stringBuffer.append(dailyNewsgroupData.getNewsgroupName());
				stringBuffer.append(" hosts ");
				stringBuffer.append(numberOfUsers);
				stringBuffer.append(" users, of which ");
				stringBuffer.append(numberOfActiveUsers);
				stringBuffer.append(" are currently active (");
				stringBuffer.append(decimalFormat.format(percentageOfActiveUsers));
				stringBuffer.append(" %).\n");
			}
		}
	
		factoid.setFactoid(stringBuffer.toString());

	}

	private int getCurrentNumberOfUsers(List<Pongo> usersList) {
		int numberOfUsers = 0;
		if ( usersList.size() > 0 ) {
			NewsgroupsUsersHistoricMetric usersPongo = 
					(NewsgroupsUsersHistoricMetric) usersList.get(usersList.size()-1);
			numberOfUsers = usersPongo.getNumberOfUsers();
		}
		return numberOfUsers;
	}

	private int getCurrentNumberOfActiveUsers(List<Pongo> usersList) {
		int numberOfActiveUsers = 0;
		if ( usersList.size() > 0 ) {
			NewsgroupsUsersHistoricMetric usersPongo = 
					(NewsgroupsUsersHistoricMetric) usersList.get(usersList.size()-1);
			numberOfActiveUsers = usersPongo.getNumberOfActiveUsers();
		}
		return numberOfActiveUsers;
	}

	private float getDailyNumberOfNewUsersInDuration(List<Pongo> usersList) {
		int numberOfNewUsers = 0;
		if ( usersList.size() > 0 ) {
			NewsgroupsUsersHistoricMetric firstUsersPongo = 
					(NewsgroupsUsersHistoricMetric) usersList.get(0);
			NewsgroupsUsersHistoricMetric lastUsersPongo = 
					(NewsgroupsUsersHistoricMetric) usersList.get(usersList.size()-1);
			int firstNumberOfUsers = firstUsersPongo.getNumberOfUsers();
			int lastNumberOfUsers = lastUsersPongo.getNumberOfUsers();
			return ( (float) (lastNumberOfUsers - firstNumberOfUsers) ) / usersList.size();
		}
		return numberOfNewUsers;
	}
	
	private float getDailyNumberOfActiveUsersInDuration(List<Pongo> usersList) {
		int numberOfActiveUsers = 0;
		if ( usersList.size() > 0 ) {
			for (Pongo pongo: usersList) {
				NewsgroupsUsersHistoricMetric usersPongo = (NewsgroupsUsersHistoricMetric) pongo;
				numberOfActiveUsers += usersPongo.getNumberOfActiveUsers();
			}
			return ( (float) numberOfActiveUsers ) / usersList.size();
		}
		return numberOfActiveUsers;
	}

	private float getMessagesPerUser(List<Pongo> threadList) {
		if ( threadList.size() > 0 ) {
			NewsgroupsThreadsHistoricMetric threadPongo = 
					(NewsgroupsThreadsHistoricMetric) threadList.get(threadList.size()-1);
			return threadPongo.getAverageArticlesPerUser();
		}
		return 0;
	}

	private float getMessagesPerRequests(List<Pongo> threadList) {
		if ( threadList.size() > 0 ) {
			NewsgroupsThreadsHistoricMetric threadPongo = 
					(NewsgroupsThreadsHistoricMetric) threadList.get(threadList.size()-1);
			return threadPongo.getAverageRequestsPerUser();
		}
		return 0;
	}
	
	private float getMessagesPerReplies(List<Pongo> threadList) {
		if ( threadList.size() > 0 ) {
			NewsgroupsThreadsHistoricMetric threadPongo = 
					(NewsgroupsThreadsHistoricMetric) threadList.get(threadList.size()-1);
			return threadPongo.getAverageRepliesPerUser();
		}
		return 0;
	}

}
