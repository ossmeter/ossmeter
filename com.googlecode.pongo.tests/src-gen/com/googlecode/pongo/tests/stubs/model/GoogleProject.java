package com.googlecode.pongo.tests.stubs.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class GoogleProject extends com.googlecode.pongo.tests.stubs.model.ProjectFromAnotherLibrary {
	
	protected List<com.googlecode.pongo.tests.stubs.model.DeveloperFromAnotherLibrary> developers = null;
	
	
	public GoogleProject() { 
		super();
		dbObject.put("developers", new BasicDBList());
	}
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public GoogleProject setName(String name) {
		dbObject.put("name", name + "");
		notifyChanged();
		return this;
	}
	
	
	public List<com.googlecode.pongo.tests.stubs.model.DeveloperFromAnotherLibrary> getDevelopers() {
		if (developers == null) {
			developers = new PongoList<com.googlecode.pongo.tests.stubs.model.DeveloperFromAnotherLibrary>(this, "developers", false);
		}
		return developers;
	}
	
	
}