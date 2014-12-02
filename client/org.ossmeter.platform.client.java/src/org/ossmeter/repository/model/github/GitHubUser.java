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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import org.ossmeter.repository.model.*;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = GitHubUser.class, name="org.ossmeter.repository.model.github.GitHubUser"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubUser extends Person {

	protected String login;
	protected String html_url;
	protected String url;
	protected String followers_url;
	
	public String getLogin() {
		return login;
	}
	public String getHtml_url() {
		return html_url;
	}
	public String getUrl() {
		return url;
	}
	public String getFollowers_url() {
		return followers_url;
	}
	
}
