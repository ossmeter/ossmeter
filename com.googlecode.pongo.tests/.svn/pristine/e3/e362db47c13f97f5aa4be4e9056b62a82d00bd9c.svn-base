package com.googlecode.pongo.tests.zoo.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class Staff extends Pongo {
	
	
	
	public Staff() { 
		super();
		NAME.setOwningType("com.googlecode.pongo.tests.zoo.model.Staff");
		ADDRESS.setOwningType("com.googlecode.pongo.tests.zoo.model.Staff");
		AGE.setOwningType("com.googlecode.pongo.tests.zoo.model.Staff");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer ADDRESS = new StringQueryProducer("address"); 
	public static NumericalQueryProducer AGE = new NumericalQueryProducer("age");
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public Staff setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public String getAddress() {
		return parseString(dbObject.get("address")+"", "");
	}
	
	public Staff setAddress(String address) {
		dbObject.put("address", address);
		notifyChanged();
		return this;
	}
	public int getAge() {
		return parseInteger(dbObject.get("age")+"", 0);
	}
	
	public Staff setAge(int age) {
		dbObject.put("age", age);
		notifyChanged();
		return this;
	}
	
	
	
	
}