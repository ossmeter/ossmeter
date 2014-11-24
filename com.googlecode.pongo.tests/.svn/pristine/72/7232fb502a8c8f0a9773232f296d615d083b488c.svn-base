package com.googlecode.pongo.tests.inheritance.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ChildOne extends Parent {
	
	
	
	public ChildOne() { 
		super();
		super.setSuperTypes("com.googlecode.pongo.tests.inheritance.model.Parent");
		NAME.setOwningType("com.googlecode.pongo.tests.inheritance.model.ChildOne");
		AGE.setOwningType("com.googlecode.pongo.tests.inheritance.model.ChildOne");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static NumericalQueryProducer AGE = new NumericalQueryProducer("age");
	
	
	
	
	
	
}