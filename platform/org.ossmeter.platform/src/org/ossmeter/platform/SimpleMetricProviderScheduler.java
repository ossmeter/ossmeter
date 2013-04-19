package org.ossmeter.platform;

import java.util.ArrayList;
import java.util.List;

import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.VcsRepository;

public class SimpleMetricProviderScheduler {
	
	protected Platform platform;
	protected int interval = 2000;
	
	public SimpleMetricProviderScheduler(Platform platform) {
		this.platform = platform;
	}
	
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	public int getInterval() {
		return interval;
	}
	
	public void run() throws Exception {
		
//		TimerTask timerTask = new TimerTask() {
//			
//			@Override
//			public void run() {
				for (Project project : platform.getProjectRepositoryManager().getProjectRepository().getProjects()) {
					executeMetricProviders(platform.getMetricProviderManager().getMetricProviders(), project);
				}
//			}
//		};
//		
//		new Timer().schedule(timerTask, 0, interval);
	}
	
	protected List<ITransientMetricProvider> getOrderedTransientMetricProviders(List<IMetricProvider> providers) {
		List<ITransientMetricProvider> transMPs = new ArrayList<ITransientMetricProvider>();
		for (IMetricProvider provider : providers) {
			if (provider instanceof ITransientMetricProvider) {
				transMPs.add((ITransientMetricProvider<?>) provider);
			} // TODO dependencies need ordering
		}
		return transMPs;
	}
	
	protected void executeMetricProviders(List<IMetricProvider> providers, Project project) throws Exception {
		
		// 1. Calculate Project Delta
		if (project.getLastExecuted().equals("-1") || project.getLastExecuted().equals("null")) {
			// TODO need updating with other repos (comms, bug) - move to separate method
			for (VcsRepository repo : project.getVcsRepositories()) {
				// This needs to be the day BEFORE the first day! (Hence the addDays(-1))
				project.setLastExecuted(platform.getVcsManager().
						getDateForRevision(repo, platform.getVcsManager().getFirstRevision(repo)).addDays(-1).toString());
			}
			for (CommunicationChannel communicationChannel : project.getCommunicationChannels()) {
				// This needs to be the day BEFORE the first day! (Hence the addDays(-1))
				project.setLastExecuted(platform.getCommunicationChannelManager().getFirstDate(communicationChannel).toString());
			}
		}
		
		Date last = new Date(project.getLastExecuted());
		Date today = new Date();
		Date[] dates = Date.range(last.addDays(1), today);
		
		for (Date date : dates) {
			ProjectDelta delta = new ProjectDelta(project, date, platform.getVcsManager(), platform.getCommunicationChannelManager());
			delta.create();

			// 2. Execute transient MPs
			for (ITransientMetricProvider provider : getOrderedTransientMetricProviders(providers)) {
				if (provider.appliesTo(project)) {
					provider.setMetricProviderContext(platform.getMetricProviderContext());
					addDependenciesToMetricProvider(provider);
					provider.measure(project, delta, provider.adapt(platform.getMetricsRepository(project).getDb()));
				}
			}
			
			// 3. Execute historical MPs
			MetricHistoryManager historyManager = new MetricHistoryManager(platform);
			for (IMetricProvider  provider : providers) {
				if (provider instanceof IHistoricalMetricProvider && provider.appliesTo(project)) {
					provider.setMetricProviderContext(platform.getMetricProviderContext());
					addDependenciesToMetricProvider(provider);
					historyManager.store(project, date, (IHistoricalMetricProvider) provider);
				}
			}
			platform.getProjectRepositoryManager().projectRepository.sync();
		}
		project.setLastExecuted(today.toString());
		platform.getProjectRepositoryManager().projectRepository.sync();
	}
	
	protected void addDependenciesToMetricProvider(IMetricProvider mp) {
		if (mp.getIdentifiersOfUses() == null) return; 
		
		List<IMetricProvider> uses = new ArrayList<IMetricProvider>();
		for (String id : mp.getIdentifiersOfUses()) {
			for (IMetricProvider imp : platform.getMetricProviderManager().getMetricProviders()) {
				if (imp.getIdentifier().equals(id)) {
					uses.add(imp);
					break;
				}
			}
		}
		mp.setUses(uses);
	}
}
