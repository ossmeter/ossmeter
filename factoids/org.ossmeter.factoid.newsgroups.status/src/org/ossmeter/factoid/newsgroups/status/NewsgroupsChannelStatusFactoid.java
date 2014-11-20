package org.ossmeter.factoid.newsgroups.status;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.RequestsRepliesHistoricMetricProvider;
import org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.average.RequestsRepliesAverageHistoricMetricProvider;
import org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.average.model.NewsgroupsRequestsRepliesAverageHistoricMetric;
import org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.model.NewsgroupsRequestsRepliesHistoricMetric;
import org.ossmeter.metricprovider.historic.newsgroups.unansweredthreads.UnansweredThreadsHistoricMetricProvider;
import org.ossmeter.metricprovider.historic.newsgroups.unansweredthreads.model.NewsgroupsUnansweredThreadsHistoricMetric;
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

public class NewsgroupsChannelStatusFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "NewsgroupChannelStatus";
	}

	@Override
	public String getFriendlyName() {
		return "Newsgroup Channel Status";
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
		return Arrays.asList(NewsgroupsUnansweredThreadsHistoricMetric.class.getCanonicalName(),
							 NewsgroupsRequestsRepliesHistoricMetric.class.getCanonicalName(),
							 NewsgroupsRequestsRepliesAverageHistoricMetric.class.getCanonicalName());
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
//		factoid.setCategory(FactoidCategory.BUGS);
		factoid.setName("");
		factoid.setName("Newsgroup Channel Status Factoid");

		UnansweredThreadsHistoricMetricProvider unansweredThreadsProvider = 
				new UnansweredThreadsHistoricMetricProvider();
		RequestsRepliesHistoricMetricProvider requestsRepliesProvider = 
				new RequestsRepliesHistoricMetricProvider();
		RequestsRepliesAverageHistoricMetricProvider requestsRepliesAverageProvider = 
				new RequestsRepliesAverageHistoricMetricProvider();
		
		Date end = new Date();
		Date start = new Date();
		List<Pongo> unansweredThreadsList = 
						unansweredThreadsProvider.getHistoricalMeasurements(context, project, start, end),
					requestsRepliesList = 
						requestsRepliesProvider.getHistoricalMeasurements(context, project, start, end),
					averageRequestsRepliesList = 
						requestsRepliesAverageProvider.getHistoricalMeasurements(context, project, start, end);
		
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		StringBuffer stringBuffer = new StringBuffer();		

		int numberOfRequests = getNumberOfRequests(requestsRepliesList),
			numberOfReplies = getNumberOfReplies(requestsRepliesList),
			numberOfArticles = numberOfReplies + numberOfRequests,
			numberOfUnsweredThreads = getNumberOfUnansweredThreads(unansweredThreadsList);
				
		float percentageOfRequests = ( (float) 100 * numberOfRequests ) / numberOfArticles,
			  percentageOfReplies = ( (float) 100 * numberOfReplies ) / numberOfArticles,
			  numberOfRequestsPerDay = getNumberOfRequestsPerDay(averageRequestsRepliesList),
			  numberOfRepliesPerDay = getNumberOfRepliesPerDay(averageRequestsRepliesList),
			  numberOfArticlesPerDay = numberOfRequestsPerDay + numberOfRepliesPerDay,
			  percentageOfRequestsPerDay = ( (float) 100 * numberOfRequestsPerDay ) / numberOfArticlesPerDay,
			  percentageOfRepliesPerDay = ( (float) 100 * numberOfRepliesPerDay ) / numberOfArticlesPerDay;

		stringBuffer.append("The project's newsgroup ");
		stringBuffer.append(" hosts ");
		stringBuffer.append(numberOfRequests);
		stringBuffer.append(" requests (");
		stringBuffer.append(percentageOfRequests);
		stringBuffer.append(" %) and ");
		stringBuffer.append(numberOfReplies);
		stringBuffer.append(" replies (");
		stringBuffer.append(percentageOfReplies);
		stringBuffer.append(" %), showing that requests are on average replied ");
		if ( numberOfReplies > 75 ) {
			stringBuffer.append("excellent");
			factoid.setStars(StarRating.FOUR);
		}
		else if ( numberOfReplies > 50 ) {
			stringBuffer.append("well");
			factoid.setStars(StarRating.THREE);
		}
		else if ( numberOfReplies > 25 ) {
			stringBuffer.append("fairly well");
			factoid.setStars(StarRating.TWO);
		}
		else {
			stringBuffer.append("inadequately");
			factoid.setStars(StarRating.ONE);
		}
		
		stringBuffer.append(".\n");
		
		stringBuffer.append("In total ");
		stringBuffer.append(numberOfUnsweredThreads);
		stringBuffer.append(" threads are unanswered.\n");
		
		stringBuffer.append("There are approximately ");
		stringBuffer.append(decimalFormat.format(numberOfRequestsPerDay));
		stringBuffer.append(" requests (");
		stringBuffer.append(decimalFormat.format(percentageOfRequestsPerDay));
		stringBuffer.append(" %) and ");
		stringBuffer.append(decimalFormat.format(numberOfRepliesPerDay));
		stringBuffer.append(" replies (");
		stringBuffer.append(decimalFormat.format(percentageOfRepliesPerDay));
		stringBuffer.append(" %) per day.\n");
		
		factoid.setFactoid(stringBuffer.toString());

	}

	private float getNumberOfRequestsPerDay(
			List<Pongo> averageRequestsRepliesList) {
		if (averageRequestsRepliesList.size() > 0) {
			NewsgroupsRequestsRepliesAverageHistoricMetric threadsPongo = 
					(NewsgroupsRequestsRepliesAverageHistoricMetric) 
							averageRequestsRepliesList.get(averageRequestsRepliesList.size() - 1);
			return threadsPongo.getAverageRequestsPerDay();
		}
		return 0;
	}

	private float getNumberOfRepliesPerDay(
			List<Pongo> averageRequestsRepliesList) {
		if (averageRequestsRepliesList.size() > 0) {
			NewsgroupsRequestsRepliesAverageHistoricMetric threadsPongo = 
					(NewsgroupsRequestsRepliesAverageHistoricMetric) 
							averageRequestsRepliesList.get(averageRequestsRepliesList.size() - 1);
			return threadsPongo.getAverageRepliesPerDay();
		}
		return 0;
	}

	private int getNumberOfRequests(List<Pongo> requestsRepliesList) {
		if (requestsRepliesList.size() > 0) {
			NewsgroupsRequestsRepliesHistoricMetric threadsPongo = 
					(NewsgroupsRequestsRepliesHistoricMetric) 
							requestsRepliesList.get(requestsRepliesList.size() - 1);
			return threadsPongo.getNumberOfRequests();
		}
		return 0;
	}

	private int getNumberOfReplies(List<Pongo> requestsRepliesList) {
		if (requestsRepliesList.size() > 0) {
			NewsgroupsRequestsRepliesHistoricMetric threadsPongo = 
					(NewsgroupsRequestsRepliesHistoricMetric) 
							requestsRepliesList.get(requestsRepliesList.size() - 1);
			return threadsPongo.getNumberOfReplies();
		}
		return 0;
	}

	private int getNumberOfUnansweredThreads(List<Pongo> unansweredThreadsList) {
		if (unansweredThreadsList.size() > 0) {
			NewsgroupsUnansweredThreadsHistoricMetric unansweredThreadsPongo = 
					(NewsgroupsUnansweredThreadsHistoricMetric) 
							unansweredThreadsList.get(unansweredThreadsList.size() - 1);
			return unansweredThreadsPongo.getNumberOfUnansweredThreads();
		}
		return 0;
	}
	
}
