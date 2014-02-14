package org.ossmeter.platform.mining.msr14.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Project extends Pongo {
	
	protected List<User> watchers = null;
	protected List<IssueEvent> issueEvents = null;
	protected User owner = null;
	
	
	public Project() { 
		super();
		dbObject.put("owner", new BasicDBObject());
		dbObject.put("watchers", new BasicDBList());
		dbObject.put("issueEvents", new BasicDBList());
		ID.setOwningType("org.ossmeter.platform.mining.msr14.model.Project");
		NAME.setOwningType("org.ossmeter.platform.mining.msr14.model.Project");
		OWNERNAME.setOwningType("org.ossmeter.platform.mining.msr14.model.Project");
		CREATEDAT.setOwningType("org.ossmeter.platform.mining.msr14.model.Project");
		SIZE.setOwningType("org.ossmeter.platform.mining.msr14.model.Project");
		WATCHERSCOUNT.setOwningType("org.ossmeter.platform.mining.msr14.model.Project");
		WATCHERSCOUNT2.setOwningType("org.ossmeter.platform.mining.msr14.model.Project");
		LANGUAGE.setOwningType("org.ossmeter.platform.mining.msr14.model.Project");
		FORKS.setOwningType("org.ossmeter.platform.mining.msr14.model.Project");
		FORKSCOUNT.setOwningType("org.ossmeter.platform.mining.msr14.model.Project");
		OPENISSUESCOUNT.setOwningType("org.ossmeter.platform.mining.msr14.model.Project");
		OPENISSUES.setOwningType("org.ossmeter.platform.mining.msr14.model.Project");
		NETWORKCOUNT.setOwningType("org.ossmeter.platform.mining.msr14.model.Project");
		NUMBEROFCOMMITS.setOwningType("org.ossmeter.platform.mining.msr14.model.Project");
		NUMBEROFISSUES.setOwningType("org.ossmeter.platform.mining.msr14.model.Project");
		NUMBEROFCOMMITCOMMENTS.setOwningType("org.ossmeter.platform.mining.msr14.model.Project");
		NUMBEROFISSUECOMMENTS.setOwningType("org.ossmeter.platform.mining.msr14.model.Project");
		NUMBEROFPULLREQUESTS.setOwningType("org.ossmeter.platform.mining.msr14.model.Project");
		NUMBEROFPULLREQUESTCOMMENTS.setOwningType("org.ossmeter.platform.mining.msr14.model.Project");
	}
	
	public static StringQueryProducer ID = new StringQueryProducer("id"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer OWNERNAME = new StringQueryProducer("ownerName"); 
	public static StringQueryProducer CREATEDAT = new StringQueryProducer("createdAt"); 
	public static NumericalQueryProducer SIZE = new NumericalQueryProducer("size");
	public static NumericalQueryProducer WATCHERSCOUNT = new NumericalQueryProducer("watchersCount");
	public static NumericalQueryProducer WATCHERSCOUNT2 = new NumericalQueryProducer("watchersCount2");
	public static StringQueryProducer LANGUAGE = new StringQueryProducer("language"); 
	public static NumericalQueryProducer FORKS = new NumericalQueryProducer("forks");
	public static NumericalQueryProducer FORKSCOUNT = new NumericalQueryProducer("forksCount");
	public static NumericalQueryProducer OPENISSUESCOUNT = new NumericalQueryProducer("openIssuesCount");
	public static NumericalQueryProducer OPENISSUES = new NumericalQueryProducer("openIssues");
	public static NumericalQueryProducer NETWORKCOUNT = new NumericalQueryProducer("networkCount");
	public static NumericalQueryProducer NUMBEROFCOMMITS = new NumericalQueryProducer("numberOfCommits");
	public static NumericalQueryProducer NUMBEROFISSUES = new NumericalQueryProducer("numberOfIssues");
	public static NumericalQueryProducer NUMBEROFCOMMITCOMMENTS = new NumericalQueryProducer("numberOfCommitComments");
	public static NumericalQueryProducer NUMBEROFISSUECOMMENTS = new NumericalQueryProducer("numberOfIssueComments");
	public static NumericalQueryProducer NUMBEROFPULLREQUESTS = new NumericalQueryProducer("numberOfPullRequests");
	public static NumericalQueryProducer NUMBEROFPULLREQUESTCOMMENTS = new NumericalQueryProducer("numberOfPullRequestComments");
	
	
	public String getId() {
		return parseString(dbObject.get("id")+"", "");
	}
	
	public Project setId(String id) {
		dbObject.put("id", id);
		notifyChanged();
		return this;
	}
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public Project setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public String getOwnerName() {
		return parseString(dbObject.get("ownerName")+"", "");
	}
	
	public Project setOwnerName(String ownerName) {
		dbObject.put("ownerName", ownerName);
		notifyChanged();
		return this;
	}
	public String getCreatedAt() {
		return parseString(dbObject.get("createdAt")+"", "");
	}
	
	public Project setCreatedAt(String createdAt) {
		dbObject.put("createdAt", createdAt);
		notifyChanged();
		return this;
	}
	public int getSize() {
		return parseInteger(dbObject.get("size")+"", 0);
	}
	
	public Project setSize(int size) {
		dbObject.put("size", size);
		notifyChanged();
		return this;
	}
	public int getWatchersCount() {
		return parseInteger(dbObject.get("watchersCount")+"", 0);
	}
	
	public Project setWatchersCount(int watchersCount) {
		dbObject.put("watchersCount", watchersCount);
		notifyChanged();
		return this;
	}
	public int getWatchersCount2() {
		return parseInteger(dbObject.get("watchersCount2")+"", 0);
	}
	
	public Project setWatchersCount2(int watchersCount2) {
		dbObject.put("watchersCount2", watchersCount2);
		notifyChanged();
		return this;
	}
	public String getLanguage() {
		return parseString(dbObject.get("language")+"", "");
	}
	
	public Project setLanguage(String language) {
		dbObject.put("language", language);
		notifyChanged();
		return this;
	}
	public int getForks() {
		return parseInteger(dbObject.get("forks")+"", 0);
	}
	
	public Project setForks(int forks) {
		dbObject.put("forks", forks);
		notifyChanged();
		return this;
	}
	public int getForksCount() {
		return parseInteger(dbObject.get("forksCount")+"", 0);
	}
	
	public Project setForksCount(int forksCount) {
		dbObject.put("forksCount", forksCount);
		notifyChanged();
		return this;
	}
	public int getOpenIssuesCount() {
		return parseInteger(dbObject.get("openIssuesCount")+"", 0);
	}
	
	public Project setOpenIssuesCount(int openIssuesCount) {
		dbObject.put("openIssuesCount", openIssuesCount);
		notifyChanged();
		return this;
	}
	public int getOpenIssues() {
		return parseInteger(dbObject.get("openIssues")+"", 0);
	}
	
	public Project setOpenIssues(int openIssues) {
		dbObject.put("openIssues", openIssues);
		notifyChanged();
		return this;
	}
	public int getNetworkCount() {
		return parseInteger(dbObject.get("networkCount")+"", 0);
	}
	
	public Project setNetworkCount(int networkCount) {
		dbObject.put("networkCount", networkCount);
		notifyChanged();
		return this;
	}
	public int getNumberOfCommits() {
		return parseInteger(dbObject.get("numberOfCommits")+"", 0);
	}
	
	public Project setNumberOfCommits(int numberOfCommits) {
		dbObject.put("numberOfCommits", numberOfCommits);
		notifyChanged();
		return this;
	}
	public int getNumberOfIssues() {
		return parseInteger(dbObject.get("numberOfIssues")+"", 0);
	}
	
	public Project setNumberOfIssues(int numberOfIssues) {
		dbObject.put("numberOfIssues", numberOfIssues);
		notifyChanged();
		return this;
	}
	public int getNumberOfCommitComments() {
		return parseInteger(dbObject.get("numberOfCommitComments")+"", 0);
	}
	
	public Project setNumberOfCommitComments(int numberOfCommitComments) {
		dbObject.put("numberOfCommitComments", numberOfCommitComments);
		notifyChanged();
		return this;
	}
	public int getNumberOfIssueComments() {
		return parseInteger(dbObject.get("numberOfIssueComments")+"", 0);
	}
	
	public Project setNumberOfIssueComments(int numberOfIssueComments) {
		dbObject.put("numberOfIssueComments", numberOfIssueComments);
		notifyChanged();
		return this;
	}
	public int getNumberOfPullRequests() {
		return parseInteger(dbObject.get("numberOfPullRequests")+"", 0);
	}
	
	public Project setNumberOfPullRequests(int numberOfPullRequests) {
		dbObject.put("numberOfPullRequests", numberOfPullRequests);
		notifyChanged();
		return this;
	}
	public int getNumberOfPullRequestComments() {
		return parseInteger(dbObject.get("numberOfPullRequestComments")+"", 0);
	}
	
	public Project setNumberOfPullRequestComments(int numberOfPullRequestComments) {
		dbObject.put("numberOfPullRequestComments", numberOfPullRequestComments);
		notifyChanged();
		return this;
	}
	
	
	public List<User> getWatchers() {
		if (watchers == null) {
			watchers = new PongoList<User>(this, "watchers", false);
		}
		return watchers;
	}
	public List<IssueEvent> getIssueEvents() {
		if (issueEvents == null) {
			issueEvents = new PongoList<IssueEvent>(this, "issueEvents", true);
		}
		return issueEvents;
	}
	
	public Project setOwner(User owner) {
		if (this.owner != owner) {
			if (owner == null) {
				dbObject.put("owner", new BasicDBObject());
			}
			else {
				createReference("owner", owner);
			}
			this.owner = owner;
			notifyChanged();
		}
		return this;
	}
	
	public User getOwner() {
		if (owner == null) {
			owner = (User) resolveReference("owner");
		}
		return owner;
	}
	
}