package org.ossmeter.platform.bugtrackingsystem.bitbucket.api;

import java.util.Iterator;

public class BitbucketIssueIterator implements Iterator<BitbucketIssue> {

	private BitbucketRestClient bitbucket;
	private BitbucketIssueQuery query;
	private boolean retrieveComments;

	private int total;
	private int retrieved = 0;
	private Iterator<BitbucketIssue> iterator;

	public BitbucketIssueIterator(BitbucketRestClient bitbucket,
			BitbucketIssueQuery query, boolean retrieveComments) {
		this.bitbucket = bitbucket;
		this.query = query;
		this.retrieveComments = retrieveComments;
	}
	
	public BitbucketIssueIterator(BitbucketRestClient bitbucket,
			BitbucketIssueQuery query, boolean retrieveComments, int startAt) {
		this.bitbucket = bitbucket;
		this.query = query;
		this.retrieveComments = retrieveComments;
		this.retrieved = startAt;
	}

	@Override
	public boolean hasNext() {
		if (null == iterator) {
			return getNextPage();
		} else if (iterator.hasNext()) {
			return true;
		} else if (retrieved >= total) {
			return false;
		} else {
			return getNextPage();
		}
	}

	@Override
	public BitbucketIssue next() {
		return iterator.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	protected boolean getNextPage() {
		BitbucketSearchResult result;
		try {
			result = bitbucket.search(query, retrieveComments, retrieved);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		total = result.getCount();
		retrieved += result.getIssues().size();
		iterator = result.getIssues().iterator();
		return iterator.hasNext();
	}

}
