package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class BugTrackingSystem extends Pongo {
	
	protected List<Person> persons = null;
	
	
	public BugTrackingSystem() { 
		super();
		dbObject.put("persons", new BasicDBList());
		URL.setOwningType("org.ossmeter.repository.model.BugTrackingSystem");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public BugTrackingSystem setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	
	
	public List<Person> getPersons() {
		if (persons == null) {
			persons = new PongoList<Person>(this, "persons", false);
		}
		return persons;
	}
	
	
}