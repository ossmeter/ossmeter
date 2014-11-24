package com.googlecode.pongo.tests.zoo.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Monkey extends Mammal {
	
	
	
	public Monkey() { 
		super();
		super.setSuperTypes("com.googlecode.pongo.tests.zoo.model.Mammal","com.googlecode.pongo.tests.zoo.model.Animal");
		SPECIES.setOwningType("com.googlecode.pongo.tests.zoo.model.Monkey");
		NAME.setOwningType("com.googlecode.pongo.tests.zoo.model.Monkey");
		WEIGHT.setOwningType("com.googlecode.pongo.tests.zoo.model.Monkey");
		HEIGHT.setOwningType("com.googlecode.pongo.tests.zoo.model.Monkey");
		AGE.setOwningType("com.googlecode.pongo.tests.zoo.model.Monkey");
		ORIGIN.setOwningType("com.googlecode.pongo.tests.zoo.model.Monkey");
	}
	
	public static StringQueryProducer SPECIES = new StringQueryProducer("species"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static NumericalQueryProducer WEIGHT = new NumericalQueryProducer("weight");
	public static NumericalQueryProducer HEIGHT = new NumericalQueryProducer("height");
	public static NumericalQueryProducer AGE = new NumericalQueryProducer("age");
	public static StringQueryProducer ORIGIN = new StringQueryProducer("origin"); 
	
	
	
	
	
	
}