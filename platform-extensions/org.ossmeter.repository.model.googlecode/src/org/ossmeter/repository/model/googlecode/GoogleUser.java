package org.ossmeter.repository.model.googlecode;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class GoogleUser extends org.ossmeter.repository.model.Person {
	
	
	
	public GoogleUser() { 
		super();
	}
	
	public String getEmail() {
		return parseString(dbObject.get("email")+"", "");
	}
	
	public GoogleUser setEmail(String email) {
		dbObject.put("email", email + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}