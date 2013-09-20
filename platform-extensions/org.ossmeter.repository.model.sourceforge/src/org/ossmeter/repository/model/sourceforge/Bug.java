package org.ossmeter.repository.model.sourceforge;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Bug extends Tracker {
	
	protected org.ossmeter.repository.model.Person assignee = null;
	protected org.ossmeter.repository.model.Person submitted = null;
	
	
	public Bug() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.sourceforge.Tracker","org.ossmeter.repository.model.sourceforge.NamedElement");
		LOCATION.setOwningType("org.ossmeter.repository.model.sourceforge.Bug");
		STATUS.setOwningType("org.ossmeter.repository.model.sourceforge.Bug");
		DESCRIPTION.setOwningType("org.ossmeter.repository.model.sourceforge.Bug");
		PRIORITY.setOwningType("org.ossmeter.repository.model.sourceforge.Bug");
		RESOLUTIONSTATUS.setOwningType("org.ossmeter.repository.model.sourceforge.Bug");
		BUGVISIBILITY.setOwningType("org.ossmeter.repository.model.sourceforge.Bug");
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
	
	public Bug setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public int getPriority() {
		return parseInteger(dbObject.get("priority")+"", 0);
	}
	
	public Bug setPriority(int priority) {
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
	
	public Bug setResolutionStatus(ResolutionStatus resolutionStatus) {
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
	
	public Bug setBugVisibility(BugVisibility bugVisibility) {
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
	
	public Bug setAssignee(org.ossmeter.repository.model.Person assignee) {
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
	
	public Bug setSubmitted(org.ossmeter.repository.model.Person submitted) {
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