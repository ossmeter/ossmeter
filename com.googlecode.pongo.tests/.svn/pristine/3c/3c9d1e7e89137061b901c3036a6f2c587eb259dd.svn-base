package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class SVNRepository extends VCSRepository {
	
	
	
	public SVNRepository() { 
		super();
		super.setSuperTypes("com.googlecode.pongo.tests.ossmeter.model.VCSRepository");
		CREATED_AT.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SVNRepository");
		UPDATED_AT.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SVNRepository");
		URL.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SVNRepository");
		BROWSE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.SVNRepository");
	}
	
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer UPDATED_AT = new StringQueryProducer("updated_at"); 
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer BROWSE = new StringQueryProducer("browse"); 
	
	
	public String getBrowse() {
		return parseString(dbObject.get("browse")+"", "");
	}
	
	public SVNRepository setBrowse(String browse) {
		dbObject.put("browse", browse);
		notifyChanged();
		return this;
	}
	
	
	
	
}