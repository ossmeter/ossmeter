package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class Tracker extends NamedElement {
	
	
	
	public Tracker() { 
		super();
		super.setSuperTypes("com.googlecode.pongo.tests.ossmeter.model.NamedElement");
		NAME.setOwningType("com.googlecode.pongo.tests.ossmeter.model.Tracker");
		LOCATION.setOwningType("com.googlecode.pongo.tests.ossmeter.model.Tracker");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer LOCATION = new StringQueryProducer("location"); 
	
	
	public String getLocation() {
		return parseString(dbObject.get("location")+"", "");
	}
	
	public Tracker setLocation(String location) {
		dbObject.put("location", location);
		notifyChanged();
		return this;
	}
	
	
	
	
}