package org.ossmeter.repository.model.sourceforge;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public abstract class Request extends Tracker {
	
	protected org.ossmeter.repository.model.Person creator = null;
	
	
	public Request() { 
		super();
	}
	
	public String getSummary() {
		return parseString(dbObject.get("summary")+"", "");
	}
	
	public Request setSummary(String summary) {
		dbObject.put("summary", summary + "");
		notifyChanged();
		return this;
	}
	public String getCreated_at() {
		return parseString(dbObject.get("created_at")+"", "");
	}
	
	public Request setCreated_at(String created_at) {
		dbObject.put("created_at", created_at + "");
		notifyChanged();
		return this;
	}
	public String getUpdated_at() {
		return parseString(dbObject.get("updated_at")+"", "");
	}
	
	public Request setUpdated_at(String updated_at) {
		dbObject.put("updated_at", updated_at + "");
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