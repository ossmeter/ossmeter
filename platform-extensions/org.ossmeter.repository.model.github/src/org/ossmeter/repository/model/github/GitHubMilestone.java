package org.ossmeter.repository.model.github;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class GitHubMilestone extends Pongo {
	
	protected List<GitHubIssue> open_issues = null;
	protected List<GitHubIssue> closed_issues = null;
	protected GitHubUser creator = null;
	
	
	public GitHubMilestone() { 
		super();
		dbObject.put("open_issues", new BasicDBList());
		dbObject.put("closed_issues", new BasicDBList());
	}
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public GitHubMilestone setUrl(String url) {
		dbObject.put("url", url + "");
		notifyChanged();
		return this;
	}
	public int getNumber() {
		return parseInteger(dbObject.get("number")+"", 0);
	}
	
	public GitHubMilestone setNumber(int number) {
		dbObject.put("number", number + "");
		notifyChanged();
		return this;
	}
	public GitHubMilestoneState getState() {
		GitHubMilestoneState state = null;
		try {
			state = GitHubMilestoneState.valueOf(dbObject.get("state")+"");
		}
		catch (Exception ex) {}
		return state;
	}
	
	public GitHubMilestone setState(GitHubMilestoneState state) {
		dbObject.put("state", state + "");
		notifyChanged();
		return this;
	}
	public String getTitle() {
		return parseString(dbObject.get("title")+"", "");
	}
	
	public GitHubMilestone setTitle(String title) {
		dbObject.put("title", title + "");
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public GitHubMilestone setDescription(String description) {
		dbObject.put("description", description + "");
		notifyChanged();
		return this;
	}
	public String getCreated_at() {
		return parseString(dbObject.get("created_at")+"", "");
	}
	
	public GitHubMilestone setCreated_at(String created_at) {
		dbObject.put("created_at", created_at + "");
		notifyChanged();
		return this;
	}
	public String getDue_on() {
		return parseString(dbObject.get("due_on")+"", "");
	}
	
	public GitHubMilestone setDue_on(String due_on) {
		dbObject.put("due_on", due_on + "");
		notifyChanged();
		return this;
	}
	
	
	public List<GitHubIssue> getOpen_issues() {
		if (open_issues == null) {
			open_issues = new PongoList<GitHubIssue>(this, "open_issues", true);
		}
		return open_issues;
	}
	public List<GitHubIssue> getClosed_issues() {
		if (closed_issues == null) {
			closed_issues = new PongoList<GitHubIssue>(this, "closed_issues", true);
		}
		return closed_issues;
	}
	
	
	public GitHubUser getCreator() {
		if (creator == null && dbObject.containsField("creator")) {
			creator = (GitHubUser) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("creator"));
		}
		return creator;
	}
	
	public GitHubMilestone setCreator(GitHubUser creator) {
		if (this.creator != creator) {
			if (creator == null) {
				dbObject.removeField("creator");
			}
			else {
				dbObject.put("creator", creator.getDbObject());
			}
			this.creator = creator;
			notifyChanged();
		}
		return this;
	}
}