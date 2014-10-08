package org.ossmeter.platform.bugtrackingsystem.bitbucket.api;

import java.io.Serializable;

public class BitbucketCommitSummary implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String hash;
	private BitbucketLinks links;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public BitbucketLinks getLinks() {
		return links;
	}
	
	public void setLinks(BitbucketLinks links) {
		this.links = links;
	}
}
