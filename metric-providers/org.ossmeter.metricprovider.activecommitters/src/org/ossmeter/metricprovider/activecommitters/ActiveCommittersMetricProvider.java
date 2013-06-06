package org.ossmeter.metricprovider.activecommitters;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.OMGVMCID;
import org.ossmeter.metricprovider.activecommitters.model.Committer;
import org.ossmeter.metricprovider.activecommitters.model.ActiveCommitters;
import org.ossmeter.metricprovider.activecommitters.model.NewsgroupData;
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

public class ActiveCommittersMetricProvider implements ITransientMetricProvider<ActiveCommitters>{

	protected final int STEP = 3;//15;
	
	protected PlatformCommunicationChannelManager communicationChannelManager;

	@Override
	public String getIdentifier() {
		return ActiveCommittersMetricProvider.class.getCanonicalName();
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
	public ActiveCommitters adapt(DB db) {
		return new ActiveCommitters(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, ActiveCommitters db) {
		
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
				Committer committer = new Committer();
				committer.setCommitterId(article.getUser());
				committer.setLastActivityDate(article.getDate().toString());
				newsgroupData.getCommitters().add(committer);
			}
			List<Committer> committers = newsgroupData.getCommitters();
			
			List<Committer> committersToRemove = new ArrayList<Committer>();
			for (Committer committer: committers) {
				java.util.Date javaDate = NntpUtil.parseDate(committer.getLastActivityDate());
				if (javaDate!=null) {
					Date date = new Date(javaDate);
					if (projectDelta.getDate().compareTo(date.addDays(STEP)) >0) {
						committersToRemove.add(committer);
					}
				}
			}
			for (Committer committer: committersToRemove)
				committers.remove(committer);
			newsgroupData.setNumberOfActiveCommiters(newsgroupData.getCommitters().size());
			db.sync();
		}
	}
}
