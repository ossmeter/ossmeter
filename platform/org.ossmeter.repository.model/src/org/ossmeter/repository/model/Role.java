package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Role extends NamedElement {
	
	
	
	public Role() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.NamedElement");
		NAME.setOwningType("org.ossmeter.repository.model.Role");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	
	
	
	
	
	
}