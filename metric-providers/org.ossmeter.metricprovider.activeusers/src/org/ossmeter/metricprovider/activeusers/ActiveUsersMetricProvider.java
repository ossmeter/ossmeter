package org.ossmeter.metricprovider.activeusers;

import java.util.ArrayList;
import java.util.List;

import org.ossmeter.metricprovider.activeusers.model.ActiveUsers;
import org.ossmeter.metricprovider.activeusers.model.User;
import org.ossmeter.metricprovider.activeusers.model.NewsgroupData;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.communicationchannel.nntp.*;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelProjectDelta;
import org.ossmeter.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.NntpNewsGroup;
import org.ossmeter.repository.model.Project;


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
				User User = new User();
				User.setUserId(article.getUser());
				User.setLastActivityDate(article.getDate().toString());
				newsgroupData.getUsers().add(User);
			}
			List<User> Users = newsgroupData.getUsers();
			
			List<User> UsersToRemove = new ArrayList<User>();
			for (User User: Users) {
				java.util.Date javaDate = NntpUtil.parseDate(User.getLastActivityDate());
				if (javaDate!=null) {
					Date date = new Date(javaDate);
					if (projectDelta.getDate().compareTo(date.addDays(STEP)) >0) {
						UsersToRemove.add(User);
					}
				}
			}
			for (User User: UsersToRemove)
				Users.remove(User);
			newsgroupData.setNumberOfActiveUsers(newsgroupData.getUsers().size());
			db.sync();
		}
	}

	@Override
	public String getShortIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFriendlyName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSummaryInformation() {
		// TODO Auto-generated method stub
		return null;
	}
}
