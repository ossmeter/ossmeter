package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class GITIssue extends Pongo {
	
	protected List<IssueLabel> labels = null;
	protected List<GITComment> comments = null;
	protected GITUser user = null;
	protected GITUser assignee = null;
	protected IssueMilestone milestone = null;
	
	
	public GITIssue() { 
		super();
		dbObject.put("user", new BasicDBObject());
		dbObject.put("assignee", new BasicDBObject());
		dbObject.put("milestone", new BasicDBObject());
		dbObject.put("labels", new BasicDBList());
		dbObject.put("comments", new BasicDBList());
		URL.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITIssue");
		HTML_URL.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITIssue");
		NUMBER.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITIssue");
		STATE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITIssue");
		TITLE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITIssue");
		BODY.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITIssue");
		CREATED_AT.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITIssue");
		UPDATED_AT.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITIssue");
		CLOSED_AT.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITIssue");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer HTML_URL = new StringQueryProducer("html_url"); 
	public static NumericalQueryProducer NUMBER = new NumericalQueryProducer("number");
	public static StringQueryProducer STATE = new StringQueryProducer("state"); 
	public static StringQueryProducer TITLE = new StringQueryProducer("title"); 
	public static StringQueryProducer BODY = new StringQueryProducer("body"); 
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer UPDATED_AT = new StringQueryProducer("updated_at"); 
	public static StringQueryProducer CLOSED_AT = new StringQueryProducer("closed_at"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public GITIssue setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public String getHtml_url() {
		return parseString(dbObject.get("html_url")+"", "");
	}
	
	public GITIssue setHtml_url(String html_url) {
		dbObject.put("html_url", html_url);
		notifyChanged();
		return this;
	}
	public int getNumber() {
		return parseInteger(dbObject.get("number")+"", 0);
	}
	
	public GITIssue setNumber(int number) {
		dbObject.put("number", number);
		notifyChanged();
		return this;
	}
	public String getState() {
		return parseString(dbObject.get("state")+"", "");
	}
	
	public GITIssue setState(String state) {
		dbObject.put("state", state);
		notifyChanged();
		return this;
	}
	public String getTitle() {
		return parseString(dbObject.get("title")+"", "");
	}
	
	public GITIssue setTitle(String title) {
		dbObject.put("title", title);
		notifyChanged();
		return this;
	}
	public String getBody() {
		return parseString(dbObject.get("body")+"", "");
	}
	
	public GITIssue setBody(String body) {
		dbObject.put("body", body);
		notifyChanged();
		return this;
	}
	public String getCreated_at() {
		return parseString(dbObject.get("created_at")+"", "");
	}
	
	public GITIssue setCreated_at(String created_at) {
		dbObject.put("created_at", created_at);
		notifyChanged();
		return this;
	}
	public String getUpdated_at() {
		return parseString(dbObject.get("updated_at")+"", "");
	}
	
	public GITIssue setUpdated_at(String updated_at) {
		dbObject.put("updated_at", updated_at);
		notifyChanged();
		return this;
	}
	public String getClosed_at() {
		return parseString(dbObject.get("closed_at")+"", "");
	}
	
	public GITIssue setClosed_at(String closed_at) {
		dbObject.put("closed_at", closed_at);
		notifyChanged();
		return this;
	}
	
	
	public List<IssueLabel> getLabels() {
		if (labels == null) {
			labels = new PongoList<IssueLabel>(this, "labels", true);
		}
		return labels;
	}
	public List<GITComment> getComments() {
		if (comments == null) {
			comments = new PongoList<GITComment>(this, "comments", true);
		}
		return comments;
	}
	
	
	public GITUser getUser() {
		if (user == null && dbObject.containsField("user")) {
			user = (GITUser) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("user"));
			user.setContainer(this);
		}
		return user;
	}
	
	public GITIssue setUser(GITUser user) {
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
	public GITUser getAssignee() {
		if (assignee == null && dbObject.containsField("assignee")) {
			assignee = (GITUser) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("assignee"));
			assignee.setContainer(this);
		}
		return assignee;
	}
	
	public GITIssue setAssignee(GITUser assignee) {
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
	public IssueMilestone getMilestone() {
		if (milestone == null && dbObject.containsField("milestone")) {
			milestone = (IssueMilestone) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("milestone"));
			milestone.setContainer(this);
		}
		return milestone;
	}
	
	public GITIssue setMilestone(IssueMilestone milestone) {
		if (this.milestone != milestone) {
			if (milestone == null) {
				dbObject.removeField("milestone");
			}
			else {
				dbObject.put("milestone", milestone.getDbObject());
			}
			this.milestone = milestone;
			notifyChanged();
		}
		return this;
	}
}