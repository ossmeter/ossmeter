package org.ossmeter.repository.model.github;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class GitHubIssue extends Pongo {
	
	protected List<GitHubUser> subscribed_users = null;
	protected List<GitHubUser> mentioned_users = null;
	protected List<GitHubComment> comments = null;
	protected GitHubUser creator = null;
	protected GitHubUser assignee = null;
	
	
	public GitHubIssue() { 
		super();
		dbObject.put("subscribed_users", new BasicDBList());
		dbObject.put("mentioned_users", new BasicDBList());
		dbObject.put("comments", new BasicDBList());
	}
	
	public int getNumber() {
		return parseInteger(dbObject.get("number")+"", 0);
	}
	
	public GitHubIssue setNumber(int number) {
		dbObject.put("number", number + "");
		notifyChanged();
		return this;
	}
	public GitHubIssueState getState() {
		GitHubIssueState state = null;
		try {
			state = GitHubIssueState.valueOf(dbObject.get("state")+"");
		}
		catch (Exception ex) {}
		return state;
	}
	
	public GitHubIssue setState(GitHubIssueState state) {
		dbObject.put("state", state + "");
		notifyChanged();
		return this;
	}
	public String getTitle() {
		return parseString(dbObject.get("title")+"", "");
	}
	
	public GitHubIssue setTitle(String title) {
		dbObject.put("title", title + "");
		notifyChanged();
		return this;
	}
	public String getBody() {
		return parseString(dbObject.get("body")+"", "");
	}
	
	public GitHubIssue setBody(String body) {
		dbObject.put("body", body + "");
		notifyChanged();
		return this;
	}
	public String getCreated_at() {
		return parseString(dbObject.get("created_at")+"", "");
	}
	
	public GitHubIssue setCreated_at(String created_at) {
		dbObject.put("created_at", created_at + "");
		notifyChanged();
		return this;
	}
	public String getUpdated_at() {
		return parseString(dbObject.get("updated_at")+"", "");
	}
	
	public GitHubIssue setUpdated_at(String updated_at) {
		dbObject.put("updated_at", updated_at + "");
		notifyChanged();
		return this;
	}
	public String getClosed_at() {
		return parseString(dbObject.get("closed_at")+"", "");
	}
	
	public GitHubIssue setClosed_at(String closed_at) {
		dbObject.put("closed_at", closed_at + "");
		notifyChanged();
		return this;
	}
	
	
	public List<GitHubUser> getSubscribed_users() {
		if (subscribed_users == null) {
			subscribed_users = new PongoList<GitHubUser>(this, "subscribed_users", true);
		}
		return subscribed_users;
	}
	public List<GitHubUser> getMentioned_users() {
		if (mentioned_users == null) {
			mentioned_users = new PongoList<GitHubUser>(this, "mentioned_users", true);
		}
		return mentioned_users;
	}
	public List<GitHubComment> getComments() {
		if (comments == null) {
			comments = new PongoList<GitHubComment>(this, "comments", true);
		}
		return comments;
	}
	
	
	public GitHubUser getCreator() {
		if (creator == null && dbObject.containsField("creator")) {
			creator = (GitHubUser) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("creator"));
		}
		return creator;
	}
	
	public GitHubIssue setCreator(GitHubUser creator) {
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
	public GitHubUser getAssignee() {
		if (assignee == null && dbObject.containsField("assignee")) {
			assignee = (GitHubUser) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("assignee"));
		}
		return assignee;
	}
	
	public GitHubIssue setAssignee(GitHubUser assignee) {
		if (this.assignee != assignee) {
			if (assignee == null) {
				dbObject.removeField("assignee");
			}
			else {
				dbObject.put("assignee", assignee.getDbObject());
			}
			this.assignee = assignee;
			notifyChanged();
		}
		return this;
	}
}