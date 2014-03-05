package org.ossmeter.metricprovider.rascal;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.eclipse.imp.pdb.facts.IValue;
import org.ossmeter.metricprovider.rascal.RascalManager.ProjectRascalManager;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.vcs.VcsChangeType;
import org.ossmeter.platform.delta.vcs.VcsCommit;
import org.ossmeter.platform.delta.vcs.VcsCommitItem;
import org.ossmeter.platform.delta.vcs.VcsProjectDelta;
import org.ossmeter.platform.delta.vcs.VcsRepositoryDelta;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.VcsRepository;

public abstract class RascalMetrics {
	protected String module;
	protected String function;
	protected ProjectRascalManager manager;
	protected MetricProviderContext context;
	
	protected TreeMap<String, HashMap<String, IValue>> getM3s(Project project, ProjectDelta delta) {
//		assert manager != null = throw new RuntimeException("manager isn't initializer");
		TreeMap<String, HashMap<String, IValue>> m3sPerRevision = new TreeMap<>();
		HashMap<String, IValue> fileM3s = new HashMap<>();
		File localStorage = new File(project.getStorage().getPath());
		VcsProjectDelta vcsDelta = delta.getVcsDelta();

		for (VcsRepositoryDelta vcsRepositoryDelta : vcsDelta.getRepoDeltas()) {
			VcsRepository vcsRepository = vcsRepositoryDelta.getRepository();
			List<VcsCommit> commits = vcsRepositoryDelta.getCommits();
			for (VcsCommit commit : commits) {
				manager.checkOutRevision(delta.getDate(), commit.getRevision(), vcsRepository.getUrl(), localStorage.getAbsolutePath());
				for (VcsCommitItem item : commit.getItems()) {
					if (item.getChangeType() == VcsChangeType.DELETED || item.getChangeType() == VcsChangeType.UNKNOWN) {
						// not handling deleted files or unknown
						continue;
					}
					IValue fileM3 = null;
					String repo = vcsRepository.getUrl();
					// FIXME: This should only be a temporary resolution.
					String path = RascalManager.makeRelative(repo, item.getPath());
					String localFile = localStorage.getAbsolutePath() + "/" + getLastSegment(repo) + "/" + path;
					String fileURL = repo + (repo.endsWith("/") ? "" : "/") + path;
					try {
						fileM3 = manager.getModel(commit.getRevision(), fileURL, localFile);
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
