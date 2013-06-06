package org.ossmeter.metricprovider.loc;

import java.util.List;

import org.ossmeter.metricprovider.loc.model.LinesOfCodeData;
import org.ossmeter.metricprovider.loc.model.Loc;
import org.ossmeter.metricprovider.loc.model.RepositoryData;
import org.ossmeter.platform.Constants;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.vcs.PlatformVcsManager;
import org.ossmeter.platform.delta.vcs.VcsCommit;
import org.ossmeter.platform.delta.vcs.VcsCommitItem;
import org.ossmeter.platform.delta.vcs.VcsProjectDelta;
import org.ossmeter.platform.delta.vcs.VcsRepositoryDelta;
import org.ossmeter.repository.model.GitRepository;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.SvnRepository;
import org.ossmeter.repository.model.VcsRepository;

import com.mongodb.DB;

public class LocMetricProvider implements ITransientMetricProvider<Loc> {
	
	protected PlatformVcsManager vcsManager;
	
	@Override
	public String getIdentifier() {
		return LocMetricProvider.class.getCanonicalName();
	}
	
	@Override
	public Loc adapt(DB db) {
		return new Loc(db);
	}
	
	@Override
	public boolean appliesTo(Project project) {
		return project.getVcsRepositories().size() > 0;
	}
	
	@Override
	public void measure(Project project, ProjectDelta projectDelta, Loc db) {
		try {
			VcsProjectDelta vcsDelta = projectDelta.getVcsDelta();
			
			for (VcsRepositoryDelta vcsRepositoryDelta : vcsDelta.getRepoDeltas()) {
				VcsRepository vcsRepository = vcsRepositoryDelta.getRepository();
				
				RepositoryData repositoryData = db.getRepositories().findOneByUrl(vcsRepository.getUrl());

				if (repositoryData != null && 
						vcsManager.compareVersions(vcsRepository, 
								repositoryData.getRevision(), 
								vcsManager.getCurrentRevision(vcsRepository)) > -1){
					// TODO: Is this necessary??? We are given a DELTA... It should only contain NEW things.
					continue;
				}
				
				if (repositoryData == null) {
					repositoryData = new RepositoryData();
					repositoryData.setRepoType(getRepositoryType(vcsRepository));
					repositoryData.setUrl(vcsRepository.getUrl());
					db.getRepositories().add(repositoryData);
				}
				
				List<VcsCommitItem> compactedCommitItems = vcsRepositoryDelta.getCompactedCommitItems();
				if (compactedCommitItems.size() == 0) {
					continue;
				}
 				for (VcsCommitItem item : compactedCommitItems) {
					String contents = vcsManager.getContents(item);
					int lines = contents.split(Constants.NEW_LINE).length;
					
					LinesOfCodeData locData = db.getLinesOfCode().findOneByFile(vcsRepository.getUrl() + item.getPath());
					if (locData == null) {
						locData = new LinesOfCodeData();
						locData.setFile(vcsRepository.getUrl() + item.getPath());
						locData.setRevisionNumber(item.getCommit().getRevision());
						db.getLinesOfCode().add(locData);
						repositoryData.getLinesOfCode().add(locData);
					}
					locData.setLines(lines);
				}
				
				// This needs to be the last commit version
				List<VcsCommit> commits = vcsRepositoryDelta.getCommits();
				VcsCommit top = commits.get(0);
				VcsCommit bottom = commits.get(commits.size()-1);
				
				int comparison = vcsManager.compareVersions(vcsRepository, top.getRevision(), bottom.getRevision());
				String revision = null;
				if (comparison < 0){ // top == bottom
					revision = bottom.getRevision();
				} else { // bottom > top || top == bottom
					revision = top.getRevision();
				}
				repositoryData.setRevision(revision);
				db.sync();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected String getRepositoryType(VcsRepository vcsRepository) {
		if (vcsRepository instanceof SvnRepository) {
			return "SVN";
		} else if (vcsRepository instanceof GitRepository) {
			return "GIT";
		}
		return "UNKNOWN";
	}
	
	@Override
	public void setUses(List<IMetricProvider> uses) {
		// DO NOTHING -- we don't use anything
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return null;
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.vcsManager = context.getPlatformVcsManager();
	}
}
