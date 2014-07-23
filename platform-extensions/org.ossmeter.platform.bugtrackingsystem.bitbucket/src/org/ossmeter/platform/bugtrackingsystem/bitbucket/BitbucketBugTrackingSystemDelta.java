package org.ossmeter.platform.bugtrackingsystem.bitbucket;


import java.util.ArrayList;
import java.util.List;

import org.ossmeter.platform.bugtrackingsystem.bitbucket.api.BitbucketPullRequest;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;


public class BitbucketBugTrackingSystemDelta extends BugTrackingSystemDelta {

	private static final long serialVersionUID = 1L;
	
	private List<BitbucketPullRequest> pullRequests = new ArrayList<BitbucketPullRequest>();

	public List<BitbucketPullRequest> getPullRequests() {
		return pullRequests;
	}
	
}
