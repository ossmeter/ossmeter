package org.ossmeter.repository.model.redmine;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class RedmineProject extends org.ossmeter.repository.model.Project {
	
	protected List<RedmineBugIssueTracker> issueTracker = null;
	protected List<RedmineUser> members = null;
	protected List<RedmineProjectVersion> versions = null;
	protected RedmineWiki wiki = null;
	protected RedmineQueryManager queryManager = null;
	
	
	public RedmineProject() { 
		super();
		dbObject.put("wiki", new BasicDBObject());
		dbObject.put("queryManager", new BasicDBObject());
		dbObject.put("issueTracker", new BasicDBList());
		dbObject.put("members", new BasicDBList());
		dbObject.put("versions", new BasicDBList());
		super.setSuperTypes("org.ossmeter.repository.model.redmine.Project");
		IDENTIFIER.setOwningType("org.ossmeter.repository.model.redmine.RedmineProject");
		DESCRIPTION.setOwningType("org.ossmeter.repository.model.redmine.RedmineProject");
		CREATED_ON.setOwningType("org.ossmeter.repository.model.redmine.RedmineProject");
		UPDATED_ON.setOwningType("org.ossmeter.repository.model.redmine.RedmineProject");
	}
	
	public static StringQueryProducer IDENTIFIER = new StringQueryProducer("identifier"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static StringQueryProducer CREATED_ON = new StringQueryProducer("created_on"); 
	public static StringQueryProducer UPDATED_ON = new StringQueryProducer("updated_on"); 
	
	
	public String getIdentifier() {
		return parseString(dbObject.get("identifier")+"", "");
	}
	
	public RedmineProject setIdentifier(String identifier) {
		dbObject.put("identifier", identifier);
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public RedmineProject setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public String getCreated_on() {
		return parseString(dbObject.get("created_on")+"", "");
	}
	
	public RedmineProject setCreated_on(String created_on) {
		dbObject.put("created_on", created_on);
		notifyChanged();
		return this;
	}
	public String getUpdated_on() {
		return parseString(dbObject.get("updated_on")+"", "");
	}
	
	public RedmineProject setUpdated_on(String updated_on) {
		dbObject.put("updated_on", updated_on);
		notifyChanged();
		return this;
	}
	
	
	public List<RedmineBugIssueTracker> getIssueTracker() {
		if (issueTracker == null) {
			issueTracker = new PongoList<RedmineBugIssueTracker>(this, "issueTracker", true);
		}
		return issueTracker;
	}
	public List<RedmineUser> getMembers() {
		if (members == null) {
			members = new PongoList<RedmineUser>(this, "members", true);
		}
		return members;
	}
	public List<RedmineProjectVersion> getVersions() {
		if (versions == null) {
			versions = new PongoList<RedmineProjectVersion>(this, "versions", true);
		}
		return versions;
	}
	
	
	public RedmineWiki getWiki() {
		if (wiki == null && dbObject.containsField("wiki")) {
			wiki = (RedmineWiki) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("wiki"));
			wiki.setContainer(this);
		}
		return wiki;
	}
	
	public RedmineProject setWiki(RedmineWiki wiki) {
		if (this.wiki != wiki) {
			if (wiki == null) {
				dbObject.removeField("wiki");
			}
			else {
				dbObject.put("wiki", wiki.getDbObject());
			}
			this.wiki = wiki;
			notifyChanged();
		}
		return this;
	}
	public RedmineQueryManager getQueryManager() {
		if (queryManager == null && dbObject.containsField("queryManager")) {
			queryManager = (RedmineQueryManager) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("queryManager"));
			queryManager.setContainer(this);
		}
		return queryManager;
	}
	
	public RedmineProject setQueryManager(RedmineQueryManager queryManager) {
		if (this.queryManager != queryManager) {
			if (queryManager == null) {
				dbObject.removeField("queryManager");
			}
			else {
				dbObject.put("queryManager", queryManager.getDbObject());
			}
			this.queryManager = queryManager;
			notifyChanged();
		}
		return this;
	}
}