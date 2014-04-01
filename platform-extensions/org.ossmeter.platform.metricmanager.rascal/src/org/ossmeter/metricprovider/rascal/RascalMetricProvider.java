package org.ossmeter.metricprovider.rascal;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.ossmeter.metricprovider.rascal.trans.model.IntegerMeasurement;
import org.ossmeter.metricprovider.rascal.trans.model.Measurement;
import org.ossmeter.metricprovider.rascal.trans.model.RascalMetrics;
import org.ossmeter.metricprovider.rascal.trans.model.StringMeasurement;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.vcs.VcsChangeType;
import org.ossmeter.platform.delta.vcs.VcsCommit;
import org.ossmeter.platform.delta.vcs.VcsCommitItem;
import org.ossmeter.platform.delta.vcs.VcsProjectDelta;
import org.ossmeter.platform.delta.vcs.VcsRepositoryDelta;
import org.ossmeter.platform.vcs.workingcopy.manager.Churn;
import org.ossmeter.platform.vcs.workingcopy.manager.WorkingCopyCheckoutException;
import org.ossmeter.platform.vcs.workingcopy.manager.WorkingCopyFactory;
import org.ossmeter.platform.vcs.workingcopy.manager.WorkingCopyManagerUnavailable;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.VcsRepository;
import org.rascalmpl.interpreter.result.ICallableValue;

import com.mongodb.DB;

public class RascalMetricProvider implements ITransientMetricProvider<RascalMetrics> {

  private final String description;
  private final String friendlyName;
  private final String shortMetricId;
  private final String metricId;
  private final ICallableValue function;
  private MetricProviderContext context;
  private static String lastRevision = null;
  private static Map<VcsCommit, List<Churn>> churnPerCommit = new HashMap<>();
  private static Map<String, File> workingCopyFolders = new HashMap<>();
  private static Map<String, File> scratchFolders = new HashMap<>();
  private static IConstructor rascalDelta;

  public RascalMetricProvider(String metricId, String shortMetricId, String friendlyName, String description, ICallableValue function) {
    this.metricId = metricId;
    this.shortMetricId =  shortMetricId;
    this.friendlyName = friendlyName;
    this.description = description;
    this.function = function;
  }
  
  @Override
  public String toString() {
    return getIdentifier();
  }
  
	@Override
	public String getIdentifier() {
		return metricId;
	}

	@Override
	public String getShortIdentifier() {
		return shortMetricId;
	}

	@Override
	public String getFriendlyName() {
		return friendlyName;
	}

	@Override
	public String getSummaryInformation() {
		return description;
	}

	@Override
	public boolean appliesTo(Project project) {
		return project.getVcsRepositories().size() > 0;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return null;
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public RascalMetrics adapt(DB db) {
		RascalMetrics rm = new RascalMetrics(db, this.metricId);
		return rm;
	}

	@Override
	public void measure(Project project, ProjectDelta delta, RascalMetrics db) {
		RascalManager _instance = RascalManager.getInstance();
		
		List<VcsRepositoryDelta> repoDeltas = delta.getVcsDelta().getRepoDeltas();
		
		if (repoDeltas.isEmpty()) { 
		  System.err.println("Found repository deltas to be empty. Returning without doing anything");
		  return;
		}
		if (RascalMetricProvider.lastRevision == null) {
			RascalMetricProvider.lastRevision = repoDeltas.get(repoDeltas.size()-1).getLatestRevision();
			// the very first time, this will still be null, so we need to check for null below as well
		}
		List<VcsCommit> deltaCommits = repoDeltas.get(repoDeltas.size()-1).getCommits();
		if (deltaCommits.isEmpty()) {
			System.err.println("No commits? continuing for now...");
		}
		
		RascalProjectDeltas rpd = new RascalProjectDeltas(_instance.getEvaluator());
		
		if (RascalMetricProvider.lastRevision == null || !deltaCommits.get(deltaCommits.size()-1).getRevision().equals(RascalMetricProvider.lastRevision)) {
			for (VcsCommit commit: deltaCommits) {
				workingCopyFolders.clear();
				scratchFolders.clear();
				try {
					WorkingCopyFactory.getInstance().checkout(project, commit.getRevision(), workingCopyFolders, scratchFolders);
					VcsRepository repo = commit.getDelta().getRepository();
					List<Churn> currentChurn = WorkingCopyFactory.getInstance().getDiff(repo, workingCopyFolders.get(repo.getUrl()), RascalMetricProvider.lastRevision);
					RascalMetricProvider.churnPerCommit.put(commit, currentChurn);
					RascalMetricProvider.lastRevision = commit.getRevision();
				} catch (WorkingCopyManagerUnavailable | WorkingCopyCheckoutException e) {
					Rasctivator.logException("Working copy manager threw an error", e);
				}
			}
		}
		
		rascalDelta = rpd.convert(delta, churnPerCommit);
		System.out.println(rascalDelta);
		  
		  IMap rWorkingCopyFolders = _instance.makeMap(workingCopyFolders);
		  IMap rScratchFolders = _instance.makeMap(scratchFolders);
		  _instance.initialize(rascalDelta, rWorkingCopyFolders, rScratchFolders);
			
		  // TODO: generalize to any return type
		  // TODO: handle exceptions gracefully
		  // TODO: route warnings to the platform as well
		  IMap result = (IMap) function.call( new Type[] { rascalDelta.getType(), rWorkingCopyFolders.getType(), rScratchFolders.getType() }, 
												new IValue[] { rascalDelta, rWorkingCopyFolders, rScratchFolders }, null).getValue();
		  
		  for (Iterator<Entry<IValue, IValue>> it = result.entryIterator(); it.hasNext(); ) {
			Entry<IValue, IValue> currentEntry = (Entry<IValue, IValue>) it.next();
			// TODO: change to source locations
			String key = ((IString) currentEntry.getKey()).getValue();
			
			if (!key.isEmpty()) {
				Measurement measurement = null;
				// for cc need to delete all methods for this file
				
				// TODO: dispatch on return type
				if (currentEntry.getValue().getType().isInteger()) {
					measurement = new IntegerMeasurement();
					((IntegerMeasurement) measurement).setValue(((IInteger) currentEntry.getValue()).longValue());
				} else {
					measurement = new StringMeasurement();
					((StringMeasurement) measurement).setValue(((IString) currentEntry.getValue()).getValue());
				}
				measurement.setUri(key);
				db.getMeasurements().add(measurement);
			}
			db.sync();
		  }
	}
	
	private Map<String, Map<String, IValue>> getM3s(Project project, ProjectDelta delta, IConstructor rdelta) {
//  assert manager != null = throw new RuntimeException("manager isn't initializer");
  Map<String, Map<String, IValue>> m3sPerRevision = new TreeMap<>();
  Map<String, IValue> fileM3s = new HashMap<>();
  File localStorage = new File(project.getStorage().getPath());
  VcsProjectDelta vcsDelta = delta.getVcsDelta();
//  ProjectRascalManager manager = RascalManager.getInstance().getInstance(project.getName());

  for (VcsRepositoryDelta vcsRepositoryDelta : vcsDelta.getRepoDeltas()) {
    VcsRepository vcsRepository = vcsRepositoryDelta.getRepository();
    List<VcsCommit> commits = vcsRepositoryDelta.getCommits();
    for (VcsCommit commit : commits) {
//      manager.checkOutRevision(delta.getDate(), commit.getRevision(), vcsRepository.getUrl(), localStorage.getAbsolutePath());
      for (VcsCommitItem item : commit.getItems()) {
        if (item.getChangeType() == VcsChangeType.DELETED || item.getChangeType() == VcsChangeType.UNKNOWN) {
          // not handling deleted files or unknown
          continue;
        }
        IValue fileM3 = null;
        String repo = vcsRepository.getUrl();
        // FIXME: This should only be a temporary resolution.
        String path = RascalManager.makeRelative(repo, item.getPath());
        String localFile = localStorage.getAbsolutePath() + "/checkout/" + path;
        String fileURL = repo + (repo.endsWith("/") ? "" : "/") + path;
        try {
//          fileM3 = manager.getModel(commit.getRevision(), fileURL, localFile, rdelta);
        } catch (Exception e) {
          System.out.println(e.getMessage());
          System.err.println("Model could not be created for file " + localFile);
          System.err.println("Continuing with other files...");
          continue;
        }
        //fileM3 = manager.isValidModel(fileM3) ? fileM3 : manager.makeLocation(localFile); //everything is valid now
        fileM3s.put(fileURL, fileM3);
      }
      m3sPerRevision.put(commit.getRevision(), fileM3s);
    }
  }
  
  return m3sPerRevision;
}
	
	 private String getLastSegment(String repo) {
	    String[] segments = repo.split("/");
	    int last = segments.length - 1;
	    while(segments[last].isEmpty()) {
	      --last;
	    }
	    return segments[last];
	  }
}
