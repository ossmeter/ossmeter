package org.ossmeter.platform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.logging.OssmeterLogger;
import org.ossmeter.platform.logging.OssmeterLoggerFactory;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.LocalStorage;
import org.ossmeter.repository.model.MetricProvider;
import org.ossmeter.repository.model.MetricProviderType;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.VcsRepository;

public class SimpleMetricProviderScheduler {
	
	protected final OssmeterLogger logger;
	protected Platform platform;
	protected int interval = 2000;
	
	public SimpleMetricProviderScheduler(Platform platform) {
		this.platform = platform;
		logger = (OssmeterLogger) OssmeterLogger.getLogger("platform.scheduler");
		logger.addConsoleAppender(OssmeterLogger.DEFAULT_PATTERN);
	}
	
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	/**
	 * @return The interval at which metric providers should be executed.
	 */
	public int getInterval() {
		return interval;
	}
	
	/**
	 * Starts scheduling the metric providers for all projects in the DB.
	 * @throws Exception
	 */
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
	
	/**
	 * Orders transient metric providers to ensure that any dependencies are
	 * executed first. TODO: Not actually implemented.
	 * @param providers
	 * @return
	 */
	protected List<ITransientMetricProvider> getOrderedTransientMetricProviders(List<IMetricProvider> providers) {
		List<ITransientMetricProvider> transMPs = new ArrayList<ITransientMetricProvider>();
		for (IMetricProvider provider : providers) {
			if (provider instanceof ITransientMetricProvider) {
				transMPs.add((ITransientMetricProvider<?>) provider);
			} // TODO dependencies need ordering
		}
		return transMPs;
	}
	
	/**
	 * Applies the list of metric providers to the project. Applies each
	 * metric provider daily from the last executed date (or the beginning
	 * of time).
	 * @param providers
	 * @param project
	 * @throws Exception
	 */
	protected void executeMetricProviders(List<IMetricProvider> providers, Project project) throws Exception {
		
		// 1. Calculate Project Delta
		if (project.getLastExecuted().equals("-1") || project.getLastExecuted().equals("null") || project.getLastExecuted() == null) {
			// TODO: refactor this block
			Date lastExec = new Date("19700101");
			

			for (VcsRepository repo : project.getVcsRepositories()) {
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
		Date today = new Date();

		//DEBUG
//		if (project.getName().equals("Epsilon"))
//			last = new Date("20130901");
//		today = new Date("20131014");
		//END DEBUG
		
		Date[] dates = Date.range(last.addDays(1), today);
		
		for (Date date : dates) {
			logger.info("Date: " + date + " (" + project.getName() + ")");
			
			ProjectDelta delta = new ProjectDelta(	project, 
													date, 
													platform.getVcsManager(), 
													platform.getCommunicationChannelManager(), 
													platform.getBugTrackingSystemManager());
			delta.create();

			// 2. Execute transient MPs
			for (ITransientMetricProvider provider : getOrderedTransientMetricProviders(providers)) {
				if (provider.appliesTo(project) && !hasMetricProviderBeenExecutedForDate(project, provider, date)) {
					logger.info("\tTMP: " + provider.getIdentifier());
					provider.setMetricProviderContext(new MetricProviderContext(platform, new OssmeterLoggerFactory().makeNewLoggerInstance(provider.getIdentifier())));
					addDependenciesToMetricProvider(provider);
					try {
						provider.measure(project, delta, provider.adapt(platform.getMetricsRepository(project).getDb()));
					} catch (Exception e) {
						logger.error("Metric provider '' threw an exception for project '' on date ''. Error message: \n" + e.getMessage());
					}
					
					updateMetricProviderMetaData(project, provider, date, MetricProviderType.TRANSIENT);
				}
			}
			
			// 3. Execute historical MPs
			MetricHistoryManager historyManager = new MetricHistoryManager(platform);
			for (IMetricProvider  provider : providers) {
				if (provider instanceof IHistoricalMetricProvider && provider.appliesTo(project) && !hasMetricProviderBeenExecutedForDate(project, provider, date)) {
					logger.info("\t'\tHMP: " + provider.getIdentifier());
					provider.setMetricProviderContext(new MetricProviderContext(platform, new OssmeterLoggerFactory().makeNewLoggerInstance(provider.getIdentifier())));
					addDependenciesToMetricProvider(provider);
					try {
						historyManager.store(project, date, (IHistoricalMetricProvider) provider);
					} catch (Exception e) {
						logger.error("Metric provider '' threw an exception for project '' on date ''. Error message: \n" + e.getMessage());
					}
					
					updateMetricProviderMetaData(project, provider, date, MetricProviderType.HISTORIC);
				}
			}
			project.setLastExecuted(date.toString());
			platform.getProjectRepositoryManager().projectRepository.sync();
		}
	}
	
	/**
	 * Does what the method name suggests.
	 * @param project
	 * @param provider
	 * @param date
	 * @return
	 */
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
	
	/**
	 * Ensures that the project DB has the up-to-date information regarding
	 * the date of last execution.
	 * @param project
	 * @param provider
	 * @param date
	 * @param type
	 */
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
		platform.getProjectRepositoryManager().projectRepository.sync();
	}
	
	/**
	 * 
	 * @param project
	 * @param iProvider
	 * @return A MetricProvider (part of the Project DB) that matches the given IMetricProvider.
	 */
	protected MetricProvider getProjectModelMetricProvider(Project project, IMetricProvider iProvider) {
		for (MetricProvider mp : project.getMetricProviderData()) {
			if (mp.getMetricProviderId().equals(iProvider.getShortIdentifier())) {
				return mp;
			}
		}
		return null;
	}
	
	/**
	 * Adds references to the dependencies of a metric provider so that they
	 * can use their data for the calculations.
	 * @param mp
	 */
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
	
	/**
	 * Configure the location on disk where metrics can store data 
	 * related to projects.
	 * @param project
	 */
	protected void initialiseProjectLocalStorage (Project project) {
		try{	
			Path projectLocalStoragePath = Paths.get(platform.getLocalStorageHomeDirectory().toString(), project.getName());		
			if (Files.notExists(projectLocalStoragePath)) {
				Files.createDirectory(projectLocalStoragePath);
			}
			LocalStorage projectLocalStorage = new LocalStorage();
			projectLocalStorage.setPath(projectLocalStoragePath.toString());
			project.setStorage(projectLocalStorage);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
