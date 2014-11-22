package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class VCSRepository extends Pongo {
	
	
	
	public VCSRepository() { 
		super();
		CREATED_AT.setOwningType("com.googlecode.pongo.tests.ossmeter.model.VCSRepository");
		UPDATED_AT.setOwningType("com.googlecode.pongo.tests.ossmeter.model.VCSRepository");
		URL.setOwningType("com.googlecode.pongo.tests.ossmeter.model.VCSRepository");
	}
	
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer UPDATED_AT = new StringQueryProducer("updated_at"); 
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	
	
	public String getCreated_at() {
		return parseString(dbObject.get("created_at")+"", "");
	}
	
	public VCSRepository setCreated_at(String created_at) {
		dbObject.put("created_at", created_at);
		notifyChanged();
		return this;
	}
	public String getUpdated_at() {
		return parseString(dbObject.get("updated_at")+"", "");
	}
	
	public VCSRepository setUpdated_at(String updated_at) {
		dbObject.put("updated_at", updated_at);
		notifyChanged();
		return this;
	}
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public VCSRepository setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	
	
	
	
}