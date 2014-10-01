package org.ossmeter.repository.model.eclipse;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import org.ossmeter.repository.model.redmine.*;
import org.ossmeter.repository.model.vcs.svn.*;
import org.ossmeter.repository.model.cc.forum.*;
import org.ossmeter.repository.model.bts.bugzilla.*;
import org.ossmeter.repository.model.cc.nntp.*;
import org.ossmeter.repository.model.vcs.cvs.*;
import org.ossmeter.repository.model.eclipse.*;
import org.ossmeter.repository.model.googlecode.*;
import org.ossmeter.repository.model.vcs.git.*;
import org.ossmeter.repository.model.sourceforge.*;
import org.ossmeter.repository.model.github.*;
import org.ossmeter.repository.model.*;
import org.ossmeter.repository.model.metrics.*;

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
