package org.ossmeter.repository.model.eclipseforge;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class NewsGroup extends org.ossmeter.repository.model.CommunicationChannel {
	
	
	
	public NewsGroup() { 
		super();
	}
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public NewsGroup setName(String name) {
		dbObject.put("name", name + "");
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public NewsGroup setDescription(String description) {
		dbObject.put("description", description + "");
		notifyChanged();
		return this;
	}
	public NewsGroupType getType() {
		NewsGroupType type = null;
		try {
			type = NewsGroupType.valueOf(dbObject.get("type")+"");
		}
		catch (Exception ex) {}
		return type;
	}
	
	public NewsGroup setType(NewsGroupType type) {
		dbObject.put("type", type + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}