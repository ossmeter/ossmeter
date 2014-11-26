package org.ossmeter.factoid.newsgroups.size;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.ossmeter.metricprovider.historic.newsgroups.articles.ArticlesHistoricMetricProvider;
import org.ossmeter.metricprovider.historic.newsgroups.articles.model.DailyNewsgroupData;
import org.ossmeter.metricprovider.historic.newsgroups.articles.model.NewsgroupsArticlesHistoricMetric;
import org.ossmeter.metricprovider.historic.newsgroups.newthreads.NewThreadsHistoricMetricProvider;
import org.ossmeter.metricprovider.historic.newsgroups.newthreads.model.NewsgroupsNewThreadsHistoricMetric;
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

public class NewsgroupsChannelSizeFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "NewsgroupChannelSize";
	}

	@Override
	public String getFriendlyName() {
		return "Newsgroup Channel Size";
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
		return Arrays.asList(ArticlesHistoricMetricProvider.IDENTIFIER,
							 NewThreadsHistoricMetricProvider.IDENTIFIER);
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
//		factoid.setCategory(FactoidCategory.NEWSGROUPS);
		factoid.setName(getFriendlyName());

		ArticlesHistoricMetricProvider articlesProvider = new ArticlesHistoricMetricProvider();
		NewThreadsHistoricMetricProvider newThreadsProvider = new NewThreadsHistoricMetricProvider();

		for (IMetricProvider m : this.uses) {
			if (m instanceof ArticlesHistoricMetricProvider) {
				articlesProvider = (ArticlesHistoricMetricProvider) m;
				continue;
			}
			if (m instanceof NewThreadsHistoricMetricProvider) {
				newThreadsProvider = (NewThreadsHistoricMetricProvider) m;
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
		List<Pongo> articlesList = articlesProvider.getHistoricalMeasurements(context, project, start, end),
					newThreadsList = newThreadsProvider.getHistoricalMeasurements(context, project, start, end);
		
//		System.err.println("---SIZE===RETRIEVED PONGOLIST FOR " + articlesList.size() + " DAYS===---");
		
		Map<String, Integer> trackerArticles = new HashMap<String, Integer>();
		int numberOfArticles = getCumulativeNumberOfArticles(articlesList, trackerArticles);

		Map<String, Integer> trackerNewThreads = new HashMap<String, Integer>();
		int numberOfNewThreads = getCumulativeNumberOfThreads(newThreadsList, trackerNewThreads);

		int threshold = 1000;
		
		if ( (numberOfArticles > 10 * threshold) || (numberOfNewThreads > threshold) ) {
			factoid.setStars(StarRating.FOUR);
		} else if ( (2 * numberOfArticles > 10 * threshold) || (2 * numberOfNewThreads > threshold) ) {
			factoid.setStars(StarRating.THREE);
		} else if ( (4 * numberOfArticles > 10 * threshold) || (4 * numberOfNewThreads > threshold) ) {
			factoid.setStars(StarRating.TWO);
		} else
			factoid.setStars(StarRating.ONE);
		
		StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("The project is associated with ");
		stringBuffer.append(project.getCommunicationChannels().size());
		if (project.getBugTrackingSystems().size()==1)
			stringBuffer.append(" newsgroup.\n");
		else
			stringBuffer.append(" newsgroups.\n");
			
		for (String tracker: sortByKeys(trackerArticles)) {
			
			int articles = trackerArticles.get(tracker);
			int threads = trackerNewThreads.get(tracker);

			stringBuffer.append("Newsgroup ");
			stringBuffer.append(tracker);
			stringBuffer.append(" is of ");
			
			if ( (articles > 10 * threshold) || (threads > threshold) ) {
				stringBuffer.append("very large");
			} else if ( (2 * articles > 10 * threshold) || (2 * threads > threshold) ) {
				stringBuffer.append("large");
			} else if ( (4 * articles > 10 * threshold) || (4 * threads > threshold) ) {
				stringBuffer.append("medium");
			} else
				stringBuffer.append("small");
			stringBuffer.append(" size. It contains ");
			stringBuffer.append(threads);
			stringBuffer.append(" threads and ");
			stringBuffer.append(articles);
			stringBuffer.append(" articles, in total.\n");
		
		}
		
		factoid.setFactoid(stringBuffer.toString());

	}

	private int getCumulativeNumberOfArticles(List<Pongo> newArticlesList, Map<String, Integer> trackerArticles) {
		int sum = 0;
		if ( newArticlesList.size() > 0 ) {
			NewsgroupsArticlesHistoricMetric newArticlesPongo = 
					(NewsgroupsArticlesHistoricMetric) newArticlesList.get(newArticlesList.size()-1);
			for (DailyNewsgroupData newsgroupData: newArticlesPongo.getNewsgroups()) {
				int articles = newsgroupData.getCumulativeNumberOfArticles();
				trackerArticles.put(newsgroupData.getNewsgroupName(), articles);
				sum += articles;
			}
		}
		return sum;
	}
	
	private int getCumulativeNumberOfThreads(List<Pongo> newThreadsList, Map<String, Integer> trackerNewThreads) {
		int sum = 0;
		for (Pongo pongo: newThreadsList) {
			NewsgroupsNewThreadsHistoricMetric commentsPongo = (NewsgroupsNewThreadsHistoricMetric) pongo;
			for (org.ossmeter.metricprovider.historic.newsgroups.newthreads.model.DailyNewsgroupData 
					newsgroupData: commentsPongo.getNewsgroups()) {
				int comments = newsgroupData.getCumulativeNumberOfNewThreads();
				trackerNewThreads.put(newsgroupData.getNewsgroupName(), comments);
				sum += comments;
			}
		}
		return sum;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private SortedSet<String> sortByKeys(Map<String, ?> map) {
		return new TreeSet(map.keySet());
	}

}
