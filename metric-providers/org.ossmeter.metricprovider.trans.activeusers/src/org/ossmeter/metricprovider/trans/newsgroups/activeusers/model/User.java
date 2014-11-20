package org.ossmeter.metricprovider.trans.newsgroups.activeusers.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class User extends Pongo {
	
	
	
	public User() { 
		super();
		URL_NAME.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.activeusers.model.User");
		USERID.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.activeusers.model.User");
		LASTACTIVITYDATE.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.activeusers.model.User");
		ARTICLES.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.activeusers.model.User");
		REQUESTS.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.activeusers.model.User");
		REPLIES.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.activeusers.model.User");
	}
	
	public static StringQueryProducer URL_NAME = new StringQueryProducer("url_name"); 
	public static StringQueryProducer USERID = new StringQueryProducer("userId"); 
	public static StringQueryProducer LASTACTIVITYDATE = new StringQueryProducer("lastActivityDate"); 
	public static NumericalQueryProducer ARTICLES = new NumericalQueryProducer("articles");
	public static NumericalQueryProducer REQUESTS = new NumericalQueryProducer("requests");
	public static NumericalQueryProducer REPLIES = new NumericalQueryProducer("replies");
	
	
	public String getUrl_name() {
		return parseString(dbObject.get("url_name")+"", "");
	}
	
	public User setUrl_name(String url_name) {
		dbObject.put("url_name", url_name);
		notifyChanged();
		return this;
	}
	public String getUserId() {
		return parseString(dbObject.get("userId")+"", "");
	}
	
	public User setUserId(String userId) {
		dbObject.put("userId", userId);
		notifyChanged();
		return this;
	}
	public String getLastActivityDate() {
		return parseString(dbObject.get("lastActivityDate")+"", "");
	}
	
	public User setLastActivityDate(String lastActivityDate) {
		dbObject.put("lastActivityDate", lastActivityDate);
		notifyChanged();
		return this;
	}
	public int getArticles() {
		return parseInteger(dbObject.get("articles")+"", 0);
	}
	
	public User setArticles(int articles) {
		dbObject.put("articles", articles);
		notifyChanged();
		return this;
	}
	public int getRequests() {
		return parseInteger(dbObject.get("requests")+"", 0);
	}
	
	public User setRequests(int requests) {
		dbObject.put("requests", requests);
		notifyChanged();
		return this;
	}
	public int getReplies() {
		return parseInteger(dbObject.get("replies")+"", 0);
	}
	
	public User setReplies(int replies) {
		dbObject.put("replies", replies);
		notifyChanged();
		return this;
	}
	
	
	
	
}