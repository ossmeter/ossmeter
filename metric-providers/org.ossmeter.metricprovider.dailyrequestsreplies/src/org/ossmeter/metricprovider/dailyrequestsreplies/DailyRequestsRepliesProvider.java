package org.ossmeter.metricprovider.dailyrequestsreplies;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.ossmeter.metricprovider.dailyrequestsreplies.model.DailyRequestsReplies;
import org.ossmeter.metricprovider.dailyrequestsreplies.model.DayArticles;
import org.ossmeter.metricprovider.dailyrequestsreplies.model.DayReplies;
import org.ossmeter.metricprovider.dailyrequestsreplies.model.DayRequests;
import org.ossmeter.metricprovider.requestreplyclassification.RequestReplyClassificationMetricProvider;
import org.ossmeter.metricprovider.requestreplyclassification.model.NewsgroupArticlesData;
import org.ossmeter.metricprovider.requestreplyclassification.model.Rrc;
import org.ossmeter.platform.Date;
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

public class DailyRequestsRepliesProvider implements ITransientMetricProvider<DailyRequestsReplies>{

	protected PlatformCommunicationChannelManager communicationChannelManager;

	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;

	@Override
	public String getIdentifier() {
		return DailyRequestsRepliesProvider.class.getCanonicalName();
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
	public DailyRequestsReplies adapt(DB db) {
		return new DailyRequestsReplies(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, DailyRequestsReplies db) {
		
		String[] daysOfWeek = new String[]{"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

		for (String d : daysOfWeek) {
			DayArticles dayArticles = db.getDayArticles().findOneByName(d);
			if (dayArticles == null) {
				dayArticles = new DayArticles();
				dayArticles.setName(d);
				dayArticles.setNumberOfArticles(0);
				db.getDayArticles().add(dayArticles);
				db.sync();
			}
			DayRequests dayRequests = db.getDayRequests().findOneByName(d);
			if (dayRequests == null) {
				dayRequests = new DayRequests();
				dayRequests.setName(d);
				dayRequests.setNumberOfRequests(0);
				db.getDayRequests().add(dayRequests);
				db.sync();
			}
			DayReplies dayReplies = db.getDayReplies().findOneByName(d);
			if (dayReplies == null) {
				dayReplies = new DayReplies();
				dayReplies.setName(d);
				dayReplies.setNumberOfReplies(0);
				db.getDayReplies().add(dayReplies);
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

				Date date = new Date(article.getDate());
				Calendar cal = Calendar.getInstance();
				cal.setTime(date.toJavaDate());
				int dow = cal.get(Calendar.DAY_OF_WEEK) - 1;
				String dayName = daysOfWeek[dow];

				DayArticles dayArticles = db.getDayArticles().findOneByName(dayName);
				dayArticles.setNumberOfArticles(dayArticles.getNumberOfArticles()+1);

				String requestReplyClass = getRequestReplyClass(usedClassifier, newsgroup, article);
				if (requestReplyClass.equals("Reply")) {
					DayRequests dayRequests = db.getDayRequests().findOneByName(dayName);
					dayRequests.setNumberOfRequests(dayRequests.getNumberOfRequests()+1);
				} else if (requestReplyClass.equals("Request")) {
					DayReplies dayReplies = db.getDayReplies().findOneByName(dayName);
					dayReplies.setNumberOfReplies(dayReplies.getNumberOfReplies()+1);
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
		} else
			return newsgroupArticleData.getClassificationResult();
		return "";
	}

	@Override
	public String getShortIdentifier() {
		return "dailyrequestsreplies";
	}

	@Override
	public String getFriendlyName() {
		return "Number of Articles, Requests and Replies per Day of the Week";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric stores the number of articles, " +
				"requests and replies for each day of the week.";
	}

}
