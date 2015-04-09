package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Company extends NamedElement {
	
	
	
	public Company() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.NamedElement");
		NAME.setOwningType("org.ossmeter.repository.model.Company");
		URL.setOwningType("org.ossmeter.repository.model.Company");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public Company setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	
	
	
	
}