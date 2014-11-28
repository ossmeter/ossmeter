package com.googlecode.pongo.tests.blog.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class Person extends Pongo {
	
	
	
	public Person() { 
		super();
	}
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public Person setName(String name) {
		dbObject.put("name", name + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}