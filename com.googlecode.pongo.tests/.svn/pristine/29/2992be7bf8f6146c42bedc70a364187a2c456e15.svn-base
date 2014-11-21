package com.googlecode.pongo.tests.zoo.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class Reptile extends Animal {
	
	
	
	public Reptile() { 
		super();
		super.setSuperTypes("com.googlecode.pongo.tests.zoo.model.Animal");
		SPECIES.setOwningType("com.googlecode.pongo.tests.zoo.model.Reptile");
		NAME.setOwningType("com.googlecode.pongo.tests.zoo.model.Reptile");
		WEIGHT.setOwningType("com.googlecode.pongo.tests.zoo.model.Reptile");
		HEIGHT.setOwningType("com.googlecode.pongo.tests.zoo.model.Reptile");
		AGE.setOwningType("com.googlecode.pongo.tests.zoo.model.Reptile");
		ORIGIN.setOwningType("com.googlecode.pongo.tests.zoo.model.Reptile");
	}
	
	public static StringQueryProducer SPECIES = new StringQueryProducer("species"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static NumericalQueryProducer WEIGHT = new NumericalQueryProducer("weight");
	public static NumericalQueryProducer HEIGHT = new NumericalQueryProducer("height");
	public static NumericalQueryProducer AGE = new NumericalQueryProducer("age");
	public static StringQueryProducer ORIGIN = new StringQueryProducer("origin"); 
	
	
	
	
	
	
}