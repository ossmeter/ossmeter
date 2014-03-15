package org.ossmeter.metricprovider.trans.committers;

import java.util.Collections;
import java.util.List;

import org.ossmeter.metricprovider.trans.committers.model.Committer;
import org.ossmeter.metricprovider.trans.committers.model.ProjectCommitters;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.vcs.VcsCommit;
import org.ossmeter.platform.delta.vcs.VcsRepositoryDelta;
import org.ossmeter.repository.model.Project;

import com.mongodb.DB;

public class CommittersMetricProvider implements ITransientMetricProvider<ProjectCommitters> {

	@Override
	public String getIdentifier() {
		return CommittersMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "List of project committers";
	}

	@Override
	public String getFriendlyName() {
		return "projectcommitters";
	}

	@Override
	public String getSummaryInformation() {
		return "The list of committers on the project.";
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
		return Collections.emptyList();
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		
	}

	@Override
	public ProjectCommitters adapt(DB db) {
		return new ProjectCommitters(db);
	}

	@Override
	public void measure(Project project, ProjectDelta delta, ProjectCommitters db) {
		
		for (VcsRepositoryDelta vcsDelta : delta.getVcsDelta().getRepoDeltas()) {
			for (VcsCommit commit : vcsDelta.getCommits()) {
				String author = commit.getAuthor();
				
				Committer committer = db.getCommitters().findOneByName(author);
				if (committer == null) {
					committer = new Committer();
					committer.setName(author);
					db.getCommitters().add(committer);
				}
				if (commit.getJavaDate() != null) committer.setLastCommitTime(commit.getJavaDate().getTime());
				committer.setNumberOfCommits(committer.getNumberOfCommits()+1);
				db.getCommitters().sync();
			}
		}
	}

}
