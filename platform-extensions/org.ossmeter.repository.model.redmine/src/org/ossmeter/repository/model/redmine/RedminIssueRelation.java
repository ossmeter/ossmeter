package org.ossmeter.repository.model.redmine;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class RedminIssueRelation extends Pongo {
	
	protected RedmineIssue relatedIssue = null;
	
	
	public RedminIssueRelation() { 
		super();
		dbObject.put("relatedIssue", new BasicDBObject());
		TYPE.setOwningType("org.ossmeter.repository.model.redmine.RedminIssueRelation");
	}
	
	public static StringQueryProducer TYPE = new StringQueryProducer("type"); 
	
	
	public RedminIssueRelationType getType() {
		RedminIssueRelationType type = null;
		try {
			type = RedminIssueRelationType.valueOf(dbObject.get("type")+"");
		}
		catch (Exception ex) {}
		return type;
	}
	
	public RedminIssueRelation setType(RedminIssueRelationType type) {
		dbObject.put("type", type.toString());
		notifyChanged();
		return this;
	}
	
	
	
	public RedminIssueRelation setRelatedIssue(RedmineIssue relatedIssue) {
		if (this.relatedIssue != relatedIssue) {
			if (relatedIssue == null) {
				dbObject.put("relatedIssue", new BasicDBObject());
			}
			else {
				createReference("relatedIssue", relatedIssue);
			}
			this.relatedIssue = relatedIssue;
			notifyChanged();
		}
		return this;
	}
	
	public RedmineIssue getRelatedIssue() {
		if (relatedIssue == null) {
			relatedIssue = (RedmineIssue) resolveReference("relatedIssue");
		}
		return relatedIssue;
	}
	
}