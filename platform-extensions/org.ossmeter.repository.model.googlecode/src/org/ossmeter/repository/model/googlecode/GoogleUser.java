package org.ossmeter.repository.model.googlecode;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class GoogleUser extends org.ossmeter.repository.model.Person {
	
	
	
	public GoogleUser() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.googlecode.Person");
		EMAIL.setOwningType("org.ossmeter.repository.model.googlecode.GoogleUser");
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