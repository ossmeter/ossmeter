package org.ossmeter.platform.bugtrackingsystem.github;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;

public class GitHubBugTrackingSystemDelta extends BugTrackingSystemDelta {

    private static final long serialVersionUID = 1L;

    private Map<Integer, GitHubMilestone> milestones = new HashMap<Integer, GitHubMilestone>();
    private Map<String, GitHubUser> users = new HashMap<String, GitHubUser>();
    private Map<Long, GitHubRepository> repositories = new HashMap<Long, GitHubRepository>();
    
    private List<GitHubPullRequest> pullRequests = new ArrayList<GitHubPullRequest>();
    
    // Pull Requests
    
    public void addGitHubPullRequest(GitHubPullRequest pullRequest) {
        pullRequests.add(pullRequest);
    }
    
    public List<GitHubPullRequest> getPullRequests() {
        return Collections.unmodifiableList(pullRequests);
    }
    
    // Milestones
    
    public void addMilestone(GitHubMilestone milestone) {
        milestones.put(milestone.getNumber(), milestone);
    }

    public GitHubMilestone getMilestone(int number) {
        return milestones.get(number);
    }

    public boolean hasMilestone(int number) {
        return milestones.containsKey(number);
    }
    
    // Users
    
    public void addUser(GitHubUser user) {
        users.put(user.getId(), user);
    }
    
    public GitHubUser getUser(String id) {
        return users.get(id);
    }
    
    public boolean hasUser(String id) {
        return users.containsKey(id);
    }
    
    // Repositories
    
    public void addRepository(GitHubRepository repository) {
        repositories.put(repository.getId(), repository);
    }
    
    public GitHubRepository getRepository(long id) {
        return repositories.get(id);
    }
    
    public boolean hasRepository(long id) {
        return repositories.containsKey(id);
    }

}
