package org.ossmeter.repository.model.eclipseforge;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class EclipseForgeProject extends org.ossmeter.repository.model.Project {
	
	protected List<org.ossmeter.repository.model.Person> committers = null;
	protected List<org.ossmeter.repository.model.Person> leaders = null;
	protected List<org.ossmeter.repository.model.Person> mentors = null;
	protected List<Review> reviews = null;
	protected List<Article> articles = null;
	protected List<Release> releases = null;
	
	
	public EclipseForgeProject() { 
		super();
		dbObject.put("committers", new BasicDBList());
		dbObject.put("leaders", new BasicDBList());
		dbObject.put("mentors", new BasicDBList());
		dbObject.put("reviews", new BasicDBList());
		dbObject.put("articles", new BasicDBList());
		dbObject.put("releases", new BasicDBList());
	}
	
	public String getShortName() {
		return parseString(dbObject.get("shortName")+"", "");
	}
	
	public EclipseForgeProject setShortName(String shortName) {
		dbObject.put("shortName", shortName + "");
		notifyChanged();
		return this;
	}
	public String getParagraphUrl() {
		return parseString(dbObject.get("paragraphUrl")+"", "");
	}
	
	public EclipseForgeProject setParagraphUrl(String paragraphUrl) {
		dbObject.put("paragraphUrl", paragraphUrl + "");
		notifyChanged();
		return this;
	}
	public String getDescriptionUrl() {
		return parseString(dbObject.get("descriptionUrl")+"", "");
	}
	
	public EclipseForgeProject setDescriptionUrl(String descriptionUrl) {
		dbObject.put("descriptionUrl", descriptionUrl + "");
		notifyChanged();
		return this;
	}
	public String getDownloadsUrl() {
		return parseString(dbObject.get("downloadsUrl")+"", "");
	}
	
	public EclipseForgeProject setDownloadsUrl(String downloadsUrl) {
		dbObject.put("downloadsUrl", downloadsUrl + "");
		notifyChanged();
		return this;
	}
	public String getHomePage() {
		return parseString(dbObject.get("homePage")+"", "");
	}
	
	public EclipseForgeProject setHomePage(String homePage) {
		dbObject.put("homePage", homePage + "");
		notifyChanged();
		return this;
	}
	public String getProjectplanUrl() {
		return parseString(dbObject.get("projectplanUrl")+"", "");
	}
	
	public EclipseForgeProject setProjectplanUrl(String projectplanUrl) {
		dbObject.put("projectplanUrl", projectplanUrl + "");
		notifyChanged();
		return this;
	}
	public String getUpdatesiteUrl() {
		return parseString(dbObject.get("updatesiteUrl")+"", "");
	}
	
	public EclipseForgeProject setUpdatesiteUrl(String updatesiteUrl) {
		dbObject.put("updatesiteUrl", updatesiteUrl + "");
		notifyChanged();
		return this;
	}
	
	
	public List<org.ossmeter.repository.model.Person> getCommitters() {
		if (committers == null) {
			committers = new PongoList<org.ossmeter.repository.model.Person>(this, "committers", true);
		}
		return committers;
	}
	public List<org.ossmeter.repository.model.Person> getLeaders() {
		if (leaders == null) {
			leaders = new PongoList<org.ossmeter.repository.model.Person>(this, "leaders", true);
		}
		return leaders;
	}
	public List<org.ossmeter.repository.model.Person> getMentors() {
		if (mentors == null) {
			mentors = new PongoList<org.ossmeter.repository.model.Person>(this, "mentors", true);
		}
		return mentors;
	}
	public List<Review> getReviews() {
		if (reviews == null) {
			reviews = new PongoList<Review>(this, "reviews", true);
		}
		return reviews;
	}
	public List<Article> getArticles() {
		if (articles == null) {
			articles = new PongoList<Article>(this, "articles", true);
		}
		return articles;
	}
	public List<Release> getReleases() {
		if (releases == null) {
			releases = new PongoList<Release>(this, "releases", true);
		}
		return releases;
	}
	
	
}