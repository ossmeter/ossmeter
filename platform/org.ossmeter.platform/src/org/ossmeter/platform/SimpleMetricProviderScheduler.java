package org.ossmeter.platform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.logging.OssmeterLoggerFactory;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.LocalStorage;
import org.ossmeter.repository.model.MetricProvider;
import org.ossmeter.repository.model.MetricProviderType;
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
					System.out.println(project.getName());
					initialiseProjectLocalStorage(project);
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
		if (project.getLastExecuted().equals("-1") || project.getLastExecuted().equals("null") || project.getLastExecuted() == null) {
			// TODO: refactor this block
			Date lastExec = new Date("19700101");
			

			for (VcsRepository repo : project.getVcsRepositories()) {
				
				
				System.err.println("Rep " + repo);
				
				// This needs to be the day BEFORE the first day! (Hence the addDays(-1))
				Date d = platform.getVcsManager().getDateForRevision(repo, platform.getVcsManager().getFirstRevision(repo)).addDays(-1);
				if (lastExec.compareTo(d) < 0) {
					lastExec = d;
				}
			}
			for (CommunicationChannel communicationChannel : project.getCommunicationChannels()) {
				Date d = platform.getCommunicationChannelManager().getFirstDate(communicationChannel).addDays(-1);
				if (lastExec.compareTo(d) < 0) {
					lastExec = d;
				}
			}
			for (BugTrackingSystem bugTrackingSystem : project.getBugTrackingSystems()) {
				Date d = platform.getBugTrackingSystemManager().getFirstDate(bugTrackingSystem).addDays(-1);
				if (lastExec.compareTo(d) < 0) {
					lastExec = d;
				}
			}
			project.setLastExecuted(lastExec.toString());
		}
		
		Date last = new Date(project.getLastExecuted());
		
		//DEBUG
		last = new Date("20120909");
		//END DEBUG
		
		
		Date today = new Date();
		
		//DEBUG
		today = new Date("20130912");
		//END DEBUG
		
		Date[] dates = Date.range(last.addDays(1), today);
		
		
		for (Date date : dates) {
			System.out.println("\tDate: " + date + " (" + project.getName() + ")");
			ProjectDelta delta = new ProjectDelta(	project, 
													date, 
													platform.getVcsManager(), 
													platform.getCommunicationChannelManager(), 
													platform.getBugTrackingSystemManager());
			delta.create();

			// 2. Execute transient MPs
			for (ITransientMetricProvider provider : getOrderedTransientMetricProviders(providers)) {
				if (provider.appliesTo(project) && !hasMetricProviderBeenExecutedForDate(project, provider, date)) {
					System.out.println("\t'\tTMP: " + provider.getIdentifier());
					provider.setMetricProviderContext(new MetricProviderContext(platform, new OssmeterLoggerFactory().makeNewLoggerInstance(provider.getIdentifier())));
					addDependenciesToMetricProvider(provider);
					provider.measure(project, delta, provider.adapt(platform.getMetricsRepository(project).getDb()));
					
					updateMetricProviderMetaData(project, provider, date, MetricProviderType.TRANSIENT);
				}
			}
			
			// 3. Execute historical MPs
			MetricHistoryManager historyManager = new MetricHistoryManager(platform);
			for (IMetricProvider  provider : providers) {
				if (provider instanceof IHistoricalMetricProvider && provider.appliesTo(project) && !hasMetricProviderBeenExecutedForDate(project, provider, date)) {
					System.out.println("\t'\tHMP: " + provider.getIdentifier());
					provider.setMetricProviderContext(new MetricProviderContext(platform, new OssmeterLoggerFactory().makeNewLoggerInstance(provider.getIdentifier())));
					addDependenciesToMetricProvider(provider);
					historyManager.store(project, date, (IHistoricalMetricProvider) provider);
					
					updateMetricProviderMetaData(project, provider, date, MetricProviderType.HISTORIC);
				}
			}
			platform.getProjectRepositoryManager().projectRepository.sync();
		}
		project.setLastExecuted(today.toString());
		platform.getProjectRepositoryManager().projectRepository.sync();
	}
	
	protected boolean hasMetricProviderBeenExecutedForDate(Project project, IMetricProvider provider, Date date) {
		MetricProvider mp = getProjectModelMetricProvider(project, provider);
		if (mp != null) {
			String last =  mp.getLastExecuted();
			try {
				return new Date(last).compareTo(date) > 0;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	protected void updateMetricProviderMetaData(Project project, IMetricProvider provider, Date date, MetricProviderType type) {
		// Update project MP meta-data
		MetricProvider mp = getProjectModelMetricProvider(project, provider);
		if (mp == null) {
			mp = new MetricProvider();
			project.getMetricProviderData().add(mp);
			mp.setMetricProviderId(provider.getShortIdentifier());
			mp.setType(type);
		}
		mp.setLastExecuted(date.toString()); 
	}
	
	protected MetricProvider getProjectModelMetricProvider(Project project, IMetricProvider iProvider) {
		for (MetricProvider mp : project.getMetricProviderData()) {
			if (mp.getMetricProviderId().equals(iProvider.getShortIdentifier())) {
				return mp;
			}
		}
		return null;
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
	
	protected void initialiseProjectLocalStorage (Project project) {
		try{	
			Path projectLocalStoragePath = Paths.get(platform.getLocalStorageHomeDirectory().toString(), project.getName());		
			if (Files.notExists(projectLocalStoragePath)) {
					Files.createDirectory(projectLocalStoragePath);
					LocalStorage projectLocalStorage = new LocalStorage();
					projectLocalStorage.setPath(projectLocalStoragePath.toString());
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
