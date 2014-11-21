package com.googlecode.pongo.tests.zoo.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class Animal extends Pongo {
	
	
	
	public Animal() { 
		super();
		SPECIES.setOwningType("com.googlecode.pongo.tests.zoo.model.Animal");
		NAME.setOwningType("com.googlecode.pongo.tests.zoo.model.Animal");
		WEIGHT.setOwningType("com.googlecode.pongo.tests.zoo.model.Animal");
		HEIGHT.setOwningType("com.googlecode.pongo.tests.zoo.model.Animal");
		AGE.setOwningType("com.googlecode.pongo.tests.zoo.model.Animal");
		ORIGIN.setOwningType("com.googlecode.pongo.tests.zoo.model.Animal");
	}
	
	public static StringQueryProducer SPECIES = new StringQueryProducer("species"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static NumericalQueryProducer WEIGHT = new NumericalQueryProducer("weight");
	public static NumericalQueryProducer HEIGHT = new NumericalQueryProducer("height");
	public static NumericalQueryProducer AGE = new NumericalQueryProducer("age");
	public static StringQueryProducer ORIGIN = new StringQueryProducer("origin"); 
	
	
	public String getSpecies() {
		return parseString(dbObject.get("species")+"", "");
	}
	
	public Animal setSpecies(String species) {
		dbObject.put("species", species);
		notifyChanged();
		return this;
	}
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public Animal setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public double getWeight() {
		return parseDouble(dbObject.get("weight")+"", 0.0d);
	}
	
	public Animal setWeight(double weight) {
		dbObject.put("weight", weight);
		notifyChanged();
		return this;
	}
	public double getHeight() {
		return parseDouble(dbObject.get("height")+"", 0.0d);
	}
	
	public Animal setHeight(double height) {
		dbObject.put("height", height);
		notifyChanged();
		return this;
	}
	public int getAge() {
		return parseInteger(dbObject.get("age")+"", 0);
	}
	
	public Animal setAge(int age) {
		dbObject.put("age", age);
		notifyChanged();
		return this;
	}
	public String getOrigin() {
		return parseString(dbObject.get("origin")+"", "");
	}
	
	public Animal setOrigin(String origin) {
		dbObject.put("origin", origin);
		notifyChanged();
		return this;
	}
	
	
	
	
}