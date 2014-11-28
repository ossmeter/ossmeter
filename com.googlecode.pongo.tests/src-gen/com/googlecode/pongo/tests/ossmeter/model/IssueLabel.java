package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class IssueLabel extends NamedElement {
	
	
	
	public IssueLabel() { 
		super();
		super.setSuperTypes("com.googlecode.pongo.tests.ossmeter.model.NamedElement");
		NAME.setOwningType("com.googlecode.pongo.tests.ossmeter.model.IssueLabel");
		URL.setOwningType("com.googlecode.pongo.tests.ossmeter.model.IssueLabel");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public IssueLabel setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	
	
	
	
}