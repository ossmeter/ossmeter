package org.ossmeter.platform.bugtrackingsystem.bitbucket.api;

import java.io.Serializable;

public class BitbucketPullRequestRepository implements Serializable {
	private static final long serialVersionUID = 1L;

	private BitbucketRepositorySummary repository;
	private BitbucketCommitSummary commit;
	private BitbucketBranchSummary branch;

	public BitbucketRepositorySummary getRepository() {
		return repository;
	}

	public BitbucketCommitSummary getCommit() {
		return commit;
	}

	public BitbucketBranchSummary getBranch() {
		return branch;
	}
}
