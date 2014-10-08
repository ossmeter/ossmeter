package org.ossmeter.platform.bugtrackingsystem.github;

import java.util.ArrayList;
import java.util.List;

import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;

public class GitHubBugTrackingSystemDelta extends BugTrackingSystemDelta {

	private static final long serialVersionUID = 1L;

	private List<GitHubPullRequest> pullRequests = new ArrayList<GitHubPullRequest>();

	public List<GitHubPullRequest> getPullRequests() {
		return pullRequests;
	}
}
