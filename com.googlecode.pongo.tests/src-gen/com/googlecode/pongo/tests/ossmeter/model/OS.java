package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class OS extends NamedElement {
	
	
	
	public OS() { 
		super();
		super.setSuperTypes("com.googlecode.pongo.tests.ossmeter.model.NamedElement");
		NAME.setOwningType("com.googlecode.pongo.tests.ossmeter.model.OS");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	
	
	
	
	
	
}