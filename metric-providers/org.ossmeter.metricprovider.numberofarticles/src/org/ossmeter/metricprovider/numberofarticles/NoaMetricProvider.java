package org.ossmeter.metricprovider.numberofarticles;

import java.util.List;

import org.ossmeter.metricprovider.numberofarticles.model.NewsgroupData;
import org.ossmeter.metricprovider.numberofarticles.model.Noa;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelProjectDelta;
import org.ossmeter.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.mongodb.DB;

public class NoaMetricProvider implements ITransientMetricProvider<Noa>{

	protected PlatformCommunicationChannelManager communicationChannelManager;

	@Override
	public String getIdentifier() {
		return NoaMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "noa";
	}

	@Override
	public String getFriendlyName() {
		return "Number of articles";
	}

	@Override
	public String getSummaryInformation() {
		return "Holds the number of articles";
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
	public Noa adapt(DB db) {
		return new Noa(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, Noa db) {
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
			newsgroupData.setNumberOfArticles(communicationChannelDelta.getArticles().size());
			db.sync();
		}
	}
}
