package com.googlecode.pongo.tests.inheritance.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class Parent extends Pongo {
	
	
	
	public Parent() { 
		super();
		NAME.setOwningType("com.googlecode.pongo.tests.inheritance.model.Parent");
		AGE.setOwningType("com.googlecode.pongo.tests.inheritance.model.Parent");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static NumericalQueryProducer AGE = new NumericalQueryProducer("age");
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public Parent setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public int getAge() {
		return parseInteger(dbObject.get("age")+"", 0);
	}
	
	public Parent setAge(int age) {
		dbObject.put("age", age);
		notifyChanged();
		return this;
	}
	
	
	
	
}