package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Bug extends Pongo {
	
	
	
	public Bug() { 
		super();
		DESCRIPTION.setOwningType("com.googlecode.pongo.tests.ossmeter.model.Bug");
		STATUS.setOwningType("com.googlecode.pongo.tests.ossmeter.model.Bug");
		ASSIGNEE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.Bug");
		SUMITTED.setOwningType("com.googlecode.pongo.tests.ossmeter.model.Bug");
		PRIORITY.setOwningType("com.googlecode.pongo.tests.ossmeter.model.Bug");
		RESOLUTIONSTATUS.setOwningType("com.googlecode.pongo.tests.ossmeter.model.Bug");
		BUGVISIBILITY.setOwningType("com.googlecode.pongo.tests.ossmeter.model.Bug");
	}
	
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static StringQueryProducer ASSIGNEE = new StringQueryProducer("assignee"); 
	public static StringQueryProducer SUMITTED = new StringQueryProducer("sumitted"); 
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
	public BugStatus getStatus() {
		BugStatus status = null;
		try {
			status = BugStatus.valueOf(dbObject.get("status")+"");
		}
		catch (Exception ex) {}
		return status;
	}
	
	public Bug setStatus(BugStatus status) {
		dbObject.put("status", status.toString());
		notifyChanged();
		return this;
	}
	public String getAssignee() {
		return parseString(dbObject.get("assignee")+"", "");
	}
	
	public Bug setAssignee(String assignee) {
		dbObject.put("assignee", assignee);
		notifyChanged();
		return this;
	}
	public String getSumitted() {
		return parseString(dbObject.get("sumitted")+"", "");
	}
	
	public Bug setSumitted(String sumitted) {
		dbObject.put("sumitted", sumitted);
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
	
	
	
	
}