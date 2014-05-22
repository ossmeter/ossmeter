package org.ossmeter.metricprovider.hourlyrequestsreplies;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.hourlyrequestsreplies.model.HourArticles;
import org.ossmeter.metricprovider.hourlyrequestsreplies.model.HourReplies;
import org.ossmeter.metricprovider.hourlyrequestsreplies.model.HourRequests;
import org.ossmeter.metricprovider.hourlyrequestsreplies.model.HourlyRequestsReplies;
import org.ossmeter.metricprovider.requestreplyclassification.RequestReplyClassificationMetricProvider;
import org.ossmeter.metricprovider.requestreplyclassification.model.NewsgroupArticlesData;
import org.ossmeter.metricprovider.requestreplyclassification.model.Rrc;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelProjectDelta;
import org.ossmeter.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.mongodb.DB;

public class HourlyRequestsRepliesProvider implements ITransientMetricProvider<HourlyRequestsReplies>{

	protected PlatformCommunicationChannelManager communicationChannelManager;

	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;

	@Override
	public String getIdentifier() {
		return HourlyRequestsRepliesProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
		}
		return false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(RequestReplyClassificationMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
	}

	@Override
	public HourlyRequestsReplies adapt(DB db) {
		return new HourlyRequestsReplies(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, HourlyRequestsReplies db) {
		
		String[] hoursOfDay = new String[]{"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
		for (String hour : hoursOfDay) {
			HourArticles hourArticles = db.getHourArticles().findOneByHour(hour+":00");
			if (hourArticles == null) {
				hourArticles = new HourArticles();
				hourArticles.setHour(hour+":00");
				hourArticles.setNumberOfArticles(0);
				db.getHourArticles().add(hourArticles);
				db.sync();
			}
			HourRequests hourRequests = db.getHourRequests().findOneByHour(hour+":00");
			if (hourRequests == null) {
				hourRequests = new HourRequests();
				hourRequests.setHour(hour+":00");
				hourRequests.setNumberOfRequests(0);
				db.getHourRequests().add(hourRequests);
				db.sync();
			}
			HourReplies hourReplies = db.getHourReplies().findOneByHour(hour+":00");
			if (hourReplies == null) {
				hourReplies = new HourReplies();
				hourReplies.setHour(hour+":00");
				hourReplies.setNumberOfReplies(0);
				db.getHourReplies().add(hourReplies);
				db.sync();
			}

		}

		CommunicationChannelProjectDelta delta = projectDelta.getCommunicationChannelDelta();
		
		Rrc usedClassifier = 
				((RequestReplyClassificationMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		for ( CommunicationChannelDelta communicationChannelDelta: delta.getCommunicationChannelSystemDeltas()) {
			CommunicationChannel communicationChannel = communicationChannelDelta.getCommunicationChannel();
			if (!(communicationChannel instanceof NntpNewsGroup)) continue;
			NntpNewsGroup newsgroup = (NntpNewsGroup) communicationChannel;
			
			List<CommunicationChannelArticle> articles = communicationChannelDelta.getArticles();
			for (CommunicationChannelArticle article: articles) {
				String hourNumber = String.format("%02d", article.getDate().getHours());
				
				HourArticles hourArticles = db.getHourArticles().findOneByHour(hourNumber + ":00");
				hourArticles.setNumberOfArticles(hourArticles.getNumberOfArticles()+1);

				String requestReplyClass = getRequestReplyClass(usedClassifier, newsgroup, article);
				if (requestReplyClass.equals("Request")) {
					HourRequests hourRequests = db.getHourRequests().findOneByHour(hourNumber + ":00");
					hourRequests.setNumberOfRequests(hourRequests.getNumberOfRequests()+1);
				} else if (requestReplyClass.equals("Reply")) {
					HourReplies hourReplies = db.getHourReplies().findOneByHour(hourNumber + ":00");
					hourReplies.setNumberOfReplies(hourReplies.getNumberOfReplies()+1);
				}
				db.sync();
			}
		}
	}

	private String getRequestReplyClass(Rrc usedClassifier, 
			NntpNewsGroup newsgroup, CommunicationChannelArticle article) {
		Iterable<NewsgroupArticlesData> newsgroupArticlesDataIt = usedClassifier.getNewsgroupArticles().
				find(NewsgroupArticlesData.URL.eq(newsgroup.getUrl()), 
						NewsgroupArticlesData.ARTICLENUMBER.eq(article.getArticleNumber()));
		NewsgroupArticlesData newsgroupArticleData = null;
		for (NewsgroupArticlesData art:  newsgroupArticlesDataIt) {
			newsgroupArticleData = art;
		}
		if (newsgroupArticleData == null) {
			System.err.println("Active users metric -\t" + 
					"there is no classification for article: " + article.getArticleNumber() +
					"\t of newsgroup: " + newsgroup.getUrl());
			System.exit(-1);
		} else{
			return newsgroupArticleData.getClassificationResult();
		}
		return "";
	}

	@Override
	public String getShortIdentifier() {
		return "hourlyrequestsreplies";
	}

	@Override
	public String getFriendlyName() {
		return "Number of Articles, Requests and Replies per Hour of the Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric stores the number of articles, " +
				"requests and replies for each hour of the day.";
	}

}
