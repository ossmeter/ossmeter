//package org.ossmeter.repository.model.sourceforge;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = BugTS.class, name="BugTS"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class BugTS extends Tracker {

	protected String description;
	protected Person assignee;
	protected Person submitted;
	protected int priority;
	protected String resolutionStatus;
	protected String bugVisibility;
	
	public String getDescription() {
		return description;
	}
	public int getPriority() {
		return priority;
	}
	public String getResolutionStatus() {
		return resolutionStatus;
	}
	public String getBugVisibility() {
		return bugVisibility;
	}
	
}
