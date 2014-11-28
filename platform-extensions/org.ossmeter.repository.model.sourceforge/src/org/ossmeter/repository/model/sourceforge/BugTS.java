/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Davide Di Ruscio - Implementation.
 *******************************************************************************/
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
	public String getResolutionStatus() {
		return parseString(dbObject.get("resolutionStatus")+"", "");
	}
	
	public BugTS setResolutionStatus(String resolutionStatus) {
		dbObject.put("resolutionStatus", resolutionStatus);
		notifyChanged();
		return this;
	}
	public String getBugVisibility() {
		return parseString(dbObject.get("bugVisibility")+"", "");
	}
	
	public BugTS setBugVisibility(String bugVisibility) {
		dbObject.put("bugVisibility", bugVisibility);
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