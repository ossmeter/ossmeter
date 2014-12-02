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

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = GitHubDownload.class, name="org.ossmeter.repository.model.github.GitHubDownload"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubDownload extends Object {

	protected int _id;
	protected String url;
	protected String html_url;
	protected String name;
	protected String description;
	protected int size;
	protected int download_count;
	protected String content_type;
	
	public int get_id() {
		return _id;
	}
	public String getUrl() {
		return url;
	}
	public String getHtml_url() {
		return html_url;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public int getSize() {
		return size;
	}
	public int getDownload_count() {
		return download_count;
	}
	public String getContent_type() {
		return content_type;
	}
	
}
