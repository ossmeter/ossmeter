package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class Person extends NamedElement {
	
	
	
	public Person() { 
		super();
		super.setSuperTypes("com.googlecode.pongo.tests.ossmeter.model.NamedElement");
		NAME.setOwningType("com.googlecode.pongo.tests.ossmeter.model.Person");
		HOMEPAGE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.Person");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer HOMEPAGE = new StringQueryProducer("homePage"); 
	
	
	public String getHomePage() {
		return parseString(dbObject.get("homePage")+"", "");
	}
	
	public Person setHomePage(String homePage) {
		dbObject.put("homePage", homePage);
		notifyChanged();
		return this;
	}
	
	
	
	
}