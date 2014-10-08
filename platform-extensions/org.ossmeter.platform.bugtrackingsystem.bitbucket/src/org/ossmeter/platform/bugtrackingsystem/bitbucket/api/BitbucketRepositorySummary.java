package org.ossmeter.platform.bugtrackingsystem.bitbucket.api;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitbucketRepositorySummary implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("full_name")
	private String fullName;
	private String name;
	private BitbucketLinks links;

	public String getFullName() {
		return fullName;
	}

	public String getName() {
		return name;
	}

	public BitbucketLinks getLinks() {
		return links;
	}
}
