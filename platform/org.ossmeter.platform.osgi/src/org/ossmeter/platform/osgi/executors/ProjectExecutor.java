package org.ossmeter.platform.osgi.executors;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.ossmeter.platform.Date;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.delta.NoManagerFoundException;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.logging.OssmeterLogger;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.VcsRepository;

public class ProjectExecutor implements Runnable {
	
	protected FileWriter writer;

	protected Project project;
	protected int numberOfCores;
	protected Platform platform;
	protected OssmeterLogger logger;
	
	public ProjectExecutor(Platform platform, Project project) {
		this.numberOfCores = Runtime.getRuntime().availableProcessors();
		this.platform = platform;
		this.project = project;
		this.logger = (OssmeterLogger)OssmeterLogger.getLogger("ProjectExecutor (" + project.getName() +")");
		this.logger.addConsoleAppender(OssmeterLogger.DEFAULT_PATTERN);
		
		// DEBUG
		try {
//			this.writer = null;
			this.writer = new FileWriter("/Users/jimmy/Desktop/D5.3-logs/" + project.getName() + ".csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		if (project == null) {
			logger.error("No project scheduled. Exiting.");
			return;
		}
		logger.info("Beginning execution.");
		
		// Clear any open flags
		project.getInternal().setInErrorState(false);
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
		// Split metrics into branches
		List<List<IMetricProvider>> metricBranches = splitIntoBranches(platform.getMetricProviderManager().getMetricProviders());
		
		// Find the date to start from 
		Date lastExecuted = getLastExecutedDate();
		
		if (lastExecuted == null) {
			// TODO: Perhaps flag the project as being in a fatal error state? This will potentially keep occurring.
			logger.error("Parse error of project's lastExecuted date. Returned null.");
			return;
		}
		Date today = new Date();
		
		Date[] dates = Date.range(lastExecuted.addDays(1), today);
		
		
		for (Date date : dates) {
			// TODO: An alternative would be to have a single thread pool for the node. I briefly tried this
			// and it didn't work. Reverted to this implement (temporarily at least).
			ExecutorService executorService = Executors.newFixedThreadPool(numberOfCores);
			logger.info("Date: " + date + ", project: " + project.getName());
			
			long startDelta = System.currentTimeMillis();
			ProjectDelta delta = new ProjectDelta(project, date, platform);
			boolean createdOk = delta.create();
			String deltaTimes = delta.getTimingsString();
			long timeDelta = System.currentTimeMillis() - startDelta;

			if (createdOk) {
			} else {
				project.getInternal().setInErrorState(true);
				platform.getProjectRepositoryManager().getProjectRepository().sync();
				
				logger.error("Project delta creation failed. Aborting.");
				return;
			}
			
			long startMetrics = System.currentTimeMillis();
			for (List<IMetricProvider> branch : metricBranches) {
				MetricListExecutor mExe = new MetricListExecutor(platform, project, delta, date);
				mExe.setMetricList(branch);
				
				executorService.execute(mExe);
			}
			
			try {
				executorService.shutdown();
				executorService.awaitTermination(24, TimeUnit.HOURS);
			} catch (InterruptedException e) {
				logger.error("Exception thrown when shutting down executor service.", e);
			}
			long timeMetrics = System.currentTimeMillis() - startMetrics;
			
			if (project.getInternal().getInErrorState()) {
				// TODO: what should we do? Is the act of not-updating the lastExecuted flag enough?
				// If it continues to loop, it simply tries tomorrow. We need to stop this happening.
				logger.warn("Project in error state. Stopping execution.");
				break;
			} else {
				project.getInternal().setLastExecuted(date.toString());
				platform.getProjectRepositoryManager().getProjectRepository().sync();
			}
			
			try { ///DEBUG
				writer.write(date.toString() + "," + deltaTimes + "," + timeDelta + "," + timeMetrics + "\n");
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Project execution complete. In error state: " + project.getInternal().getInErrorState());
	}

	/**
	 * Algorithm to split a list of metric providers into dependency branches. Current implementation isn't
	 * wonderful - it was built to work, not to perform - and needs relooking at in the future. 
	 * @param metrics
	 * @return
	 */
	public List<List<IMetricProvider>> splitIntoBranches(List<IMetricProvider> metrics) {
		List<Set<IMetricProvider>> branches = new ArrayList<Set<IMetricProvider>>();
		
		for (IMetricProvider m : metrics) {
			Set<IMetricProvider> mBranch = new HashSet<>();
			
			for (Set<IMetricProvider> branch : branches) {
				if (branch.contains(m)) {
					mBranch = branch;
					break;
				}
			}
			if (!mBranch.contains(m)) mBranch.add(m);
			if (!branches.contains(mBranch)) branches.add(mBranch);

			for (String id : m.getIdentifiersOfUses()) {
				IMetricProvider use = lookupMetricProviderById(metrics, id);
				if (use == null) continue;
				boolean foundUse = false;
				for (Set<IMetricProvider> branch : branches) {
					if (branch.contains(use)) {
						branch.addAll(mBranch);
						branches.remove(mBranch);
						mBranch = branch;
						foundUse = true;
						break;
					}
				}
				if (!foundUse) {
					mBranch.add(use);
				}
			}
			if (!branches.contains(mBranch)) branches.add(mBranch);
		}
		
		List<List<IMetricProvider>> sortedBranches = new ArrayList<List<IMetricProvider>>();
		for (Set<IMetricProvider> b : branches) {
			sortedBranches.add(sortMetricProviders(new ArrayList<IMetricProvider>(b)));
		}
		
		return sortedBranches;
	}
	
	protected IMetricProvider lookupMetricProviderById(List<IMetricProvider> metrics, String id){
		for (IMetricProvider mp : metrics) {
			if (mp.getIdentifier().equals(id)) {
				return mp;
			}
		}
		return null;
	}
		
	protected Date getLastExecutedDate() {
		Date lastExec;
		String lastExecuted = project.getInternal().getLastExecuted();
		
		if(lastExecuted.equals("null") || lastExecuted.equals("")) {
			lastExec = new Date();
			
			for (VcsRepository repo : project.getVcsRepositories()) {
				// This needs to be the day BEFORE the first day! (Hence the addDays(-1)) 
				try {
					Date d = platform.getVcsManager().getDateForRevision(repo, platform.getVcsManager().getFirstRevision(repo)).addDays(-1);
					if (d == null) continue;
					if (lastExec.compareTo(d) > 0) {
						lastExec = d;
					} 
				} catch (NoManagerFoundException e) {
					System.err.println(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (CommunicationChannel communicationChannel : project.getCommunicationChannels()) {
				try {
					Date d = platform.getCommunicationChannelManager().getFirstDate(platform.getMetricsRepository(project).getDb(), communicationChannel).addDays(-1);
					if (d == null) continue;
					if (lastExec.compareTo(d) > 0) {
						lastExec = d;
					}
				} catch (NoManagerFoundException e) {
					System.err.println(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (BugTrackingSystem bugTrackingSystem : project.getBugTrackingSystems()) {
				try {
					Date d = platform.getBugTrackingSystemManager().getFirstDate(bugTrackingSystem).addDays(-1);
					if (d == null) continue;
					if (lastExec.compareTo(d) > 0) {
						lastExec = d;
					}
				} catch (NoManagerFoundException e) {
					System.err.println(e.getMessage());					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				lastExec = new Date(lastExecuted);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		return lastExec;
	}
	
	public List<IMetricProvider> sortMetricProviders(List<IMetricProvider> providers) {
		List<IMetricProvider> sorted = new ArrayList<IMetricProvider>();
		List<IMetricProvider> marked = new ArrayList<IMetricProvider>();
		List<IMetricProvider> temporarilyMarked = new ArrayList<IMetricProvider>();
		List<IMetricProvider> unmarked = new ArrayList<IMetricProvider>();
		unmarked.addAll(providers);
		
		while (unmarked.size()>0) {
			IMetricProvider mp = unmarked.get(0);
			visitDependencies(marked, temporarilyMarked, unmarked, mp, providers, sorted);
		}
		return sorted;
	}
	
	protected void visitDependencies(List<IMetricProvider> marked, List<IMetricProvider> temporarilyMarked, List<IMetricProvider> unmarked, IMetricProvider mp, List<IMetricProvider> providers, List<IMetricProvider> sorted) {
		if (temporarilyMarked.contains(mp)) {
			throw new RuntimeException("Temporarily marked error.");
		}
		if (!marked.contains(mp)) {
			temporarilyMarked.add(mp);
			List<String> dependencies = mp.getIdentifiersOfUses();
			if (dependencies != null) 
				for (String dependencyIdentifier : dependencies) {
					for (IMetricProvider p : providers) {
						if (p.getIdentifier().equals(dependencyIdentifier)){
							visitDependencies(marked, temporarilyMarked, unmarked, p, providers, sorted);
							break;
						}
					}
				}
			marked.add(mp);
			temporarilyMarked.remove(mp);
			unmarked.remove(mp);
			sorted.add(mp);
		}
	}
}
