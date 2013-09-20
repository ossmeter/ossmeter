package org.ossmeter.metricprovider.activeusers;

import java.util.ArrayList;
import java.util.List;

import org.ossmeter.metricprovider.activeusers.model.ActiveUsers;
import org.ossmeter.metricprovider.activeusers.model.NewsgroupData;
import org.ossmeter.metricprovider.activeusers.model.User;
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

public class ActiveUsersMetricProvider implements ITransientMetricProvider<ActiveUsers>{

	protected final int STEP = 15;
	
	protected PlatformCommunicationChannelManager communicationChannelManager;

	@Override
	public String getIdentifier() {
		return ActiveUsersMetricProvider.class.getCanonicalName();
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
		// DO NOTHING -- we don't use anything
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return null;
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
	}

	@Override
	public ActiveUsers adapt(DB db) {
		return new ActiveUsers(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, ActiveUsers db) {
		System.out.println("ActiveUsersMetric");
		CommunicationChannelProjectDelta delta = projectDelta.getCommunicationChannelDelta();
		for ( CommunicationChannelDelta communicationChannelDelta: delta.getCommunicationChannelSystemDeltas()) {
			CommunicationChannel communicationChannel = communicationChannelDelta.getCommunicationChannel();
			if (!(communicationChannel instanceof NntpNewsGroup)) continue;
			NntpNewsGroup newsgroup = (NntpNewsGroup) communicationChannel;
			NewsgroupData newsgroupData = db.getNewsgroups().findOneByUrl_name(newsgroup.getUrl());
			if (newsgroupData == null) {
				newsgroupData = new NewsgroupData();
				newsgroupData.setUrl_name(newsgroup.getUrl());
				db.getNewsgroups().add(newsgroupData);
			} 
			List<CommunicationChannelArticle> articles = communicationChannelDelta.getArticles();
			for (CommunicationChannelArticle article: articles) {
				Iterable<User> usersIt = db.getUsers().
						find(User.URL_NAME.eq(newsgroup.getUrl()), 
								User.USERID.eq(article.getUser()));
				User user = null;
				for (User u:  usersIt) {
					user = u;
				}
				if (user == null) {
					user = new User();
					user.setUrl_name(newsgroup.getUrl());
					user.setUserId(article.getUser());
					db.getUsers().add(user);
					user.setLastActivityDate(article.getDate().toString());
				} else {
					java.util.Date javaDate = NntpUtil.parseDate(user.getLastActivityDate());
					Date userDate = new Date(javaDate);
					Date articleDate = new Date(article.getDate());
					if (articleDate.compareTo(userDate)==1)
						user.setLastActivityDate(article.getDate().toString());
				}
				db.sync();
			}
			
			Iterable<User> usersIt = db.getUsers().findByUrl_name(newsgroup.getUrl());
			List<User> usersToRemove = new ArrayList<User>();
			int numberOfUsers = 0;
			for (User user:  usersIt) {
				numberOfUsers++;
				java.util.Date javaDate = NntpUtil.parseDate(user.getLastActivityDate());
				if (javaDate!=null) {
					Date date = new Date(javaDate);
					if (projectDelta.getDate().compareTo(date.addDays(STEP)) >0) {
						usersToRemove.add(user);
					}
				}
			}
			for (User user: usersToRemove) {
				db.getUsers().remove(user);
			}
			
			newsgroupData.setNumberOfActiveUsers(numberOfUsers - usersToRemove.size());
			db.sync();
		}
	}

	@Override
	public String getShortIdentifier() {
		return "activeusersmetricprovider";
	}

	@Override
	public String getFriendlyName() {
		return "Active Users Metric Provider";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric keeps track of the users that submitted news comments " +
				"in the last 15 days.";
	}

}
