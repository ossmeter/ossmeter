package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class GoogleUser extends Pongo {
	
	
	
	public GoogleUser() { 
		super();
		EMAIL.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GoogleUser");
	}
	
	public static StringQueryProducer EMAIL = new StringQueryProducer("email"); 
	
	
	public String getEmail() {
		return parseString(dbObject.get("email")+"", "");
	}
	
	public GoogleUser setEmail(String email) {
		dbObject.put("email", email);
		notifyChanged();
		return this;
	}
	
	
	
	
}