package org.ossmeter.repository.model.eclipse;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Release extends org.ossmeter.repository.model.NamedElement {
	
	
	
	public Release() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.eclipse.NamedElement");
		TYPE.setOwningType("org.ossmeter.repository.model.eclipse.Release");
		DATE.setOwningType("org.ossmeter.repository.model.eclipse.Release");
		LINK.setOwningType("org.ossmeter.repository.model.eclipse.Release");
	}
	
	public static StringQueryProducer TYPE = new StringQueryProducer("type"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	public static StringQueryProducer LINK = new StringQueryProducer("link"); 
	
	
	public ReleaseType getType() {
		ReleaseType type = null;
		try {
			type = ReleaseType.valueOf(dbObject.get("type")+"");
		}
		catch (Exception ex) {}
		return type;
	}
	
	public Release setType(ReleaseType type) {
		dbObject.put("type", type.toString());
		notifyChanged();
		return this;
	}
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public Release setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	public String getLink() {
		return parseString(dbObject.get("link")+"", "");
	}
	
	public Release setLink(String link) {
		dbObject.put("link", link);
		notifyChanged();
		return this;
	}
	
	
	
	
}