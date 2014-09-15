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
	@Type(value = RedmineProjectVersion.class, name="RedmineProjectVersion"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedmineProjectVersion extends Object {

	protected String name;
	protected String description;
	protected String created_on;
	protected String updated_on;
	protected String status;
	
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getCreated_on() {
		return created_on;
	}
	public String getUpdated_on() {
		return updated_on;
	}
	public String getStatus() {
		return status;
	}
	
}
