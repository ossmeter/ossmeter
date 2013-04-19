package org.ossmeter.repository.model.eclipseforge;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class MailingList extends org.ossmeter.repository.model.CommunicationChannel {
	
	
	
	public MailingList() { 
		super();
	}
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public MailingList setName(String name) {
		dbObject.put("name", name + "");
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public MailingList setDescription(String description) {
		dbObject.put("description", description + "");
		notifyChanged();
		return this;
	}
	public MailingListType getType() {
		MailingListType type = null;
		try {
			type = MailingListType.valueOf(dbObject.get("type")+"");
		}
		catch (Exception ex) {}
		return type;
	}
	
	public MailingList setType(MailingListType type) {
		dbObject.put("type", type + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}