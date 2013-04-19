package org.ossmeter.repository.model.github;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class GitHubUser extends Pongo {
	
	protected List<GitHubUser> followers = null;
	
	
	public GitHubUser() { 
		super();
		dbObject.put("followers", new BasicDBList());
	}
	
	public String getLogin() {
		return parseString(dbObject.get("login")+"", "");
	}
	
	public GitHubUser setLogin(String login) {
		dbObject.put("login", login + "");
		notifyChanged();
		return this;
	}
	public String getHtml_url() {
		return parseString(dbObject.get("html_url")+"", "");
	}
	
	public GitHubUser setHtml_url(String html_url) {
		dbObject.put("html_url", html_url + "");
		notifyChanged();
		return this;
	}
	
	
	public List<GitHubUser> getFollowers() {
		if (followers == null) {
			followers = new PongoList<GitHubUser>(this, "followers", true);
		}
		return followers;
	}
	
	
}