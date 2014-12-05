/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Davide Di Ruscio - Implementation,
 *    Juri Di Rocco - Implementation.
 *******************************************************************************/
package org.ossmeter.repository.model.sourceforge;

import com.mongodb.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class Request extends Tracker {
	
	protected org.ossmeter.repository.model.Person creator = null;
	
	
	public Request() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.sourceforge.Tracker","org.ossmeter.repository.model.sourceforge.NamedElement");
		LOCATION.setOwningType("org.ossmeter.repository.model.sourceforge.Request");
		STATUS.setOwningType("org.ossmeter.repository.model.sourceforge.Request");
		SUMMARY.setOwningType("org.ossmeter.repository.model.sourceforge.Request");
		CREATED_AT.setOwningType("org.ossmeter.repository.model.sourceforge.Request");
		UPDATED_AT.setOwningType("org.ossmeter.repository.model.sourceforge.Request");
	}
	
	public static StringQueryProducer LOCATION = new StringQueryProducer("location"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static StringQueryProducer SUMMARY = new StringQueryProducer("summary"); 
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer UPDATED_AT = new StringQueryProducer("updated_at"); 
	
	
	public String getSummary() {
		return parseString(dbObject.get("summary")+"", "");
	}
	
	public Request setSummary(String summary) {
		dbObject.put("summary", summary);
		notifyChanged();
		return this;
	}
	public String getCreated_at() {
		return parseString(dbObject.get("created_at")+"", "");
	}
	
	public Request setCreated_at(String created_at) {
		dbObject.put("created_at", created_at);
		notifyChanged();
		return this;
	}
	public String getUpdated_at() {
		return parseString(dbObject.get("updated_at")+"", "");
	}
	
	public Request setUpdated_at(String updated_at) {
		dbObject.put("updated_at", updated_at);
		notifyChanged();
		return this;
	}
	
	
	
	
	public org.ossmeter.repository.model.Person getCreator() {
		if (creator == null && dbObject.containsField("creator")) {
			creator = (org.ossmeter.repository.model.Person) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("creator"));
		}
		return creator;
	}
	
	public Request setCreator(org.ossmeter.repository.model.Person creator) {
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