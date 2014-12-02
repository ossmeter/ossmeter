/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.ossmeter.repository.model.redmine;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import org.ossmeter.repository.model.*;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = RedmineProject.class, name="org.ossmeter.repository.model.redmine.RedmineProject"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedmineProject extends Project {

	protected List<RedmineBugIssueTracker> issueTracker;
	protected List<RedmineUser> members;
	protected List<RedmineProjectVersion> versions;
	protected String identifier;
	protected String description;
	protected String created_on;
	protected String updated_on;
	protected RedmineWiki wiki;
	protected RedmineQueryManager queryManager;
	
	public String getIdentifier() {
		return identifier;
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
	
	public List<RedmineBugIssueTracker> getIssueTracker() {
		return issueTracker;
	}
	public List<RedmineUser> getMembers() {
		return members;
	}
	public List<RedmineProjectVersion> getVersions() {
		return versions;
	}
}
