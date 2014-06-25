package org.ossmeter.repository.model.eclipse;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class EclipseProject extends org.ossmeter.repository.model.Project {
	
	protected List<EclipsePlatform> platforms = null;
	protected List<Review> reviews = null;
	protected List<Article> articles = null;
	protected List<Release> releases = null;
	
	
	public EclipseProject() { 
		super();
		dbObject.put("platforms", new BasicDBList());
		dbObject.put("reviews", new BasicDBList());
		dbObject.put("articles", new BasicDBList());
		dbObject.put("releases", new BasicDBList());
		super.setSuperTypes("org.ossmeter.repository.model.eclipse.Project");
		SHORTNAME.setOwningType("org.ossmeter.repository.model.eclipse.EclipseProject");
		PARAGRAPHURL.setOwningType("org.ossmeter.repository.model.eclipse.EclipseProject");
		DESCRIPTIONURL.setOwningType("org.ossmeter.repository.model.eclipse.EclipseProject");
		DOWNLOADSURL.setOwningType("org.ossmeter.repository.model.eclipse.EclipseProject");
		HOMEPAGE.setOwningType("org.ossmeter.repository.model.eclipse.EclipseProject");
		PROJECTPLANURL.setOwningType("org.ossmeter.repository.model.eclipse.EclipseProject");
		UPDATESITEURL.setOwningType("org.ossmeter.repository.model.eclipse.EclipseProject");
		STATUS.setOwningType("org.ossmeter.repository.model.eclipse.EclipseProject");
		USERNAME.setOwningType("org.ossmeter.repository.model.eclipse.EclipseProject");
		PASSWORD.setOwningType("org.ossmeter.repository.model.eclipse.EclipseProject");
	}
	
	public static StringQueryProducer SHORTNAME = new StringQueryProducer("shortName"); 
	public static StringQueryProducer PARAGRAPHURL = new StringQueryProducer("paragraphUrl"); 
	public static StringQueryProducer DESCRIPTIONURL = new StringQueryProducer("descriptionUrl"); 
	public static StringQueryProducer DOWNLOADSURL = new StringQueryProducer("downloadsUrl"); 
	public static StringQueryProducer HOMEPAGE = new StringQueryProducer("homePage"); 
	public static StringQueryProducer PROJECTPLANURL = new StringQueryProducer("projectplanUrl"); 
	public static StringQueryProducer UPDATESITEURL = new StringQueryProducer("updatesiteUrl"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static StringQueryProducer USERNAME = new StringQueryProducer("userName"); 
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	
	
	public String getShortName() {
		return parseString(dbObject.get("shortName")+"", "");
	}
	
	public EclipseProject setShortName(String shortName) {
		dbObject.put("shortName", shortName);
		notifyChanged();
		return this;
	}
	public String getParagraphUrl() {
		return parseString(dbObject.get("paragraphUrl")+"", "");
	}
	
	public EclipseProject setParagraphUrl(String paragraphUrl) {
		dbObject.put("paragraphUrl", paragraphUrl);
		notifyChanged();
		return this;
	}
	public String getDescriptionUrl() {
		return parseString(dbObject.get("descriptionUrl")+"", "");
	}
	
	public EclipseProject setDescriptionUrl(String descriptionUrl) {
		dbObject.put("descriptionUrl", descriptionUrl);
		notifyChanged();
		return this;
	}
	public String getDownloadsUrl() {
		return parseString(dbObject.get("downloadsUrl")+"", "");
	}
	
	public EclipseProject setDownloadsUrl(String downloadsUrl) {
		dbObject.put("downloadsUrl", downloadsUrl);
		notifyChanged();
		return this;
	}
	public String getHomePage() {
		return parseString(dbObject.get("homePage")+"", "");
	}
	
	public EclipseProject setHomePage(String homePage) {
		dbObject.put("homePage", homePage);
		notifyChanged();
		return this;
	}
	public String getProjectplanUrl() {
		return parseString(dbObject.get("projectplanUrl")+"", "");
	}
	
	public EclipseProject setProjectplanUrl(String projectplanUrl) {
		dbObject.put("projectplanUrl", projectplanUrl);
		notifyChanged();
		return this;
	}
	public String getUpdatesiteUrl() {
		return parseString(dbObject.get("updatesiteUrl")+"", "");
	}
	
	public EclipseProject setUpdatesiteUrl(String updatesiteUrl) {
		dbObject.put("updatesiteUrl", updatesiteUrl);
		notifyChanged();
		return this;
	}
	public String getStatus() {
		return parseString(dbObject.get("status")+"", "");
	}
	
	public EclipseProject setStatus(String status) {
		dbObject.put("status", status);
		notifyChanged();
		return this;
	}
	public String getUserName() {
		return parseString(dbObject.get("userName")+"", "");
	}
	
	public EclipseProject setUserName(String userName) {
		dbObject.put("userName", userName);
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public EclipseProject setPassword(String password) {
		dbObject.put("password", password);
		notifyChanged();
		return this;
	}
	
	
	public List<EclipsePlatform> getPlatforms() {
		if (platforms == null) {
			platforms = new PongoList<EclipsePlatform>(this, "platforms", true);
		}
		return platforms;
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