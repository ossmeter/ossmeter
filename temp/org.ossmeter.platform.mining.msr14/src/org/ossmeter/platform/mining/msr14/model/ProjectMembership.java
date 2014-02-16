package org.ossmeter.platform.mining.msr14.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ProjectMembership extends Pongo {
	
	protected List<IssueEvent> issueEvents = null;
	protected List<Artefact> artefacts = null;
	protected Project project = null;
	protected User user = null;
	protected Commits commits = null;
	
	
	public ProjectMembership() { 
		super();
		dbObject.put("project", new BasicDBObject());
		dbObject.put("user", new BasicDBObject());
		dbObject.put("issueEvents", new BasicDBList());
		dbObject.put("artefacts", new BasicDBList());
		OWNER.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		PROJECTNAME.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		PROJECTOWNER.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		USERNAME.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		NUMBEROFCOMMITS.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		NUMBEROFISSUES.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		NUMBEROFCOMMITCOMMENTS.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		NUMBEROFISSUECOMMENTS.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		NUMBEROFPULLREQUESTS.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		NUMBEROFPULLREQUESTCOMMENTS.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
	}
	
	public static StringQueryProducer OWNER = new StringQueryProducer("owner"); 
	public static StringQueryProducer PROJECTNAME = new StringQueryProducer("projectName"); 
	public static StringQueryProducer PROJECTOWNER = new StringQueryProducer("projectOwner"); 
	public static StringQueryProducer USERNAME = new StringQueryProducer("userName"); 
	public static NumericalQueryProducer NUMBEROFCOMMITS = new NumericalQueryProducer("numberOfCommits");
	public static NumericalQueryProducer NUMBEROFISSUES = new NumericalQueryProducer("numberOfIssues");
	public static NumericalQueryProducer NUMBEROFCOMMITCOMMENTS = new NumericalQueryProducer("numberOfCommitComments");
	public static NumericalQueryProducer NUMBEROFISSUECOMMENTS = new NumericalQueryProducer("numberOfIssueComments");
	public static NumericalQueryProducer NUMBEROFPULLREQUESTS = new NumericalQueryProducer("numberOfPullRequests");
	public static NumericalQueryProducer NUMBEROFPULLREQUESTCOMMENTS = new NumericalQueryProducer("numberOfPullRequestComments");
	
	
	public boolean getOwner() {
		return parseBoolean(dbObject.get("owner")+"", false);
	}
	
	public ProjectMembership setOwner(boolean owner) {
		dbObject.put("owner", owner);
		notifyChanged();
		return this;
	}
	public String getProjectName() {
		return parseString(dbObject.get("projectName")+"", "");
	}
	
	public ProjectMembership setProjectName(String projectName) {
		dbObject.put("projectName", projectName);
		notifyChanged();
		return this;
	}
	public String getProjectOwner() {
		return parseString(dbObject.get("projectOwner")+"", "");
	}
	
	public ProjectMembership setProjectOwner(String projectOwner) {
		dbObject.put("projectOwner", projectOwner);
		notifyChanged();
		return this;
	}
	public String getUserName() {
		return parseString(dbObject.get("userName")+"", "");
	}
	
	public ProjectMembership setUserName(String userName) {
		dbObject.put("userName", userName);
		notifyChanged();
		return this;
	}
	public int getNumberOfCommits() {
		return parseInteger(dbObject.get("numberOfCommits")+"", 0);
	}
	
	public ProjectMembership setNumberOfCommits(int numberOfCommits) {
		dbObject.put("numberOfCommits", numberOfCommits);
		notifyChanged();
		return this;
	}
	public int getNumberOfIssues() {
		return parseInteger(dbObject.get("numberOfIssues")+"", 0);
	}
	
	public ProjectMembership setNumberOfIssues(int numberOfIssues) {
		dbObject.put("numberOfIssues", numberOfIssues);
		notifyChanged();
		return this;
	}
	public int getNumberOfCommitComments() {
		return parseInteger(dbObject.get("numberOfCommitComments")+"", 0);
	}
	
	public ProjectMembership setNumberOfCommitComments(int numberOfCommitComments) {
		dbObject.put("numberOfCommitComments", numberOfCommitComments);
		notifyChanged();
		return this;
	}
	public int getNumberOfIssueComments() {
		return parseInteger(dbObject.get("numberOfIssueComments")+"", 0);
	}
	
	public ProjectMembership setNumberOfIssueComments(int numberOfIssueComments) {
		dbObject.put("numberOfIssueComments", numberOfIssueComments);
		notifyChanged();
		return this;
	}
	public int getNumberOfPullRequests() {
		return parseInteger(dbObject.get("numberOfPullRequests")+"", 0);
	}
	
	public ProjectMembership setNumberOfPullRequests(int numberOfPullRequests) {
		dbObject.put("numberOfPullRequests", numberOfPullRequests);
		notifyChanged();
		return this;
	}
	public int getNumberOfPullRequestComments() {
		return parseInteger(dbObject.get("numberOfPullRequestComments")+"", 0);
	}
	
	public ProjectMembership setNumberOfPullRequestComments(int numberOfPullRequestComments) {
		dbObject.put("numberOfPullRequestComments", numberOfPullRequestComments);
		notifyChanged();
		return this;
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
	
	public ProjectMembership setProject(Project project) {
		if (this.project != project) {
			if (project == null) {
				dbObject.put("project", new BasicDBObject());
			}
			else {
				createReference("project", project);
			}
			this.project = project;
			notifyChanged();
		}
		return this;
	}
	
	public Project getProject() {
		if (project == null) {
			project = (Project) resolveReference("project");
		}
		return project;
	}
	public ProjectMembership setUser(User user) {
		if (this.user != user) {
			if (user == null) {
				dbObject.put("user", new BasicDBObject());
			}
			else {
				createReference("user", user);
			}
			this.user = user;
			notifyChanged();
		}
		return this;
	}
	
	public User getUser() {
		if (user == null) {
			user = (User) resolveReference("user");
		}
		return user;
	}
	
	public Commits getCommits() {
		if (commits == null && dbObject.containsField("commits")) {
			commits = (Commits) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("commits"));
		}
		return commits;
	}
	
	public ProjectMembership setCommits(Commits commits) {
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