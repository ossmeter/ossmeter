package org.ossmeter.platform.mining.msr14.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class User extends Pongo {
	
	protected List<User> followers = null;
	protected List<User> following = null;
	protected List<String> watching = null;
	protected List<String> forks = null;
	protected List<String> projects = null;
	protected List<IssueEvent> issueEvents = null;
	protected List<Artefact> artefacts = null;
	protected Countable issues = null;
	protected Countable issueComments = null;
	protected Countable pullRequests = null;
	protected Countable pullRequestComments = null;
	protected Commit commitAuthors = null;
	protected Commit commitCommitter = null;
	protected Countable commitComments = null;
	
	
	public User() { 
		super();
		dbObject.put("followers", new BasicDBList());
		dbObject.put("following", new BasicDBList());
		dbObject.put("watching", new BasicDBList());
		dbObject.put("forks", new BasicDBList());
		dbObject.put("projects", new BasicDBList());
		dbObject.put("issueEvents", new BasicDBList());
		dbObject.put("artefacts", new BasicDBList());
		LOGIN.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		ID.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		LOCATION.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		PUBLICREPOS.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		JOINEDDATE.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		FOLLOWERCOUNT.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		FOLLOWINGCOUNT.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		WATCHING.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		FORKS.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		PROJECTS.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
	}
	
	public static StringQueryProducer LOGIN = new StringQueryProducer("login"); 
	public static StringQueryProducer ID = new StringQueryProducer("id"); 
	public static StringQueryProducer LOCATION = new StringQueryProducer("location"); 
	public static NumericalQueryProducer PUBLICREPOS = new NumericalQueryProducer("publicRepos");
	public static StringQueryProducer JOINEDDATE = new StringQueryProducer("joinedDate"); 
	public static NumericalQueryProducer FOLLOWERCOUNT = new NumericalQueryProducer("followerCount");
	public static NumericalQueryProducer FOLLOWINGCOUNT = new NumericalQueryProducer("followingCount");
	public static ArrayQueryProducer WATCHING = new ArrayQueryProducer("watching");
	public static ArrayQueryProducer FORKS = new ArrayQueryProducer("forks");
	public static ArrayQueryProducer PROJECTS = new ArrayQueryProducer("projects");
	
	
	public String getLogin() {
		return parseString(dbObject.get("login")+"", "");
	}
	
	public User setLogin(String login) {
		dbObject.put("login", login);
		notifyChanged();
		return this;
	}
	public String getId() {
		return parseString(dbObject.get("id")+"", "");
	}
	
	public User setId(String id) {
		dbObject.put("id", id);
		notifyChanged();
		return this;
	}
	public String getLocation() {
		return parseString(dbObject.get("location")+"", "");
	}
	
	public User setLocation(String location) {
		dbObject.put("location", location);
		notifyChanged();
		return this;
	}
	public int getPublicRepos() {
		return parseInteger(dbObject.get("publicRepos")+"", 0);
	}
	
	public User setPublicRepos(int publicRepos) {
		dbObject.put("publicRepos", publicRepos);
		notifyChanged();
		return this;
	}
	public String getJoinedDate() {
		return parseString(dbObject.get("joinedDate")+"", "");
	}
	
	public User setJoinedDate(String joinedDate) {
		dbObject.put("joinedDate", joinedDate);
		notifyChanged();
		return this;
	}
	public int getFollowerCount() {
		return parseInteger(dbObject.get("followerCount")+"", 0);
	}
	
	public User setFollowerCount(int followerCount) {
		dbObject.put("followerCount", followerCount);
		notifyChanged();
		return this;
	}
	public int getFollowingCount() {
		return parseInteger(dbObject.get("followingCount")+"", 0);
	}
	
	public User setFollowingCount(int followingCount) {
		dbObject.put("followingCount", followingCount);
		notifyChanged();
		return this;
	}
	
	public List<String> getWatching() {
		if (watching == null) {
			watching = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("watching"));
		}
		return watching;
	}
	public List<String> getForks() {
		if (forks == null) {
			forks = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("forks"));
		}
		return forks;
	}
	public List<String> getProjects() {
		if (projects == null) {
			projects = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("projects"));
		}
		return projects;
	}
	
	public List<User> getFollowers() {
		if (followers == null) {
			followers = new PongoList<User>(this, "followers", false);
		}
		return followers;
	}
	public List<User> getFollowing() {
		if (following == null) {
			following = new PongoList<User>(this, "following", false);
		}
		return following;
	}
	public List<IssueEvent> getIssueEvents() {
		if (issueEvents == null) {
			issueEvents = new PongoList<IssueEvent>(this, "issueEvents", true);
		}
		return issueEvents;
	}
	public List<Artefact> getArtefacts() {
		if (artefacts == null) {
			artefacts = new PongoList<Artefact>(this, "artefacts", true);
		}
		return artefacts;
	}
	
	
	public Countable getIssues() {
		if (issues == null && dbObject.containsField("issues")) {
			issues = (Countable) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("issues"));
		}
		return issues;
	}
	
	public User setIssues(Countable issues) {
		if (this.issues != issues) {
			if (issues == null) {
				dbObject.removeField("issues");
			}
			else {
				dbObject.put("issues", issues.getDbObject());
			}
			this.issues = issues;
			notifyChanged();
		}
		return this;
	}
	public Countable getIssueComments() {
		if (issueComments == null && dbObject.containsField("issueComments")) {
			issueComments = (Countable) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("issueComments"));
		}
		return issueComments;
	}
	
	public User setIssueComments(Countable issueComments) {
		if (this.issueComments != issueComments) {
			if (issueComments == null) {
				dbObject.removeField("issueComments");
			}
			else {
				dbObject.put("issueComments", issueComments.getDbObject());
			}
			this.issueComments = issueComments;
			notifyChanged();
		}
		return this;
	}
	public Countable getPullRequests() {
		if (pullRequests == null && dbObject.containsField("pullRequests")) {
			pullRequests = (Countable) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("pullRequests"));
		}
		return pullRequests;
	}
	
	public User setPullRequests(Countable pullRequests) {
		if (this.pullRequests != pullRequests) {
			if (pullRequests == null) {
				dbObject.removeField("pullRequests");
			}
			else {
				dbObject.put("pullRequests", pullRequests.getDbObject());
			}
			this.pullRequests = pullRequests;
			notifyChanged();
		}
		return this;
	}
	public Countable getPullRequestComments() {
		if (pullRequestComments == null && dbObject.containsField("pullRequestComments")) {
			pullRequestComments = (Countable) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("pullRequestComments"));
		}
		return pullRequestComments;
	}
	
	public User setPullRequestComments(Countable pullRequestComments) {
		if (this.pullRequestComments != pullRequestComments) {
			if (pullRequestComments == null) {
				dbObject.removeField("pullRequestComments");
			}
			else {
				dbObject.put("pullRequestComments", pullRequestComments.getDbObject());
			}
			this.pullRequestComments = pullRequestComments;
			notifyChanged();
		}
		return this;
	}
	public Commit getCommitAuthors() {
		if (commitAuthors == null && dbObject.containsField("commitAuthors")) {
			commitAuthors = (Commit) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("commitAuthors"));
		}
		return commitAuthors;
	}
	
	public User setCommitAuthors(Commit commitAuthors) {
		if (this.commitAuthors != commitAuthors) {
			if (commitAuthors == null) {
				dbObject.removeField("commitAuthors");
			}
			else {
				dbObject.put("commitAuthors", commitAuthors.getDbObject());
			}
			this.commitAuthors = commitAuthors;
			notifyChanged();
		}
		return this;
	}
	public Commit getCommitCommitter() {
		if (commitCommitter == null && dbObject.containsField("commitCommitter")) {
			commitCommitter = (Commit) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("commitCommitter"));
		}
		return commitCommitter;
	}
	
	public User setCommitCommitter(Commit commitCommitter) {
		if (this.commitCommitter != commitCommitter) {
			if (commitCommitter == null) {
				dbObject.removeField("commitCommitter");
			}
			else {
				dbObject.put("commitCommitter", commitCommitter.getDbObject());
			}
			this.commitCommitter = commitCommitter;
			notifyChanged();
		}
		return this;
	}
	public Countable getCommitComments() {
		if (commitComments == null && dbObject.containsField("commitComments")) {
			commitComments = (Countable) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("commitComments"));
		}
		return commitComments;
	}
	
	public User setCommitComments(Countable commitComments) {
		if (this.commitComments != commitComments) {
			if (commitComments == null) {
				dbObject.removeField("commitComments");
			}
			else {
				dbObject.put("commitComments", commitComments.getDbObject());
			}
			this.commitComments = commitComments;
			notifyChanged();
		}
		return this;
	}
}