package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class GoogleIssue extends Pongo {
	
	protected List<GoogleIssueComment> comments = null;
	protected List<GoogleLabel> labels = null;
	protected GoogleUser owner = null;
	
	
	public GoogleIssue() { 
		super();
		dbObject.put("owner", new BasicDBObject());
		dbObject.put("comments", new BasicDBList());
		dbObject.put("labels", new BasicDBList());
		CREATED_AT.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GoogleIssue");
		UPDATED_AT.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GoogleIssue");
		PRIORITY.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GoogleIssue");
		TYPE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GoogleIssue");
		COMPONENT.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GoogleIssue");
		STATUS.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GoogleIssue");
		STARS.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GoogleIssue");
		SUMMARY.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GoogleIssue");
	}
	
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer UPDATED_AT = new StringQueryProducer("updated_at"); 
	public static StringQueryProducer PRIORITY = new StringQueryProducer("priority"); 
	public static StringQueryProducer TYPE = new StringQueryProducer("type"); 
	public static StringQueryProducer COMPONENT = new StringQueryProducer("component"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static NumericalQueryProducer STARS = new NumericalQueryProducer("stars");
	public static StringQueryProducer SUMMARY = new StringQueryProducer("summary"); 
	
	
	public String getCreated_at() {
		return parseString(dbObject.get("created_at")+"", "");
	}
	
	public GoogleIssue setCreated_at(String created_at) {
		dbObject.put("created_at", created_at);
		notifyChanged();
		return this;
	}
	public String getUpdated_at() {
		return parseString(dbObject.get("updated_at")+"", "");
	}
	
	public GoogleIssue setUpdated_at(String updated_at) {
		dbObject.put("updated_at", updated_at);
		notifyChanged();
		return this;
	}
	public GoogleIssuePriority getPriority() {
		GoogleIssuePriority priority = null;
		try {
			priority = GoogleIssuePriority.valueOf(dbObject.get("priority")+"");
		}
		catch (Exception ex) {}
		return priority;
	}
	
	public GoogleIssue setPriority(GoogleIssuePriority priority) {
		dbObject.put("priority", priority.toString());
		notifyChanged();
		return this;
	}
	public GoogleIssueType getType() {
		GoogleIssueType type = null;
		try {
			type = GoogleIssueType.valueOf(dbObject.get("type")+"");
		}
		catch (Exception ex) {}
		return type;
	}
	
	public GoogleIssue setType(GoogleIssueType type) {
		dbObject.put("type", type.toString());
		notifyChanged();
		return this;
	}
	public String getComponent() {
		return parseString(dbObject.get("component")+"", "");
	}
	
	public GoogleIssue setComponent(String component) {
		dbObject.put("component", component);
		notifyChanged();
		return this;
	}
	public GoogleIssueStatus getStatus() {
		GoogleIssueStatus status = null;
		try {
			status = GoogleIssueStatus.valueOf(dbObject.get("status")+"");
		}
		catch (Exception ex) {}
		return status;
	}
	
	public GoogleIssue setStatus(GoogleIssueStatus status) {
		dbObject.put("status", status.toString());
		notifyChanged();
		return this;
	}
	public int getStars() {
		return parseInteger(dbObject.get("stars")+"", 0);
	}
	
	public GoogleIssue setStars(int stars) {
		dbObject.put("stars", stars);
		notifyChanged();
		return this;
	}
	public String getSummary() {
		return parseString(dbObject.get("summary")+"", "");
	}
	
	public GoogleIssue setSummary(String summary) {
		dbObject.put("summary", summary);
		notifyChanged();
		return this;
	}
	
	
	public List<GoogleIssueComment> getComments() {
		if (comments == null) {
			comments = new PongoList<GoogleIssueComment>(this, "comments", true);
		}
		return comments;
	}
	public List<GoogleLabel> getLabels() {
		if (labels == null) {
			labels = new PongoList<GoogleLabel>(this, "labels", true);
		}
		return labels;
	}
	
	
	public GoogleUser getOwner() {
		if (owner == null && dbObject.containsField("owner")) {
			owner = (GoogleUser) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("owner"));
			owner.setContainer(this);
		}
		return owner;
	}
	
	public GoogleIssue setOwner(GoogleUser owner) {
		if (this.owner != owner) {
			if (owner == null) {
				dbObject.removeField("owner");
			}
			else {
				dbObject.put("owner", owner.getDbObject());
			}
			this.owner = owner;
			notifyChanged();
		}
		return this;
	}
}