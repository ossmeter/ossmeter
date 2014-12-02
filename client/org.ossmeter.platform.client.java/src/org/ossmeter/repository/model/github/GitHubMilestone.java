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
package org.ossmeter.repository.model.github;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = GitHubMilestone.class, name="org.ossmeter.repository.model.github.GitHubMilestone"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubMilestone extends Object {

	protected List<GitHubIssue> open_issues;
	protected List<GitHubIssue> closed_issues;
	protected String url;
	protected int number;
	protected String state;
	protected String title;
	protected String description;
	protected GitHubUser creator;
	protected String created_at;
	protected String due_on;
	
	public String getUrl() {
		return url;
	}
	public int getNumber() {
		return number;
	}
	public String getState() {
		return state;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public String getCreated_at() {
		return created_at;
	}
	public String getDue_on() {
		return due_on;
	}
	
	public List<GitHubIssue> getOpen_issues() {
		return open_issues;
	}
	public List<GitHubIssue> getClosed_issues() {
		return closed_issues;
	}
}
