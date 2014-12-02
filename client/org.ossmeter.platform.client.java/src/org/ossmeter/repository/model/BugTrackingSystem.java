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
package org.ossmeter.repository.model;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = BugTrackingSystem.class, name="org.ossmeter.repository.model.BugTrackingSystem"), 	@Type(value = org.ossmeter.repository.model.bts.bugzilla.Bugzilla.class, name="org.ossmeter.repository.model.bts.bugzilla.Bugzilla"),
	@Type(value = org.ossmeter.repository.model.github.GitHubBugTracker.class, name="org.ossmeter.repository.model.github.GitHubBugTracker"),
	@Type(value = org.ossmeter.repository.model.googlecode.GoogleIssueTracker.class, name="org.ossmeter.repository.model.googlecode.GoogleIssueTracker"),
	@Type(value = org.ossmeter.repository.model.redmine.RedmineBugIssueTracker.class, name="org.ossmeter.repository.model.redmine.RedmineBugIssueTracker"),
	@Type(value = org.ossmeter.repository.model.sourceforge.SourceForgeBugTrackingSystem.class, name="org.ossmeter.repository.model.sourceforge.SourceForgeBugTrackingSystem"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BugTrackingSystem extends Object {

	protected List<Person> persons;
	protected String url;
	
	public String getUrl() {
		return url;
	}
	
	public List<Person> getPersons() {
		return persons;
	}
}
