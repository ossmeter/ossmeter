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
	@Type(value = RedminIssueRelation.class, name="RedminIssueRelation"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedminIssueRelation extends Object {

	protected RedmineIssue relatedIssue;
	protected String type;
	
	public String getType() {
		return type;
	}
	
}
