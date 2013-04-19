package org.ossmeter.repository.model.github;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class GitHubComment extends Pongo {
	
	protected GitHubUser user = null;
	
	
	public GitHubComment() { 
		super();
	}
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public GitHubComment setUrl(String url) {
		dbObject.put("url", url + "");
		notifyChanged();
		return this;
	}
	public String getBody() {
		return parseString(dbObject.get("body")+"", "");
	}
	
	public GitHubComment setBody(String body) {
		dbObject.put("body", body + "");
		notifyChanged();
		return this;
	}
	public String getPath() {
		return parseString(dbObject.get("path")+"", "");
	}
	
	public GitHubComment setPath(String path) {
		dbObject.put("path", path + "");
		notifyChanged();
		return this;
	}
	public int getPosition() {
		return parseInteger(dbObject.get("position")+"", 0);
	}
	
	public GitHubComment setPosition(int position) {
		dbObject.put("position", position + "");
		notifyChanged();
		return this;
	}
	public int getLine() {
		return parseInteger(dbObject.get("line")+"", 0);
	}
	
	public GitHubComment setLine(int line) {
		dbObject.put("line", line + "");
		notifyChanged();
		return this;
	}
	public String getCreated_at() {
		return parseString(dbObject.get("created_at")+"", "");
	}
	
	public GitHubComment setCreated_at(String created_at) {
		dbObject.put("created_at", created_at + "");
		notifyChanged();
		return this;
	}
	public String getUpdated_at() {
		return parseString(dbObject.get("updated_at")+"", "");
	}
	
	public GitHubComment setUpdated_at(String updated_at) {
		dbObject.put("updated_at", updated_at + "");
		notifyChanged();
		return this;
	}
	
	
	
	
	public GitHubUser getUser() {
		if (user == null && dbObject.containsField("user")) {
			user = (GitHubUser) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("user"));
		}
		return user;
	}
	
	public GitHubComment setUser(GitHubUser user) {
		if (this.user != user) {
			if (user == null) {
				dbObject.removeField("user");
			}
			else {
				dbObject.put("user", user.getDbObject());
			}
			this.user = user;
			notifyChanged();
		}
		return this;
	}
}