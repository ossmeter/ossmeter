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
	@Type(value = Person.class, name="org.ossmeter.repository.model.Person"), 	@Type(value = org.ossmeter.repository.model.github.GitHubUser.class, name="org.ossmeter.repository.model.github.GitHubUser"),
	@Type(value = org.ossmeter.repository.model.googlecode.GoogleUser.class, name="org.ossmeter.repository.model.googlecode.GoogleUser"),
	@Type(value = org.ossmeter.repository.model.redmine.RedmineUser.class, name="org.ossmeter.repository.model.redmine.RedmineUser"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person extends NamedElement {

	protected List<Role> roles;
	protected String homePage;
	
	public String getHomePage() {
		return homePage;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
}
