package com.googlecode.pongo.tests.zoo.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Enclosure extends Building {
	
	
	
	public Enclosure() { 
		super();
		super.setSuperTypes("com.googlecode.pongo.tests.zoo.model.Building");
		NAME.setOwningType("com.googlecode.pongo.tests.zoo.model.Enclosure");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	
	
	
	
	
	
}