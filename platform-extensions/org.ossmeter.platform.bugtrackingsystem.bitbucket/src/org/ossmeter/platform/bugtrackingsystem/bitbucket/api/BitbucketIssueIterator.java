package org.ossmeter.platform.bugtrackingsystem.bitbucket.api;

import org.ossmeter.platform.bugtrackingsystem.PagedIterator;

public class BitbucketIssueIterator extends PagedIterator<BitbucketIssue> {

	private BitbucketRestClient bitbucket;
	private BitbucketIssueQuery query;
	private boolean retrieveComments;

	public BitbucketIssueIterator(BitbucketRestClient bitbucket,
			BitbucketIssueQuery query, boolean retrieveComments) {
		this.bitbucket = bitbucket;
		this.query = query;
		this.retrieveComments = retrieveComments;
	}

	protected Page<BitbucketIssue> getNextPage() {
		BitbucketSearchResult result;
		try {
			result = bitbucket.search(query, retrieveComments, getRetrieved());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new Page<BitbucketIssue>(result.getIssues(),
				result.getCount());
	}

}
