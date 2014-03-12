package org.ossmeter.platform.osgi.executors;

import java.util.ArrayList;
import java.util.List;
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
		List<List<IMetricProvider>> metricBranches = splitIntoBranches(platform.getMetricProviderManager().getMetricProviders());
		
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
		}
		
		//TODO: Once terminated, update lastExecuted date. If a metric provider failed,
		// it should be the last date it was successful
		System.out.println("Finished projects");
	}
	
	// FIXME: This should really only be done once - at the beginning, as all projects do the same.
	public  List<List<IMetricProvider>> splitIntoBranches(List<IMetricProvider> metrics) {
		List<List<IMetricProvider>> branches = new ArrayList<List<IMetricProvider>>();
		
//		Collections.reverse(metrics);
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
				
			if (mp.getIdentifiersOfUses() != null && mp.getIdentifiersOfUses().size() != 0) {
				// Find self
				for (String useId : mp.getIdentifiersOfUses()) {
					for (IMetricProvider use : metrics) {
						if (use.getIdentifier().equals(useId)) {
							mpBranch.add(0, use);
							break;
						}
					}
				}
			}
		}
		
		return branches;
	}

	protected Date getLastExecutedDate() {
		Date lastExec;
		String lastExecuted = project.getLastExecuted();
		
		try {
		
		if(lastExecuted.equals("null") || lastExecuted.equals("")) {
			lastExec = new Date("19700101");
			
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
		} else {
			lastExec = new Date(lastExecuted);
		}
		return lastExec;
		} catch (Exception e) {
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
