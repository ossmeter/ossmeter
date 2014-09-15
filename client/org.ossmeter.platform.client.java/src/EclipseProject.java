//package org.ossmeter.repository.model.eclipse;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = EclipseProject.class, name="EclipseProject"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class EclipseProject extends Project {

	protected List<EclipsePlatform> platforms;
	protected List<Review> reviews;
	protected List<Article> articles;
	protected List<Release> releases;
	protected String paragraphUrl;
	protected String descriptionUrl;
	protected String downloadsUrl;
	protected String homePage;
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
	public String getHomePage() {
		return homePage;
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
