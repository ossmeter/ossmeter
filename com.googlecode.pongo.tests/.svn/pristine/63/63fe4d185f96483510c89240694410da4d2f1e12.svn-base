package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class IssueMilestone extends Pongo {
	
	protected List<GITIssue> open_issues = null;
	protected List<GITIssue> closed_issues = null;
	protected GITUser creator = null;
	
	
	public IssueMilestone() { 
		super();
		dbObject.put("creator", new BasicDBObject());
		dbObject.put("open_issues", new BasicDBList());
		dbObject.put("closed_issues", new BasicDBList());
		URL.setOwningType("com.googlecode.pongo.tests.ossmeter.model.IssueMilestone");
		NUMBER.setOwningType("com.googlecode.pongo.tests.ossmeter.model.IssueMilestone");
		STATE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.IssueMilestone");
		TITLE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.IssueMilestone");
		DESCRIPTION.setOwningType("com.googlecode.pongo.tests.ossmeter.model.IssueMilestone");
		CREATED_AT.setOwningType("com.googlecode.pongo.tests.ossmeter.model.IssueMilestone");
		DUE_ON.setOwningType("com.googlecode.pongo.tests.ossmeter.model.IssueMilestone");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static NumericalQueryProducer NUMBER = new NumericalQueryProducer("number");
	public static StringQueryProducer STATE = new StringQueryProducer("state"); 
	public static StringQueryProducer TITLE = new StringQueryProducer("title"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer DUE_ON = new StringQueryProducer("due_on"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public IssueMilestone setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public int getNumber() {
		return parseInteger(dbObject.get("number")+"", 0);
	}
	
	public IssueMilestone setNumber(int number) {
		dbObject.put("number", number);
		notifyChanged();
		return this;
	}
	public String getState() {
		return parseString(dbObject.get("state")+"", "");
	}
	
	public IssueMilestone setState(String state) {
		dbObject.put("state", state);
		notifyChanged();
		return this;
	}
	public String getTitle() {
		return parseString(dbObject.get("title")+"", "");
	}
	
	public IssueMilestone setTitle(String title) {
		dbObject.put("title", title);
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public IssueMilestone setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public String getCreated_at() {
		return parseString(dbObject.get("created_at")+"", "");
	}
	
	public IssueMilestone setCreated_at(String created_at) {
		dbObject.put("created_at", created_at);
		notifyChanged();
		return this;
	}
	public String getDue_on() {
		return parseString(dbObject.get("due_on")+"", "");
	}
	
	public IssueMilestone setDue_on(String due_on) {
		dbObject.put("due_on", due_on);
		notifyChanged();
		return this;
	}
	
	
	public List<GITIssue> getOpen_issues() {
		if (open_issues == null) {
			open_issues = new PongoList<GITIssue>(this, "open_issues", true);
		}
		return open_issues;
	}
	public List<GITIssue> getClosed_issues() {
		if (closed_issues == null) {
			closed_issues = new PongoList<GITIssue>(this, "closed_issues", true);
		}
		return closed_issues;
	}
	
	
	public GITUser getCreator() {
		if (creator == null && dbObject.containsField("creator")) {
			creator = (GITUser) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("creator"));
			creator.setContainer(this);
		}
		return creator;
	}
	
	public IssueMilestone setCreator(GITUser creator) {
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