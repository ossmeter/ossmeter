package com.googlecode.pongo.tests.zoo.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Keeper extends Staff {
	
	protected List<Animal> animals = null;
	
	
	public Keeper() { 
		super();
		dbObject.put("animals", new BasicDBList());
		super.setSuperTypes("com.googlecode.pongo.tests.zoo.model.Staff");
		NAME.setOwningType("com.googlecode.pongo.tests.zoo.model.Keeper");
		ADDRESS.setOwningType("com.googlecode.pongo.tests.zoo.model.Keeper");
		AGE.setOwningType("com.googlecode.pongo.tests.zoo.model.Keeper");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer ADDRESS = new StringQueryProducer("address"); 
	public static NumericalQueryProducer AGE = new NumericalQueryProducer("age");
	
	
	
	
	public List<Animal> getAnimals() {
		if (animals == null) {
			animals = new PongoList<Animal>(this, "animals", false);
		}
		return animals;
	}
	
	
}