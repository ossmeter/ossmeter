package org.ossmeter.repository.model.googlecode;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class GoogleIssue extends Pongo {
	
	protected List<GoogleIssueComment> comments = null;
	protected List<GoogleLabel> labels = null;
	protected GoogleUser owner = null;
	
	
	public GoogleIssue() { 
		super();
		dbObject.put("comments", new BasicDBList());
		dbObject.put("labels", new BasicDBList());
	}
	
	public String getCreated_at() {
		return parseString(dbObject.get("created_at")+"", "");
	}
	
	public GoogleIssue setCreated_at(String created_at) {
		dbObject.put("created_at", created_at + "");
		notifyChanged();
		return this;
	}
	public String getUpdated_at() {
		return parseString(dbObject.get("updated_at")+"", "");
	}
	
	public GoogleIssue setUpdated_at(String updated_at) {
		dbObject.put("updated_at", updated_at + "");
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
		dbObject.put("priority", priority + "");
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
		dbObject.put("type", type + "");
		notifyChanged();
		return this;
	}
	public String getComponent() {
		return parseString(dbObject.get("component")+"", "");
	}
	
	public GoogleIssue setComponent(String component) {
		dbObject.put("component", component + "");
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
		dbObject.put("status", status + "");
		notifyChanged();
		return this;
	}
	public int getStars() {
		return parseInteger(dbObject.get("stars")+"", 0);
	}
	
	public GoogleIssue setStars(int stars) {
		dbObject.put("stars", stars + "");
		notifyChanged();
		return this;
	}
	public String getSummary() {
		return parseString(dbObject.get("summary")+"", "");
	}
	
	public GoogleIssue setSummary(String summary) {
		dbObject.put("summary", summary + "");
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