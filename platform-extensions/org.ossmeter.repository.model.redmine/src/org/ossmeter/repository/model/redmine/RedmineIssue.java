package org.ossmeter.repository.model.redmine;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class RedmineIssue extends Pongo {
	
	protected List<RedminIssueRelation> relations = null;
	protected RedmineCategory category = null;
	protected RedmineFeature feature = null;
	protected RedmineUser author = null;
	protected RedmineUser assignedTo = null;
	
	
	public RedmineIssue() { 
		super();
		dbObject.put("author", new BasicDBObject());
		dbObject.put("assignedTo", new BasicDBObject());
		dbObject.put("relations", new BasicDBList());
		DESCRIPTION.setOwningType("org.ossmeter.repository.model.redmine.RedmineIssue");
		STATUS.setOwningType("org.ossmeter.repository.model.redmine.RedmineIssue");
		PRIORITY.setOwningType("org.ossmeter.repository.model.redmine.RedmineIssue");
		TEMPLATE.setOwningType("org.ossmeter.repository.model.redmine.RedmineIssue");
		START_DATE.setOwningType("org.ossmeter.repository.model.redmine.RedmineIssue");
		UPDATE_DATE.setOwningType("org.ossmeter.repository.model.redmine.RedmineIssue");
		DUE_DATE.setOwningType("org.ossmeter.repository.model.redmine.RedmineIssue");
	}
	
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static StringQueryProducer PRIORITY = new StringQueryProducer("priority"); 
	public static StringQueryProducer TEMPLATE = new StringQueryProducer("template"); 
	public static StringQueryProducer START_DATE = new StringQueryProducer("start_date"); 
	public static StringQueryProducer UPDATE_DATE = new StringQueryProducer("update_date"); 
	public static StringQueryProducer DUE_DATE = new StringQueryProducer("due_date"); 
	
	
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public RedmineIssue setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public RedmineIssueStatus getStatus() {
		RedmineIssueStatus status = null;
		try {
			status = RedmineIssueStatus.valueOf(dbObject.get("status")+"");
		}
		catch (Exception ex) {}
		return status;
	}
	
	public RedmineIssue setStatus(RedmineIssueStatus status) {
		dbObject.put("status", status.toString());
		notifyChanged();
		return this;
	}
	public RedmineIssuePriority getPriority() {
		RedmineIssuePriority priority = null;
		try {
			priority = RedmineIssuePriority.valueOf(dbObject.get("priority")+"");
		}
		catch (Exception ex) {}
		return priority;
	}
	
	public RedmineIssue setPriority(RedmineIssuePriority priority) {
		dbObject.put("priority", priority.toString());
		notifyChanged();
		return this;
	}
	public RedminIssueTemplate getTemplate() {
		RedminIssueTemplate template = null;
		try {
			template = RedminIssueTemplate.valueOf(dbObject.get("template")+"");
		}
		catch (Exception ex) {}
		return template;
	}
	
	public RedmineIssue setTemplate(RedminIssueTemplate template) {
		dbObject.put("template", template.toString());
		notifyChanged();
		return this;
	}
	public String getStart_date() {
		return parseString(dbObject.get("start_date")+"", "");
	}
	
	public RedmineIssue setStart_date(String start_date) {
		dbObject.put("start_date", start_date);
		notifyChanged();
		return this;
	}
	public String getUpdate_date() {
		return parseString(dbObject.get("update_date")+"", "");
	}
	
	public RedmineIssue setUpdate_date(String update_date) {
		dbObject.put("update_date", update_date);
		notifyChanged();
		return this;
	}
	public String getDue_date() {
		return parseString(dbObject.get("due_date")+"", "");
	}
	
	public RedmineIssue setDue_date(String due_date) {
		dbObject.put("due_date", due_date);
		notifyChanged();
		return this;
	}
	
	
	public List<RedminIssueRelation> getRelations() {
		if (relations == null) {
			relations = new PongoList<RedminIssueRelation>(this, "relations", true);
		}
		return relations;
	}
	
	public RedmineIssue setAuthor(RedmineUser author) {
		if (this.author != author) {
			if (author == null) {
				dbObject.put("author", new BasicDBObject());
			}
			else {
				createReference("author", author);
			}
			this.author = author;
			notifyChanged();
		}
		return this;
	}
	
	public RedmineUser getAuthor() {
		if (author == null) {
			author = (RedmineUser) resolveReference("author");
		}
		return author;
	}
	public RedmineIssue setAssignedTo(RedmineUser assignedTo) {
		if (this.assignedTo != assignedTo) {
			if (assignedTo == null) {
				dbObject.put("assignedTo", new BasicDBObject());
			}
			else {
				createReference("assignedTo", assignedTo);
			}
			this.assignedTo = assignedTo;
			notifyChanged();
		}
		return this;
	}
	
	public RedmineUser getAssignedTo() {
		if (assignedTo == null) {
			assignedTo = (RedmineUser) resolveReference("assignedTo");
		}
		return assignedTo;
	}
	
	public RedmineCategory getCategory() {
		if (category == null && dbObject.containsField("category")) {
			category = (RedmineCategory) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("category"));
		}
		return category;
	}
	
	public RedmineIssue setCategory(RedmineCategory category) {
		if (this.category != category) {
			if (category == null) {
				dbObject.removeField("category");
			}
			else {
				dbObject.put("category", category.getDbObject());
			}
			this.category = category;
			notifyChanged();
		}
		return this;
	}
	public RedmineFeature getFeature() {
		if (feature == null && dbObject.containsField("feature")) {
			feature = (RedmineFeature) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("feature"));
		}
		return feature;
	}
	
	public RedmineIssue setFeature(RedmineFeature feature) {
		if (this.feature != feature) {
			if (feature == null) {
				dbObject.removeField("feature");
			}
			else {
				dbObject.put("feature", feature.getDbObject());
			}
			this.feature = feature;
			notifyChanged();
		}
		return this;
	}
}