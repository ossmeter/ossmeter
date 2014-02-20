package org.ossmeter.platform.mining.msr14.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class User extends Measureable {
	
	protected List<User> followers = null;
	protected List<User> following = null;
	protected List<Project> watches = null;
	protected List<Project> forks = null;
	protected List<ProjectMembership> projects = null;
	
	
	public User() { 
		super();
		dbObject.put("followers", new BasicDBList());
		dbObject.put("following", new BasicDBList());
		dbObject.put("watches", new BasicDBList());
		dbObject.put("forks", new BasicDBList());
		dbObject.put("projects", new BasicDBList());
		super.setSuperTypes("org.ossmeter.platform.mining.msr14.model.Measureable");
		COMMITCOUNT.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		COMMITTOTALCHANGES.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		COMMITADDITIONS.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		COMMITDELETIONS.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		COMMITSASAUTHOR.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		COMMITSASCOMMITTER.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		COMMITTOTALFILES.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		AVERAGEFILESPERCOMMIT.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		COMMITTIMES.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		COMMITCOMMENTTIMES.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		PULLREQUESTTIMES.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		PULLREQUESTCOMMENTTIMES.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		ISSUETIMES.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		ISSUECOMMENTTIMES.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		NUMBEROFISSUES.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		NUMBEROFCOMMITCOMMENTS.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		NUMBEROFISSUECOMMENTS.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		NUMBEROFPULLREQUESTS.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		NUMBEROFPULLREQUESTCOMMENTS.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		LOGIN.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		GHID.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		LOCATION.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		PUBLICREPOS.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		JOINEDDATE.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		FOLLOWERCOUNT.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		FOLLOWINGCOUNT.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		NUMEROFWATCHES.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		NUMBEROFFORKS.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
		PUBLICGISTS.setOwningType("org.ossmeter.platform.mining.msr14.model.User");
	}
	
	public static NumericalQueryProducer COMMITCOUNT = new NumericalQueryProducer("commitCount");
	public static NumericalQueryProducer COMMITTOTALCHANGES = new NumericalQueryProducer("commitTotalChanges");
	public static NumericalQueryProducer COMMITADDITIONS = new NumericalQueryProducer("commitAdditions");
	public static NumericalQueryProducer COMMITDELETIONS = new NumericalQueryProducer("commitDeletions");
	public static NumericalQueryProducer COMMITSASAUTHOR = new NumericalQueryProducer("commitsAsAuthor");
	public static NumericalQueryProducer COMMITSASCOMMITTER = new NumericalQueryProducer("commitsAsCommitter");
	public static NumericalQueryProducer COMMITTOTALFILES = new NumericalQueryProducer("commitTotalFiles");
	public static NumericalQueryProducer AVERAGEFILESPERCOMMIT = new NumericalQueryProducer("averageFilesPerCommit");
	public static NumericalQueryProducer NUMBEROFISSUES = new NumericalQueryProducer("numberOfIssues");
	public static NumericalQueryProducer NUMBEROFCOMMITCOMMENTS = new NumericalQueryProducer("numberOfCommitComments");
	public static NumericalQueryProducer NUMBEROFISSUECOMMENTS = new NumericalQueryProducer("numberOfIssueComments");
	public static NumericalQueryProducer NUMBEROFPULLREQUESTS = new NumericalQueryProducer("numberOfPullRequests");
	public static NumericalQueryProducer NUMBEROFPULLREQUESTCOMMENTS = new NumericalQueryProducer("numberOfPullRequestComments");
	public static StringQueryProducer LOGIN = new StringQueryProducer("login"); 
	public static StringQueryProducer GHID = new StringQueryProducer("ghId"); 
	public static StringQueryProducer LOCATION = new StringQueryProducer("location"); 
	public static NumericalQueryProducer PUBLICREPOS = new NumericalQueryProducer("publicRepos");
	public static StringQueryProducer JOINEDDATE = new StringQueryProducer("joinedDate"); 
	public static NumericalQueryProducer FOLLOWERCOUNT = new NumericalQueryProducer("followerCount");
	public static NumericalQueryProducer FOLLOWINGCOUNT = new NumericalQueryProducer("followingCount");
	public static NumericalQueryProducer NUMEROFWATCHES = new NumericalQueryProducer("numerOfWatches");
	public static NumericalQueryProducer NUMBEROFFORKS = new NumericalQueryProducer("numberOfForks");
	public static NumericalQueryProducer PUBLICGISTS = new NumericalQueryProducer("publicGists");
	public static ArrayQueryProducer COMMITTIMES = new ArrayQueryProducer("commitTimes");
	public static ArrayQueryProducer COMMITCOMMENTTIMES = new ArrayQueryProducer("commitCommentTimes");
	public static ArrayQueryProducer PULLREQUESTTIMES = new ArrayQueryProducer("pullRequestTimes");
	public static ArrayQueryProducer PULLREQUESTCOMMENTTIMES = new ArrayQueryProducer("pullRequestCommentTimes");
	public static ArrayQueryProducer ISSUETIMES = new ArrayQueryProducer("issueTimes");
	public static ArrayQueryProducer ISSUECOMMENTTIMES = new ArrayQueryProducer("issueCommentTimes");
	
	
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
	public int getPublicGists() {
		return parseInteger(dbObject.get("publicGists")+"", 0);
	}
	
	public User setPublicGists(int publicGists) {
		dbObject.put("publicGists", publicGists);
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
	
	
}