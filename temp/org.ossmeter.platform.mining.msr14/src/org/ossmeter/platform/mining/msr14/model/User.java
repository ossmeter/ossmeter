package org.ossmeter.platform.mining.msr14.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class User extends Pongo {
	
	protected List<User> followers = null;
	protected List<User> following = null;
	protected List<Project> watches = null;
	protected List<Project> forks = null;
	protected List<ProjectMembership> projects = null;
	protected List<IssueEvent> totalIssueEvents = null;
	protected List<Artefact> artefacts = null;
	protected Commits commits = null;
	
	
	public User() { 
		super();
		dbObject.put("followers", new BasicDBList());
		dbObject.put("following", new BasicDBList());
		dbObject.put("watches", new BasicDBList());
		dbObject.put("forks", new BasicDBList());
		dbObject.put("projects", new BasicDBList());
		dbObject.put("totalIssueEvents", new BasicDBList());
		dbObject.put("artefacts", new BasicDBList());
		LOGIN.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		GHID.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		LOCATION.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		PUBLICREPOS.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		JOINEDDATE.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		FOLLOWERCOUNT.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		FOLLOWINGCOUNT.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		NUMEROFWATCHES.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		NUMBEROFFORKS.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		TOTALNUMBEROFISSUES.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		TOTALNUMBEROFCOMMITCOMMENTS.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		TOTALNUMBEROFISSUECOMMENTS.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		TOTALNUMBEROFPULLREQUESTS.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		TOTALNUMBEROFPULLREQUESTCOMMENTS.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
	}
	
	public static StringQueryProducer LOGIN = new StringQueryProducer("login"); 
	public static StringQueryProducer GHID = new StringQueryProducer("ghId"); 
	public static StringQueryProducer LOCATION = new StringQueryProducer("location"); 
	public static NumericalQueryProducer PUBLICREPOS = new NumericalQueryProducer("publicRepos");
	public static StringQueryProducer JOINEDDATE = new StringQueryProducer("joinedDate"); 
	public static NumericalQueryProducer FOLLOWERCOUNT = new NumericalQueryProducer("followerCount");
	public static NumericalQueryProducer FOLLOWINGCOUNT = new NumericalQueryProducer("followingCount");
	public static NumericalQueryProducer NUMEROFWATCHES = new NumericalQueryProducer("numerOfWatches");
	public static NumericalQueryProducer NUMBEROFFORKS = new NumericalQueryProducer("numberOfForks");
	public static NumericalQueryProducer TOTALNUMBEROFISSUES = new NumericalQueryProducer("totalNumberOfIssues");
	public static NumericalQueryProducer TOTALNUMBEROFCOMMITCOMMENTS = new NumericalQueryProducer("totalNumberOfCommitComments");
	public static NumericalQueryProducer TOTALNUMBEROFISSUECOMMENTS = new NumericalQueryProducer("totalNumberOfIssueComments");
	public static NumericalQueryProducer TOTALNUMBEROFPULLREQUESTS = new NumericalQueryProducer("totalNumberOfPullRequests");
	public static NumericalQueryProducer TOTALNUMBEROFPULLREQUESTCOMMENTS = new NumericalQueryProducer("totalNumberOfPullRequestComments");
	
	
	public String getLogin() {
		return parseString(dbObject.get("login")+"", "");
	}
	
	public User setLogin(String login) {
		dbObject.put("login", login);
		notifyChanged();
		return this;
	}
	public String getGhId() {
		return parseString(dbObject.get("ghId")+"", "");
	}
	
	public User setGhId(String ghId) {
		dbObject.put("ghId", ghId);
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
	public int getNumerOfWatches() {
		return parseInteger(dbObject.get("numerOfWatches")+"", 0);
	}
	
	public User setNumerOfWatches(int numerOfWatches) {
		dbObject.put("numerOfWatches", numerOfWatches);
		notifyChanged();
		return this;
	}
	public int getNumberOfForks() {
		return parseInteger(dbObject.get("numberOfForks")+"", 0);
	}
	
	public User setNumberOfForks(int numberOfForks) {
		dbObject.put("numberOfForks", numberOfForks);
		notifyChanged();
		return this;
	}
	public int getTotalNumberOfIssues() {
		return parseInteger(dbObject.get("totalNumberOfIssues")+"", 0);
	}
	
	public User setTotalNumberOfIssues(int totalNumberOfIssues) {
		dbObject.put("totalNumberOfIssues", totalNumberOfIssues);
		notifyChanged();
		return this;
	}
	public int getTotalNumberOfCommitComments() {
		return parseInteger(dbObject.get("totalNumberOfCommitComments")+"", 0);
	}
	
	public User setTotalNumberOfCommitComments(int totalNumberOfCommitComments) {
		dbObject.put("totalNumberOfCommitComments", totalNumberOfCommitComments);
		notifyChanged();
		return this;
	}
	public int getTotalNumberOfIssueComments() {
		return parseInteger(dbObject.get("totalNumberOfIssueComments")+"", 0);
	}
	
	public User setTotalNumberOfIssueComments(int totalNumberOfIssueComments) {
		dbObject.put("totalNumberOfIssueComments", totalNumberOfIssueComments);
		notifyChanged();
		return this;
	}
	public int getTotalNumberOfPullRequests() {
		return parseInteger(dbObject.get("totalNumberOfPullRequests")+"", 0);
	}
	
	public User setTotalNumberOfPullRequests(int totalNumberOfPullRequests) {
		dbObject.put("totalNumberOfPullRequests", totalNumberOfPullRequests);
		notifyChanged();
		return this;
	}
	public int getTotalNumberOfPullRequestComments() {
		return parseInteger(dbObject.get("totalNumberOfPullRequestComments")+"", 0);
	}
	
	public User setTotalNumberOfPullRequestComments(int totalNumberOfPullRequestComments) {
		dbObject.put("totalNumberOfPullRequestComments", totalNumberOfPullRequestComments);
		notifyChanged();
		return this;
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
	public List<Project> getWatches() {
		if (watches == null) {
			watches = new PongoList<Project>(this, "watches", false);
		}
		return watches;
	}
	public List<Project> getForks() {
		if (forks == null) {
			forks = new PongoList<Project>(this, "forks", false);
		}
		return forks;
	}
	public List<ProjectMembership> getProjects() {
		if (projects == null) {
			projects = new PongoList<ProjectMembership>(this, "projects", false);
		}
		return projects;
	}
	public List<IssueEvent> getTotalIssueEvents() {
		if (totalIssueEvents == null) {
			totalIssueEvents = new PongoList<IssueEvent>(this, "totalIssueEvents", true);
		}
		return totalIssueEvents;
	}
	public List<Artefact> getArtefacts() {
		if (artefacts == null) {
			artefacts = new PongoList<Artefact>(this, "artefacts", true);
		}
		return artefacts;
	}
	
	
	public Commits getCommits() {
		if (commits == null && dbObject.containsField("commits")) {
			commits = (Commits) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("commits"));
		}
		return commits;
	}
	
	public User setCommits(Commits commits) {
		if (this.commits != commits) {
			if (commits == null) {
				dbObject.removeField("commits");
			}
			else {
				dbObject.put("commits", commits.getDbObject());
			}
			this.commits = commits;
			notifyChanged();
		}
		return this;
	}
}