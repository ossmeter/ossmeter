package com.googlecode.pongo.tests.zoo.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Cafe extends Building {
	
	
	
	public Cafe() { 
		super();
		super.setSuperTypes("com.googlecode.pongo.tests.zoo.model.Building");
		NAME.setOwningType("com.googlecode.pongo.tests.zoo.model.Cafe");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	
	
	
	
	
	
}