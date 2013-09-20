package org.ossmeter.repository.model.eclipse;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class MailingList extends org.ossmeter.repository.model.CommunicationChannel {
	
	
	
	public MailingList() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.eclipse.CommunicationChannel");
		NAME.setOwningType("org.ossmeter.repository.model.eclipse.MailingList");
		DESCRIPTION.setOwningType("org.ossmeter.repository.model.eclipse.MailingList");
		TYPE.setOwningType("org.ossmeter.repository.model.eclipse.MailingList");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static StringQueryProducer TYPE = new StringQueryProducer("type"); 
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public MailingList setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public MailingList setDescription(String description) {
		dbObject.put("description", description);
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
		dbObject.put("type", type.toString());
		notifyChanged();
		return this;
	}
	
	
	
	
}