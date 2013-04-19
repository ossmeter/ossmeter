package org.ossmeter.repository.model.github;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class GitHubCommit extends Pongo {
	
	protected List<GitHubComment> comments = null;
	protected List<GitHubCommit> parents = null;
	protected GitHubUser author = null;
	protected GitHubUser committer = null;
	
	
	public GitHubCommit() { 
		super();
		dbObject.put("comments", new BasicDBList());
		dbObject.put("parents", new BasicDBList());
	}
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public GitHubCommit setUrl(String url) {
		dbObject.put("url", url + "");
		notifyChanged();
		return this;
	}
	public String getSha() {
		return parseString(dbObject.get("sha")+"", "");
	}
	
	public GitHubCommit setSha(String sha) {
		dbObject.put("sha", sha + "");
		notifyChanged();
		return this;
	}
	public String getMessage() {
		return parseString(dbObject.get("message")+"", "");
	}
	
	public GitHubCommit setMessage(String message) {
		dbObject.put("message", message + "");
		notifyChanged();
		return this;
	}
	
	
	public List<GitHubComment> getComments() {
		if (comments == null) {
			comments = new PongoList<GitHubComment>(this, "comments", true);
		}
		return comments;
	}
	public List<GitHubCommit> getParents() {
		if (parents == null) {
			parents = new PongoList<GitHubCommit>(this, "parents", true);
		}
		return parents;
	}
	
	
	public GitHubUser getAuthor() {
		if (author == null && dbObject.containsField("author")) {
			author = (GitHubUser) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("author"));
		}
		return author;
	}
	
	public GitHubCommit setAuthor(GitHubUser author) {
		if (this.author != author) {
			if (author == null) {
				dbObject.removeField("author");
			}
			else {
				dbObject.put("author", author.getDbObject());
			}
			this.author = author;
			notifyChanged();
		}
		return this;
	}
	public GitHubUser getCommitter() {
		if (committer == null && dbObject.containsField("committer")) {
			committer = (GitHubUser) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("committer"));
		}
		return committer;
	}
	
	public GitHubCommit setCommitter(GitHubUser committer) {
		if (this.committer != committer) {
			if (committer == null) {
				dbObject.removeField("committer");
			}
			else {
				dbObject.put("committer", committer.getDbObject());
			}
			this.committer = committer;
			notifyChanged();
		}
		return this;
	}
}