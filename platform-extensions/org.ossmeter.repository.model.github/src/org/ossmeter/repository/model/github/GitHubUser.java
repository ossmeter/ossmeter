package org.ossmeter.repository.model.github;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class GitHubUser extends org.ossmeter.repository.model.Person {
	
	protected List<GitHubUser> followers = null;
	
	
	public GitHubUser() { 
		super();
		dbObject.put("followers", new BasicDBList());
		super.setSuperTypes("org.ossmeter.repository.model.github.Person");
		LOGIN.setOwningType("org.ossmeter.repository.model.github.GitHubUser");
		HTML_URL.setOwningType("org.ossmeter.repository.model.github.GitHubUser");
	}
	
	public static StringQueryProducer LOGIN = new StringQueryProducer("login"); 
	public static StringQueryProducer HTML_URL = new StringQueryProducer("html_url"); 
	
	
	public String getLogin() {
		return parseString(dbObject.get("login")+"", "");
	}
	
	public GitHubUser setLogin(String login) {
		dbObject.put("login", login);
		notifyChanged();
		return this;
	}
	public String getHtml_url() {
		return parseString(dbObject.get("html_url")+"", "");
	}
	
	public GitHubUser setHtml_url(String html_url) {
		dbObject.put("html_url", html_url);
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