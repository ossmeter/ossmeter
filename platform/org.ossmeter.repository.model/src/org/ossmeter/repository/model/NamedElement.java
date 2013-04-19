package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public abstract class NamedElement extends Pongo {
	
	
	
	public NamedElement() { 
		super();
	}
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public NamedElement setName(String name) {
		dbObject.put("name", name + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}