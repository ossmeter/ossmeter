package org.ossmeter.repository.model.sourceforge;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugTS extends Tracker {
	
	protected org.ossmeter.repository.model.Person assignee = null;
	protected org.ossmeter.repository.model.Person submitted = null;
	
	
	public BugTS() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.sourceforge.Tracker","org.ossmeter.repository.model.sourceforge.NamedElement");
		LOCATION.setOwningType("org.ossmeter.repository.model.sourceforge.BugTS");
		STATUS.setOwningType("org.ossmeter.repository.model.sourceforge.BugTS");
		DESCRIPTION.setOwningType("org.ossmeter.repository.model.sourceforge.BugTS");
		PRIORITY.setOwningType("org.ossmeter.repository.model.sourceforge.BugTS");
		RESOLUTIONSTATUS.setOwningType("org.ossmeter.repository.model.sourceforge.BugTS");
		BUGVISIBILITY.setOwningType("org.ossmeter.repository.model.sourceforge.BugTS");
	}
	
	public static StringQueryProducer LOCATION = new StringQueryProducer("location"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static NumericalQueryProducer PRIORITY = new NumericalQueryProducer("priority");
	public static StringQueryProducer RESOLUTIONSTATUS = new StringQueryProducer("resolutionStatus"); 
	public static StringQueryProducer BUGVISIBILITY = new StringQueryProducer("bugVisibility"); 
	
	
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public BugTS setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public int getPriority() {
		return parseInteger(dbObject.get("priority")+"", 0);
	}
	
	public BugTS setPriority(int priority) {
		dbObject.put("priority", priority);
		notifyChanged();
		return this;
	}
	public ResolutionStatus getResolutionStatus() {
		ResolutionStatus resolutionStatus = null;
		try {
			resolutionStatus = ResolutionStatus.valueOf(dbObject.get("resolutionStatus")+"");
		}
		catch (Exception ex) {}
		return resolutionStatus;
	}
	
	public BugTS setResolutionStatus(ResolutionStatus resolutionStatus) {
		dbObject.put("resolutionStatus", resolutionStatus.toString());
		notifyChanged();
		return this;
	}
	public BugVisibility getBugVisibility() {
		BugVisibility bugVisibility = null;
		try {
			bugVisibility = BugVisibility.valueOf(dbObject.get("bugVisibility")+"");
		}
		catch (Exception ex) {}
		return bugVisibility;
	}
	
	public BugTS setBugVisibility(BugVisibility bugVisibility) {
		dbObject.put("bugVisibility", bugVisibility.toString());
		notifyChanged();
		return this;
	}
	
	
	
	
	public org.ossmeter.repository.model.Person getAssignee() {
		if (assignee == null && dbObject.containsField("assignee")) {
			assignee = (org.ossmeter.repository.model.Person) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("assignee"));
		}
		return assignee;
	}
	
	public BugTS setAssignee(org.ossmeter.repository.model.Person assignee) {
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
	public org.ossmeter.repository.model.Person getSubmitted() {
		if (submitted == null && dbObject.containsField("submitted")) {
			submitted = (org.ossmeter.repository.model.Person) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("submitted"));
		}
		return submitted;
	}
	
	public BugTS setSubmitted(org.ossmeter.repository.model.Person submitted) {
		if (this.submitted != submitted) {
			if (submitted == null) {
				dbObject.removeField("submitted");
			}
			else {
				dbObject.put("submitted", submitted.getDbObject());
			}
			this.submitted = submitted;
			notifyChanged();
		}
		return this;
	}
}