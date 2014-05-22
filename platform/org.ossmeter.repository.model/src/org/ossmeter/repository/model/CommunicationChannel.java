package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class CommunicationChannel extends Pongo {
	
	
	
	public CommunicationChannel() { 
		super();
		URL.setOwningType("org.ossmeter.repository.model.CommunicationChannel");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public CommunicationChannel setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	
	
	
	
}