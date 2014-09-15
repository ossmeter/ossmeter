//package org.ossmeter.repository.model.redmine;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = RedmineIssue.class, name="RedmineIssue"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedmineIssue extends Object {

	protected List<RedminIssueRelation> relations;
	protected RedmineCategory category;
	protected String description;
	protected String status;
	protected RedmineFeature feature;
	protected String priority;
	protected RedmineUser author;
	protected String template;
	protected String start_date;
	protected String update_date;
	protected String due_date;
	protected RedmineUser assignedTo;
	
	public String getDescription() {
		return description;
	}
	public String getStatus() {
		return status;
	}
	public String getPriority() {
		return priority;
	}
	public String getTemplate() {
		return template;
	}
	public String getStart_date() {
		return start_date;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public String getDue_date() {
		return due_date;
	}
	
	public List<RedminIssueRelation> getRelations() {
		return relations;
	}
}
