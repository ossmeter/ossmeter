package com.googlecode.pongo.tests.zoo.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Shark extends Fish {
	
	
	
	public Shark() { 
		super();
		super.setSuperTypes("com.googlecode.pongo.tests.zoo.model.Fish","com.googlecode.pongo.tests.zoo.model.Animal");
		SPECIES.setOwningType("com.googlecode.pongo.tests.zoo.model.Shark");
		NAME.setOwningType("com.googlecode.pongo.tests.zoo.model.Shark");
		WEIGHT.setOwningType("com.googlecode.pongo.tests.zoo.model.Shark");
		HEIGHT.setOwningType("com.googlecode.pongo.tests.zoo.model.Shark");
		AGE.setOwningType("com.googlecode.pongo.tests.zoo.model.Shark");
		ORIGIN.setOwningType("com.googlecode.pongo.tests.zoo.model.Shark");
	}
	
	public static StringQueryProducer SPECIES = new StringQueryProducer("species"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static NumericalQueryProducer WEIGHT = new NumericalQueryProducer("weight");
	public static NumericalQueryProducer HEIGHT = new NumericalQueryProducer("height");
	public static NumericalQueryProducer AGE = new NumericalQueryProducer("age");
	public static StringQueryProducer ORIGIN = new StringQueryProducer("origin"); 
	
	
	
	
	
	
}