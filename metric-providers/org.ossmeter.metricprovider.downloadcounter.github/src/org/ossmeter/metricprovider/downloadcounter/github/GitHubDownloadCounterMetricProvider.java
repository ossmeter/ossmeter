package org.ossmeter.metricprovider.downloadcounter.github;

import org.eclipse.egit.github.core.Download;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.DownloadService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.ossmeter.metricprovider.downloadcounter.model.DownloadCounter;
import org.ossmeter.platform.AbstractTransientMetricProvider;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.github.GitHubProject;
import org.ossmeter.repository.model.github.GitHubRepository;
import org.ossmeter.repository.model.github.GitHubUser;

import com.mongodb.DB;

public class GitHubDownloadCounterMetricProvider extends AbstractTransientMetricProvider<DownloadCounter>{

	@Override
	public boolean appliesTo(Project project) {
		return project instanceof GitHubProject;
	}

	@Override
	public DownloadCounter adapt(DB db) {
		return new DownloadCounter(db);
	}

	@Override
	public void measure(Project project, ProjectDelta delta, DownloadCounter db) {
		
		if (!new Date().toString().equals(delta.getDate().toString())) return;
		
		GitHubProject gitHubProject = (GitHubProject) project;
		
		GitHubRepository gitHubRepository = ((GitHubRepository)gitHubProject.getVcsRepositories().get(0));
		String owner = gitHubRepository.getOwner().getLogin();
		
		try {
			RepositoryService repositoryService = new RepositoryService();
			Repository repository = null;
			for (Repository r : repositoryService.getRepositories(owner)) {
				if (gitHubRepository.getName().equals(r.getName())) repository = r;
			}
			DownloadService downloadService = new DownloadService();
			
			int downloads = 0;
			
			for (Download download : downloadService.getDownloads(repository)) {
				downloads += download.getDownloadCount();
			}
			
			org.ossmeter.metricprovider.downloadcounter.model.Download download = null;
			if (db.getDownloads().size() == 0) {
				download = new org.ossmeter.metricprovider.downloadcounter.model.Download();
				db.getDownloads().add(download);
			}
			download.setCounter(downloads);
			db.sync();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		
		GitHubDownloadCounterMetricProvider provider = new GitHubDownloadCounterMetricProvider();
		provider.measure(provider.createGitHubProject("mojombo", "grit"), null, null);
		
	}
	
	public GitHubProject createGitHubProject(String login, String repository) {
		GitHubProject project = new GitHubProject();
		
		GitHubRepository gitHubRepository = new GitHubRepository();
		gitHubRepository.setName(repository);
		
		GitHubUser owner = new GitHubUser();
		owner.setLogin(login);
		gitHubRepository.setOwner(owner);
		
		project.getVcsRepositories().add(gitHubRepository);
		
		return project;
	}

	@Override
	public String getShortIdentifier() {
		return "ghdc";
	}

	@Override
	public String getFriendlyName() {
		return "Download counter";
	}

	@Override
	public String getSummaryInformation() {
		return "Lorum ipsum";
	}
}
