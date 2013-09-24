package org.ossmeter.repository.model.redmine;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class RedmineProjectVersion extends Pongo {
	
	
	
	public RedmineProjectVersion() { 
		super();
		NAME.setOwningType("org.ossmeter.repository.model.redmine.RedmineProjectVersion");
		DESCRIPTION.setOwningType("org.ossmeter.repository.model.redmine.RedmineProjectVersion");
		CREATED_ON.setOwningType("org.ossmeter.repository.model.redmine.RedmineProjectVersion");
		UPDATED_ON.setOwningType("org.ossmeter.repository.model.redmine.RedmineProjectVersion");
		STATUS.setOwningType("org.ossmeter.repository.model.redmine.RedmineProjectVersion");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static StringQueryProducer CREATED_ON = new StringQueryProducer("created_on"); 
	public static StringQueryProducer UPDATED_ON = new StringQueryProducer("updated_on"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public RedmineProjectVersion setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public RedmineProjectVersion setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public String getCreated_on() {
		return parseString(dbObject.get("created_on")+"", "");
	}
	
	public RedmineProjectVersion setCreated_on(String created_on) {
		dbObject.put("created_on", created_on);
		notifyChanged();
		return this;
	}
	public String getUpdated_on() {
		return parseString(dbObject.get("updated_on")+"", "");
	}
	
	public RedmineProjectVersion setUpdated_on(String updated_on) {
		dbObject.put("updated_on", updated_on);
		notifyChanged();
		return this;
	}
	public RedmineProjectVersionStatus getStatus() {
		RedmineProjectVersionStatus status = null;
		try {
			status = RedmineProjectVersionStatus.valueOf(dbObject.get("status")+"");
		}
		catch (Exception ex) {}
		return status;
	}
	
	public RedmineProjectVersion setStatus(RedmineProjectVersionStatus status) {
		dbObject.put("status", status.toString());
		notifyChanged();
		return this;
	}
	
	
	
	
}