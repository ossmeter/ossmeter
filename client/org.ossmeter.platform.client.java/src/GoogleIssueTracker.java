//package org.ossmeter.repository.model.googlecode;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = GoogleIssueTracker.class, name="GoogleIssueTracker"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleIssueTracker extends BugTrackingSystem {

	protected List<GoogleIssue> issues;
	protected String url;
	
	public String getUrl() {
		return url;
	}
	
	public List<GoogleIssue> getIssues() {
		return issues;
	}
}
