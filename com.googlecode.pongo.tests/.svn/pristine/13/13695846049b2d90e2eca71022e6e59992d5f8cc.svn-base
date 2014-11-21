package com.googlecode.pongo.tests.zoo.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class Amphibian extends Animal {
	
	
	
	public Amphibian() { 
		super();
		super.setSuperTypes("com.googlecode.pongo.tests.zoo.model.Animal");
		SPECIES.setOwningType("com.googlecode.pongo.tests.zoo.model.Amphibian");
		NAME.setOwningType("com.googlecode.pongo.tests.zoo.model.Amphibian");
		WEIGHT.setOwningType("com.googlecode.pongo.tests.zoo.model.Amphibian");
		HEIGHT.setOwningType("com.googlecode.pongo.tests.zoo.model.Amphibian");
		AGE.setOwningType("com.googlecode.pongo.tests.zoo.model.Amphibian");
		ORIGIN.setOwningType("com.googlecode.pongo.tests.zoo.model.Amphibian");
	}
	
	public static StringQueryProducer SPECIES = new StringQueryProducer("species"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static NumericalQueryProducer WEIGHT = new NumericalQueryProducer("weight");
	public static NumericalQueryProducer HEIGHT = new NumericalQueryProducer("height");
	public static NumericalQueryProducer AGE = new NumericalQueryProducer("age");
	public static StringQueryProducer ORIGIN = new StringQueryProducer("origin"); 
	
	
	
	
	
	
}