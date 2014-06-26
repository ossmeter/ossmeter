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
		ARCHIVEURL.setOwningType("org.ossmeter.repository.model.eclipse.MailingList");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static StringQueryProducer TYPE = new StringQueryProducer("type"); 
	public static StringQueryProducer ARCHIVEURL = new StringQueryProducer("archiveUrl"); 
	
	
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
	public String getType() {
		return parseString(dbObject.get("type")+"", "");
	}
	
	public MailingList setType(String type) {
		dbObject.put("type", type);
		notifyChanged();
		return this;
	}
	public String getArchiveUrl() {
		return parseString(dbObject.get("archiveUrl")+"", "");
	}
	
	public MailingList setArchiveUrl(String archiveUrl) {
		dbObject.put("archiveUrl", archiveUrl);
		notifyChanged();
		return this;
	}
	
	
	
	
}