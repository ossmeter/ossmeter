package org.ossmeter.platform.bugtrackingsystem.bitbucket.api;

import java.util.List;

public class BitbucketPullRequestPage {
	private String next;
	private int page;
	private int size;
	private List<BitbucketPullRequest> values;
	
	public  List<BitbucketPullRequest> getValues() {
		return values;
	}
	
	public String getNext() {
		return next;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getPage() {
		return page;
	}
}
