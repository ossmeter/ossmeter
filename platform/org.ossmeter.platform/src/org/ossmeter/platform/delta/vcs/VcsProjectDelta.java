package org.ossmeter.platform.delta.vcs;

import java.util.ArrayList;
import java.util.List;

import org.ossmeter.platform.Date;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.VcsRepository;

public class VcsProjectDelta {
	
	protected List<VcsRepositoryDelta> repoDeltas = new ArrayList<VcsRepositoryDelta>();
	
	public VcsProjectDelta(Project project, Date date, IVcsManager vcsManager) throws Exception {
		for (VcsRepository repo : project.getVcsRepositories()) {
			String[] revs = vcsManager.getRevisionsForDate(repo, date);
			
			if (revs == null) return;
			if (revs[0] == null || revs[1] == null) return;
			
			repoDeltas.add(vcsManager.getDelta(repo, revs[0], revs[1]));
		}
	}
	
	public List<VcsRepositoryDelta> getRepoDeltas() {
		return repoDeltas;
	}
	
	public void setRepoDeltas(List<VcsRepositoryDelta> repoDeltas) {
		this.repoDeltas = repoDeltas;
	}
}
