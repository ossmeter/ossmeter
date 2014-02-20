package org.ossmeter.platform.mining.msr14.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ProjectMembership extends Measureable {
	
	protected Project project = null;
	protected User user = null;
	
	
	public ProjectMembership() { 
		super();
		dbObject.put("project", new BasicDBObject());
		dbObject.put("user", new BasicDBObject());
		super.setSuperTypes("org.ossmeter.platform.mining.msr14.model.Measureable");
		COMMITCOUNT.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		COMMITTOTALCHANGES.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		COMMITADDITIONS.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		COMMITDELETIONS.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		COMMITSASAUTHOR.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		COMMITSASCOMMITTER.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		COMMITTOTALFILES.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		AVERAGEFILESPERCOMMIT.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		COMMITTIMES.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		COMMITCOMMENTTIMES.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		PULLREQUESTTIMES.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		PULLREQUESTCOMMENTTIMES.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		ISSUETIMES.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		ISSUECOMMENTTIMES.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		NUMBEROFISSUES.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		NUMBEROFCOMMITCOMMENTS.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		NUMBEROFISSUECOMMENTS.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		NUMBEROFPULLREQUESTS.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		NUMBEROFPULLREQUESTCOMMENTS.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		OWNER.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		ORGMEMBER.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		COLLABORATOR.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		PROJECTNAME.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		PROJECTOWNER.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
		USERNAME.setOwningType("org.ossmeter.platform.mining.msr14.model.ProjectMembership");
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
	public static StringQueryProducer OWNER = new StringQueryProducer("owner"); 
	public static StringQueryProducer ORGMEMBER = new StringQueryProducer("orgMember"); 
	public static StringQueryProducer COLLABORATOR = new StringQueryProducer("collaborator"); 
	public static StringQueryProducer PROJECTNAME = new StringQueryProducer("projectName"); 
	public static StringQueryProducer PROJECTOWNER = new StringQueryProducer("projectOwner"); 
	public static StringQueryProducer USERNAME = new StringQueryProducer("userName"); 
	public static ArrayQueryProducer COMMITTIMES = new ArrayQueryProducer("commitTimes");
	public static ArrayQueryProducer COMMITCOMMENTTIMES = new ArrayQueryProducer("commitCommentTimes");
	public static ArrayQueryProducer PULLREQUESTTIMES = new ArrayQueryProducer("pullRequestTimes");
	public static ArrayQueryProducer PULLREQUESTCOMMENTTIMES = new ArrayQueryProducer("pullRequestCommentTimes");
	public static ArrayQueryProducer ISSUETIMES = new ArrayQueryProducer("issueTimes");
	public static ArrayQueryProducer ISSUECOMMENTTIMES = new ArrayQueryProducer("issueCommentTimes");
	
	
	public boolean getOwner() {
		return parseBoolean(dbObject.get("owner")+"", false);
	}
	
	public ProjectMembership setOwner(boolean owner) {
		dbObject.put("owner", owner);
		notifyChanged();
		return this;
	}
	public boolean getOrgMember() {
		return parseBoolean(dbObject.get("orgMember")+"", false);
	}
	
	public ProjectMembership setOrgMember(boolean orgMember) {
		dbObject.put("orgMember", orgMember);
		notifyChanged();
		return this;
	}
	public boolean getCollaborator() {
		return parseBoolean(dbObject.get("collaborator")+"", false);
	}
	
	public ProjectMembership setCollaborator(boolean collaborator) {
		dbObject.put("collaborator", collaborator);
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
	
}