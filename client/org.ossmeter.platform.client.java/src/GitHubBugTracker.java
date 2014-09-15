//package org.ossmeter.repository.model.github;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = GitHubBugTracker.class, name="GitHubBugTracker"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubBugTracker extends BugTrackingSystem {

	protected List<GitHubIssue> issues;
	
	
	public List<GitHubIssue> getIssues() {
		return issues;
	}
}
