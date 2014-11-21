package org.ossmeter.metricprovider.trans.newsgroups.activeusers;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.trans.newsgroups.activeusers.model.NewsgroupData;
import org.ossmeter.metricprovider.trans.newsgroups.activeusers.model.NewsgroupsActiveUsersTransMetric;
import org.ossmeter.metricprovider.trans.newsgroups.activeusers.model.User;
import org.ossmeter.metricprovider.trans.requestreplyclassification.RequestReplyClassificationTransMetricProvider;
import org.ossmeter.metricprovider.trans.requestreplyclassification.model.NewsgroupArticles;
import org.ossmeter.metricprovider.trans.requestreplyclassification.model.RequestReplyClassificationTransMetric;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.communicationchannel.nntp.NntpUtil;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelProjectDelta;
import org.ossmeter.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.mongodb.DB;

public class ActiveUsersTransMetricProvider implements ITransientMetricProvider<NewsgroupsActiveUsersTransMetric>{

	protected final int STEP = 15;
	
	protected PlatformCommunicationChannelManager communicationChannelManager;

	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;

	@Override
	public String getIdentifier() {
		return ActiveUsersTransMetricProvider.class.getCanonicalName();
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
		return Arrays.asList(RequestReplyClassificationTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
	}

	@Override
	public NewsgroupsActiveUsersTransMetric adapt(DB db) {
		return new NewsgroupsActiveUsersTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, NewsgroupsActiveUsersTransMetric db) {
		CommunicationChannelProjectDelta delta = projectDelta.getCommunicationChannelDelta();
		
		RequestReplyClassificationTransMetric usedClassifier = 
				((RequestReplyClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		for ( CommunicationChannelDelta communicationChannelDelta: delta.getCommunicationChannelSystemDeltas()) {
			CommunicationChannel communicationChannel = communicationChannelDelta.getCommunicationChannel();
			if (!(communicationChannel instanceof NntpNewsGroup)) continue;
			NntpNewsGroup newsgroup = (NntpNewsGroup) communicationChannel;
			 NewsgroupData newsgroupData = db.getNewsgroups().findOneByNewsgroupName(newsgroup.getNewsGroupName());
			if (newsgroupData == null) {
				newsgroupData = new NewsgroupData();
				newsgroupData.setNewsgroupName(newsgroup.getNewsGroupName());
				newsgroupData.setPreviousUsers(0);
				newsgroupData.setDays(1);
				db.getNewsgroups().add(newsgroupData);
			} else {
				newsgroupData.setPreviousUsers(newsgroupData.getUsers());
				newsgroupData.setDays(newsgroupData.getDays()+1);
			}
			
			List<CommunicationChannelArticle> articles = communicationChannelDelta.getArticles();
			for (CommunicationChannelArticle article: articles) {
				Iterable<User> usersIt = db.getUsers().
						find(User.NEWSGROUPNAME.eq(newsgroup.getNewsGroupName()), 
								User.USERID.eq(article.getUser()));
				User user = null;
				for (User u:  usersIt) {
					user = u;
				}
				if (user == null) {
					user = new User();
					user.setNewsgroupName(newsgroup.getNewsGroupName());
					user.setUserId(article.getUser());
					user.setLastActivityDate(article.getDate().toString());
					user.setArticles(1);
					String requestReplyClass = getRequestReplyClass(usedClassifier, newsgroup, article);
					if (requestReplyClass.equals("Reply"))
						user.setReplies(1);
					else if (requestReplyClass.equals("Request"))
						user.setRequests(1);
					db.getUsers().add(user);
				} else {
					java.util.Date javaDate = NntpUtil.parseDate(user.getLastActivityDate());
					Date userDate = new Date(javaDate);
					Date articleDate = new Date(article.getDate());
					if (articleDate.compareTo(userDate)==1)
						user.setLastActivityDate(article.getDate().toString());
					user.setArticles(user.getArticles()+1);
					String requestReplyClass = getRequestReplyClass(usedClassifier, newsgroup, article);
					if (requestReplyClass.equals("Reply"))
						user.setReplies(user.getReplies()+1);
					else if (requestReplyClass.equals("Request"))
						user.setRequests(user.getRequests()+1);
				}
				db.sync();
			}
			
			Iterable<User> usersIt = db.getUsers().findByNewsgroupName(newsgroup.getNewsGroupName());
			int users = 0,
				activeUsers = 0,
				inactiveUsers = 0;
			for (User user:  usersIt) {
				Boolean active = true;
				users++;
				java.util.Date javaDate = NntpUtil.parseDate(user.getLastActivityDate());
				if (javaDate!=null) {
					Date date = new Date(javaDate);
					if (projectDelta.getDate().compareTo(date.addDays(STEP)) >0) {
						active=false;
					}
				} else
					active=false;
				if (active) activeUsers++;
				else inactiveUsers++;
			}
			newsgroupData.setActiveUsers(activeUsers);
			newsgroupData.setInactiveUsers(inactiveUsers);
			newsgroupData.setUsers(users);
			db.sync();
		}
	}

	private String getRequestReplyClass(RequestReplyClassificationTransMetric usedClassifier, 
			NntpNewsGroup newsgroup, CommunicationChannelArticle article) {
		Iterable<NewsgroupArticles> newsgroupArticlesIt = usedClassifier.getNewsgroupArticles().
				find(NewsgroupArticles.NEWSGROUPNAME.eq(newsgroup.getNewsGroupName()), 
						NewsgroupArticles.ARTICLENUMBER.eq(article.getArticleNumber()));
		NewsgroupArticles newsgroupArticleData = null;
		for (NewsgroupArticles art:  newsgroupArticlesIt) {
			newsgroupArticleData = art;
		}
		if (newsgroupArticleData == null) {
			System.err.println("Active users metric -\t" + 
					"there is no classification for article: " + article.getArticleNumber() +
					"\t of newsgroup: " + newsgroup.getNewsGroupName());
//			System.exit(-1);
		} else
			return newsgroupArticleData.getClassificationResult();
		return "";
	}

	@Override
	public String getShortIdentifier() {
		return "activeusers";
	}

	@Override
	public String getFriendlyName() {
		return "Active Users";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric keeps track of the users that submitted news comments " +
				"in the last 15 days.";
	}

}
