package org.ossmeter.platform.bugtrackingsystem.bitbucket.api;

import org.ossmeter.platform.bugtrackingsystem.PagedIterator;

class BitbucketPullRequestIterator extends PagedIterator<BitbucketPullRequest> {
	private BitbucketRestClient bitbucket;
	private String user;
	private String repository;

	public BitbucketPullRequestIterator(BitbucketRestClient bitbucket,
			String user, String repository) {
		this.bitbucket = bitbucket;
		this.user = user;
		this.repository = repository;
	}
	
	@Override
	protected int getFirstPageNumber() {
		return 1;
	}

	protected Page<BitbucketPullRequest> getNextPage() {
		BitbucketPullRequestPage result;
		try {
			result = bitbucket.getPullRequests(user, repository, getNextPageNumber());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new Page<BitbucketPullRequest>(result.getValues(), result.getSize());
	}

}
