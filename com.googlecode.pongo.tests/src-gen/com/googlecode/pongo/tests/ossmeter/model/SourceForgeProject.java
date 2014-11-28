package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class SourceForgeProject extends Project {
	
	protected List<License> license = null;
	protected List<OS> os = null;
	protected List<Topic> topics = null;
	protected List<ProgrammingLanguage> programminLanguages = null;
	protected List<Audience> audiences = null;
	protected List<Translation> translations = null;
	protected List<Environment> environments = null;
	protected List<Category> categories = null;
	protected List<Tracker> trackers = null;
	protected List<Maintainer> maintainers = null;
	protected List<Developer> developers = null;
	protected Donation donation = null;
	
	
	public SourceForgeProject() { 
		super();
		dbObject.put("donation", new BasicDBObject());
		dbObject.put("license", new BasicDBList());
		dbObject.put("os", new BasicDBList());
		dbObject.put("topics", new BasicDBList());
		dbObject.put("programminLanguages", new BasicDBList());
		dbObject.put("audiences", new BasicDBList());
		dbObject.put("translations", new BasicDBList());
		dbObject.put("environments", new BasicDBList());
		dbObject.put("categories", new BasicDBList());
		dbObject.put("trackers", new BasicDBList());
		dbObject.put("maintainers", new BasicDBList());
		dbObject.put("developers", new BasicDBList());
		super.setSuperTypes("com.googlecode.pongo.tests.ossmeter.model.Project","com.googlecode.pongo.tests.ossmeter.model.NamedElement");
		NAME.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SourceForgeProject");
		DESCRIPTION.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SourceForgeProject");
		YEAR.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SourceForgeProject");
		ACTIVE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SourceForgeProject");
		CREATED.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SourceForgeProject");
		CREATEDTIMESTAMP.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SourceForgeProject");
		PROJECTID.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SourceForgeProject");
		_PRIVATE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SourceForgeProject");
		TYPE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SourceForgeProject");
		SHORTDESC.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SourceForgeProject");
		PERCENTILE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SourceForgeProject");
		RANKING.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SourceForgeProject");
		DOWNLOADPAGE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SourceForgeProject");
		SUPPORTPAGE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SourceForgeProject");
		SUMMARYPAGE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SourceForgeProject");
		HOMEPAGE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SourceForgeProject");
		BASEURL.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SourceForgeProject");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static NumericalQueryProducer YEAR = new NumericalQueryProducer("year");
	public static StringQueryProducer ACTIVE = new StringQueryProducer("active"); 
	public static StringQueryProducer CREATED = new StringQueryProducer("created"); 
	public static NumericalQueryProducer CREATEDTIMESTAMP = new NumericalQueryProducer("createdTimestamp");
	public static NumericalQueryProducer PROJECTID = new NumericalQueryProducer("projectId");
	public static NumericalQueryProducer _PRIVATE = new NumericalQueryProducer("_private");
	public static NumericalQueryProducer TYPE = new NumericalQueryProducer("type");
	public static StringQueryProducer SHORTDESC = new StringQueryProducer("shortDesc"); 
	public static NumericalQueryProducer PERCENTILE = new NumericalQueryProducer("percentile");
	public static NumericalQueryProducer RANKING = new NumericalQueryProducer("ranking");
	public static StringQueryProducer DOWNLOADPAGE = new StringQueryProducer("downloadPage"); 
	public static StringQueryProducer SUPPORTPAGE = new StringQueryProducer("supportPage"); 
	public static StringQueryProducer SUMMARYPAGE = new StringQueryProducer("summaryPage"); 
	public static StringQueryProducer HOMEPAGE = new StringQueryProducer("homePage"); 
	public static StringQueryProducer BASEURL = new StringQueryProducer("baseUrl"); 
	
	
	public String getCreated() {
		return parseString(dbObject.get("created")+"", "");
	}
	
	public SourceForgeProject setCreated(String created) {
		dbObject.put("created", created);
		notifyChanged();
		return this;
	}
	public int getCreatedTimestamp() {
		return parseInteger(dbObject.get("createdTimestamp")+"", 0);
	}
	
	public SourceForgeProject setCreatedTimestamp(int createdTimestamp) {
		dbObject.put("createdTimestamp", createdTimestamp);
		notifyChanged();
		return this;
	}
	public int getProjectId() {
		return parseInteger(dbObject.get("projectId")+"", 0);
	}
	
	public SourceForgeProject setProjectId(int projectId) {
		dbObject.put("projectId", projectId);
		notifyChanged();
		return this;
	}
	public int get_private() {
		return parseInteger(dbObject.get("_private")+"", 0);
	}
	
	public SourceForgeProject set_private(int _private) {
		dbObject.put("_private", _private);
		notifyChanged();
		return this;
	}
	public int getType() {
		return parseInteger(dbObject.get("type")+"", 0);
	}
	
	public SourceForgeProject setType(int type) {
		dbObject.put("type", type);
		notifyChanged();
		return this;
	}
	public String getShortDesc() {
		return parseString(dbObject.get("shortDesc")+"", "");
	}
	
	public SourceForgeProject setShortDesc(String shortDesc) {
		dbObject.put("shortDesc", shortDesc);
		notifyChanged();
		return this;
	}
	public float getPercentile() {
		return parseFloat(dbObject.get("percentile")+"", 0.0f);
	}
	
	public SourceForgeProject setPercentile(float percentile) {
		dbObject.put("percentile", percentile);
		notifyChanged();
		return this;
	}
	public int getRanking() {
		return parseInteger(dbObject.get("ranking")+"", 0);
	}
	
	public SourceForgeProject setRanking(int ranking) {
		dbObject.put("ranking", ranking);
		notifyChanged();
		return this;
	}
	public String getDownloadPage() {
		return parseString(dbObject.get("downloadPage")+"", "");
	}
	
	public SourceForgeProject setDownloadPage(String downloadPage) {
		dbObject.put("downloadPage", downloadPage);
		notifyChanged();
		return this;
	}
	public String getSupportPage() {
		return parseString(dbObject.get("supportPage")+"", "");
	}
	
	public SourceForgeProject setSupportPage(String supportPage) {
		dbObject.put("supportPage", supportPage);
		notifyChanged();
		return this;
	}
	public String getSummaryPage() {
		return parseString(dbObject.get("summaryPage")+"", "");
	}
	
	public SourceForgeProject setSummaryPage(String summaryPage) {
		dbObject.put("summaryPage", summaryPage);
		notifyChanged();
		return this;
	}
	public String getHomePage() {
		return parseString(dbObject.get("homePage")+"", "");
	}
	
	public SourceForgeProject setHomePage(String homePage) {
		dbObject.put("homePage", homePage);
		notifyChanged();
		return this;
	}
	public String getBaseUrl() {
		return parseString(dbObject.get("baseUrl")+"", "");
	}
	
	public SourceForgeProject setBaseUrl(String baseUrl) {
		dbObject.put("baseUrl", baseUrl);
		notifyChanged();
		return this;
	}
	
	
	public List<License> getLicense() {
		if (license == null) {
			license = new PongoList<License>(this, "license", true);
		}
		return license;
	}
	public List<OS> getOs() {
		if (os == null) {
			os = new PongoList<OS>(this, "os", true);
		}
		return os;
	}
	public List<Topic> getTopics() {
		if (topics == null) {
			topics = new PongoList<Topic>(this, "topics", true);
		}
		return topics;
	}
	public List<ProgrammingLanguage> getProgramminLanguages() {
		if (programminLanguages == null) {
			programminLanguages = new PongoList<ProgrammingLanguage>(this, "programminLanguages", true);
		}
		return programminLanguages;
	}
	public List<Audience> getAudiences() {
		if (audiences == null) {
			audiences = new PongoList<Audience>(this, "audiences", true);
		}
		return audiences;
	}
	public List<Translation> getTranslations() {
		if (translations == null) {
			translations = new PongoList<Translation>(this, "translations", true);
		}
		return translations;
	}
	public List<Environment> getEnvironments() {
		if (environments == null) {
			environments = new PongoList<Environment>(this, "environments", true);
		}
		return environments;
	}
	public List<Category> getCategories() {
		if (categories == null) {
			categories = new PongoList<Category>(this, "categories", true);
		}
		return categories;
	}
	public List<Tracker> getTrackers() {
		if (trackers == null) {
			trackers = new PongoList<Tracker>(this, "trackers", true);
		}
		return trackers;
	}
	public List<Maintainer> getMaintainers() {
		if (maintainers == null) {
			maintainers = new PongoList<Maintainer>(this, "maintainers", true);
		}
		return maintainers;
	}
	public List<Developer> getDevelopers() {
		if (developers == null) {
			developers = new PongoList<Developer>(this, "developers", true);
		}
		return developers;
	}
	
	
	public Donation getDonation() {
		if (donation == null && dbObject.containsField("donation")) {
			donation = (Donation) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("donation"));
			donation.setContainer(this);
		}
		return donation;
	}
	
	public SourceForgeProject setDonation(Donation donation) {
		if (this.donation != donation) {
			if (donation == null) {
				dbObject.removeField("donation");
			}
			else {
				dbObject.put("donation", donation.getDbObject());
			}
			this.donation = donation;
			notifyChanged();
		}
		return this;
	}
}