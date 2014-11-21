package com.googlecode.pongo.tests.zoo.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class Bird extends Animal {
	
	
	
	public Bird() { 
		super();
		super.setSuperTypes("com.googlecode.pongo.tests.zoo.model.Animal");
		SPECIES.setOwningType("com.googlecode.pongo.tests.zoo.model.Bird");
		NAME.setOwningType("com.googlecode.pongo.tests.zoo.model.Bird");
		WEIGHT.setOwningType("com.googlecode.pongo.tests.zoo.model.Bird");
		HEIGHT.setOwningType("com.googlecode.pongo.tests.zoo.model.Bird");
		AGE.setOwningType("com.googlecode.pongo.tests.zoo.model.Bird");
		ORIGIN.setOwningType("com.googlecode.pongo.tests.zoo.model.Bird");
	}
	
	public static StringQueryProducer SPECIES = new StringQueryProducer("species"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static NumericalQueryProducer WEIGHT = new NumericalQueryProducer("weight");
	public static NumericalQueryProducer HEIGHT = new NumericalQueryProducer("height");
	public static NumericalQueryProducer AGE = new NumericalQueryProducer("age");
	public static StringQueryProducer ORIGIN = new StringQueryProducer("origin"); 
	
	
	
	
	
	
}