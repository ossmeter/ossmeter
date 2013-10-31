package org.ossmeter.metricprovider.generic.unansweredthreadsperdaypernewsgroup;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.ossmeter.metricprovider.generic.unansweredthreadsperdaypernewsgroup.model.DailyNewsgroupData;
import org.ossmeter.metricprovider.generic.unansweredthreadsperdaypernewsgroup.model.DailyUnansweredThreads;
import org.ossmeter.metricprovider.requestreplyclassification.RequestReplyClassificationMetricProvider;
import org.ossmeter.metricprovider.requestreplyclassification.model.NewsgroupArticlesData;
import org.ossmeter.metricprovider.requestreplyclassification.model.Rrc;
import org.ossmeter.metricprovider.threads.ThreadsMetricProvider;
import org.ossmeter.metricprovider.threads.model.ArticleData;
import org.ossmeter.metricprovider.threads.model.ArticleDataComparator;
import org.ossmeter.metricprovider.threads.model.Threads;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class UnsweredThreadsPerDayPerNewsgroupProvider implements IHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.generic.unansweredthreadsperdaypernewsgroup";

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

		if (uses.size()!=2) {
			System.err.println("Metric: unansweredthreadsperdaypernewsgroup failed to retrieve " + 
								"the two transient metrics it needs!");
			System.exit(-1);
		}

		Threads usedThreads = 
				((ThreadsMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		Rrc usedClassifier = 
				((RequestReplyClassificationMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));

		HashSet<ArticleData> articleSet = new HashSet<ArticleData>();
		for (ArticleData article: usedThreads.getArticles())
			articleSet.add(article);
		SortedSet<ArticleData> sortedArticleSet = new TreeSet<ArticleData>(new ArticleDataComparator());
		sortedArticleSet.addAll(articleSet);
		
		DailyUnansweredThreads dailyUnansweredThreads = new DailyUnansweredThreads();

		String lastUrl_name = "";
		int lastThreadId = -1, unansweredThreads = 0;
		Boolean answered = false;
		for (ArticleData article: sortedArticleSet) {
			if (!article.getUrl_name().equals(lastUrl_name)) {
				if (lastUrl_name.length()>0) {
					if (!answered)
						unansweredThreads++;
					if (unansweredThreads > 0) {
						dailyUnansweredThreads.getNewsgroups().add(prepareNewsgroupData(lastUrl_name, unansweredThreads));
					}
				}
				lastUrl_name = article.getUrl_name();
				lastThreadId = article.getThreadId();
				unansweredThreads = 0;
				answered = false;
			} else if (article.getThreadId() != lastThreadId) {
				if (!answered)
					unansweredThreads++;
				lastThreadId = article.getThreadId();
			}
			
			Iterable<NewsgroupArticlesData> newsgroupArticlesDataIt = usedClassifier.getNewsgroupArticles().
					find(NewsgroupArticlesData.URL.eq(article.getUrl_name()), 
							NewsgroupArticlesData.ARTICLENUMBER.eq(article.getArticleNumber()));
			NewsgroupArticlesData newsgroupArticleData = null;
			for (NewsgroupArticlesData art:  newsgroupArticlesDataIt) {
				newsgroupArticleData = art;
			}
			if (newsgroupArticleData == null) {
				System.err.println("Metric: " + IDENTIFIER + "\n\t" + 
								   "there is no classification for article: " + article.getArticleNumber() +
								   "\t of newsgroup: " + article.getUrl_name());
				System.exit(-1);
			} else if (newsgroupArticleData.getClassificationResult().equals("Reply")) {
				answered = true;
			}
		}
		if (!answered)
			unansweredThreads++;
		if (unansweredThreads > 0) {
			dailyUnansweredThreads.getNewsgroups().add(prepareNewsgroupData(lastUrl_name, unansweredThreads));
		}
		return dailyUnansweredThreads;
	}
			
	private DailyNewsgroupData prepareNewsgroupData(String url_name, int unansweredThreads) {
		DailyNewsgroupData dailyNewsgroupData = new DailyNewsgroupData();
		dailyNewsgroupData.setUrl_name(url_name);
		dailyNewsgroupData.setNumberOfUnansweredThreads(unansweredThreads);
		return dailyNewsgroupData;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(ThreadsMetricProvider.class.getCanonicalName(),
							 RequestReplyClassificationMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "unansweredthreadspernewsgroup";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Unanswered Threads Per Day Per Newsgroup";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of unanswered threads per day for each newsgroup separately.";
	}
}
