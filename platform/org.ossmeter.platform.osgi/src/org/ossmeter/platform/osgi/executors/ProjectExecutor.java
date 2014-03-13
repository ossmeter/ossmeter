package org.ossmeter.platform.osgi.executors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.ossmeter.platform.Date;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.VcsRepository;

public class ProjectExecutor implements Runnable {

	protected Project project;
	protected int numberOfCores;
	protected Platform platform;
	
	public ProjectExecutor(Platform platform, Project project) {
		this.numberOfCores = Runtime.getRuntime().availableProcessors();
		
		this.platform = platform;
		this.project = project;
	}
	
	@Override
	public void run() {
		if (project == null) {
			System.err.println("No project scheduled. Exiting.");
			return;
		}
		System.out.println(project.getName() + " executing...");

		// Split metrics into branches
		List<List<IMetricProvider>> metricBranches = splitIntoBranchesDFS2(platform.getMetricProviderManager().getMetricProviders());
		
		//
		Date lastExecuted = getLastExecutedDate();
		
		if (lastExecuted == null) {
			// TODO: Logging/alert. Perhaps flag the project as being in an error state.
			System.err.println("Date parse error. ");
			return;
		}
		Date today = new Date();
		
		Date[] dates = Date.range(lastExecuted.addDays(1), today);
		for (Date date : dates) {
			// TODO: An alternative would be to have a single thread pool for the node. I briefly tried this
			// and it didn't work. Reverted to this implement (temporarily at least).
			ExecutorService executorService = Executors.newFixedThreadPool(numberOfCores);
			System.out.println("Date: " + date + ", project: " + project.getName());
			
			ProjectDelta delta = new ProjectDelta(project, date, 
					platform.getVcsManager(), platform.getCommunicationChannelManager(), platform.getBugTrackingSystemManager());
			delta.create();
			
			System.out.println("Number of metric branches: " + metricBranches.size());
			for (List<IMetricProvider> branch : metricBranches) {
				MetricListExecutor mExe = new MetricListExecutor(platform, project, delta, date);
				mExe.setMetricList(branch);
				
				executorService.execute(mExe);				
			}
			
			// TODO: check a metric provider hasn't already been executed for the given date
			try {
				executorService.shutdown();
				executorService.awaitTermination(24, TimeUnit.HOURS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			project.setLastExecuted(date.toString());
			platform.getProjectRepositoryManager().getProjectRepository().sync();
		}
		
		//TODO: Once terminated, update lastExecuted date. If a metric provider failed,
		// it should be the last date it was successful
		System.out.println("Finished projects");
	}
	
	// FIXME: This should really only be done once - at the beginning, as all projects do the same.
	@Deprecated
	private List<List<IMetricProvider>> splitIntoBranches(List<IMetricProvider> metrics) {
		List<List<IMetricProvider>> branches = new ArrayList<List<IMetricProvider>>();
		
		// Start at the leaves
		Collections.reverse(metrics);
		
		for (IMetricProvider mp : metrics) {
			List<IMetricProvider> mpBranch = null;
				
			for (List<IMetricProvider> branch : branches) {
				if (branch.contains(mp)) {
					mpBranch = branch;
					break;
				}
			}
			if (mpBranch == null) {
				List<IMetricProvider> br = new ArrayList<IMetricProvider>();
				br.add(mp);
				mpBranch = br;
				branches.add(mpBranch);
			}
				
			//FIXME: This is incorrect. Rethink it.
			if (mp.getIdentifiersOfUses() != null && mp.getIdentifiersOfUses().size() != 0) {
				// Find self
				for (String useId : mp.getIdentifiersOfUses()) {
					for (IMetricProvider use : metrics) {
						if (use.getIdentifier().equals(useId)) {
							// Check if it's already in a branch
							boolean found = false;
							for (List<IMetricProvider> branch : branches) {
								if (branch.contains(use)) {
									
									branch.addAll(mpBranch);
//									branches.remove(mpBranch);
//									mpBranch.clear();
									mpBranch = branch;
									found = true;
									break;
								}
							}
							
							if (!found) mpBranch.add(0, use);
							break;
						}
					}
				}
			}
		}
		
		return branches;
	}
	
	public List<List<IMetricProvider>> splitIntoBranchesDFS2(List<IMetricProvider> metrics) {
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
		
		// TODO sort each branch
		
		List<List<IMetricProvider>> sortedBranches = new ArrayList<List<IMetricProvider>>();
		for (Set<IMetricProvider> b : branches) {
			sortedBranches.add(sortMetricProviders(new ArrayList<IMetricProvider>(b)));
		}
		
		
		return sortedBranches;
	}
	
	@Deprecated
	private List<List<IMetricProvider>> splitIntoBranchesDFS(List<IMetricProvider> metrics) {
		List<List<IMetricProvider>> branches = new ArrayList<List<IMetricProvider>>();
		
		// Start at the leaves
		Collections.reverse(metrics);
		
		for (IMetricProvider mp : metrics) {
			boolean alreadyInBranch = false;
			for (List<IMetricProvider> branch : branches) {
				if (branch.contains(mp)) {
					System.err.println("Found a metric already in a branch");
					alreadyInBranch = true;
					break;
				}
			}
			if (alreadyInBranch) continue; // We've already analysed it.
			
			List<IMetricProvider> branch = new ArrayList<>();
			branch.add(mp);
			branches.add(branch);
			dfsUses(branch, mp, metrics);
		}
		
		// We now should have each dependency branch separated, but unordered.
		
		return branches;
	}

	@Deprecated
	private void dfsUses(List<IMetricProvider> branch, IMetricProvider mp, List<IMetricProvider> allMetrics) {
		if (mp.getIdentifiersOfUses() != null && mp.getIdentifiersOfUses().size() != 0) {
			for (String useId : mp.getIdentifiersOfUses()) {
				IMetricProvider use = lookupMetricProviderById(allMetrics, useId);
				if (use == null) {
					System.err.println("Metric provider lookup failed: " + useId);
					continue;
				}
				branch.add(use);
				dfsUses(branch, use, allMetrics);
			}
		}
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
		String lastExecuted = project.getLastExecuted();
		
		try {
		
		if(lastExecuted.equals("null") || lastExecuted.equals("")) {
			lastExec = new Date();
			
			for (VcsRepository repo : project.getVcsRepositories()) {
				// This needs to be the day BEFORE the first day! (Hence the addDays(-1)) 
				Date d = platform.getVcsManager().getDateForRevision(repo, platform.getVcsManager().getFirstRevision(repo)).addDays(-1);
				if (d == null) continue;
				if (lastExec.compareTo(d) > 0) {
					lastExec = d;
				}
			}
			for (CommunicationChannel communicationChannel : project.getCommunicationChannels()) {
				Date d = platform.getCommunicationChannelManager().getFirstDate(communicationChannel).addDays(-1);
				if (d == null) continue;
				if (lastExec.compareTo(d) > 0) {
					lastExec = d;
				}
			}
			for (BugTrackingSystem bugTrackingSystem : project.getBugTrackingSystems()) {
				Date d = platform.getBugTrackingSystemManager().getFirstDate(bugTrackingSystem).addDays(-1);
				if (d == null) continue;
				if (lastExec.compareTo(d) > 0) {
					lastExec = d;
				}
			}
		} else {
			lastExec = new Date(lastExecuted);
		}
		return lastExec;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<IMetricProvider> getOrderedHistoricMetricProviders(List<IMetricProvider> providers) {
		List<IMetricProvider> histMps = new ArrayList<IMetricProvider>();
		
		for (IMetricProvider mp : providers) {
			if( mp instanceof IHistoricalMetricProvider) {
				histMps.add(mp);
			}
		}
		return sortMetricProviders(histMps); // FIXME: ack! Loss of type safety.
	}

	
	/**
	 * Orders transient metric providers to ensure that any dependencies are
	 * executed first. 
	 * @param providers
	 * @return
	 */
	public List<IMetricProvider> getOrderedTransientMetricProviders(List<IMetricProvider> providers) {
		List<IMetricProvider> transMps = new ArrayList<IMetricProvider>();
		
		for (IMetricProvider mp : providers) {
			if( mp instanceof ITransientMetricProvider) {
				transMps.add(mp);
			}
		}
		return sortMetricProviders(transMps); // FIXME: ack! Loss of type safety.
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
