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
package org.ossmeter.repository.model.eclipse;

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
	@Type(value = EclipseProject.class, name="org.ossmeter.repository.model.eclipse.EclipseProject"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class EclipseProject extends Project {

	protected List<EclipsePlatform> platforms;
	protected List<Review> reviews;
	protected List<Article> articles;
	protected List<Release> releases;
	protected String paragraphUrl;
	protected String descriptionUrl;
	protected String downloadsUrl;
	protected String projectplanUrl;
	protected String updatesiteUrl;
	protected String state;
	
	public String getParagraphUrl() {
		return paragraphUrl;
	}
	public String getDescriptionUrl() {
		return descriptionUrl;
	}
	public String getDownloadsUrl() {
		return downloadsUrl;
	}
	public String getProjectplanUrl() {
		return projectplanUrl;
	}
	public String getUpdatesiteUrl() {
		return updatesiteUrl;
	}
	public String getState() {
		return state;
	}
	
	public List<EclipsePlatform> getPlatforms() {
		return platforms;
	}
	public List<Review> getReviews() {
		return reviews;
	}
	public List<Article> getArticles() {
		return articles;
	}
	public List<Release> getReleases() {
		return releases;
	}
}
