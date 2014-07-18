package org.ossmeter.platform.bugtrackingsystem.redmine.api;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RedmineCommentDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private String property;
	private String name;
	
	@JsonProperty("old_value")
	private String oldValue;
	
	@JsonProperty("new_value")
	private String newValue;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

}
