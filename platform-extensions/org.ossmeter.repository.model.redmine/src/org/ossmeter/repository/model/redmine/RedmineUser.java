package org.ossmeter.repository.model.redmine;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class RedmineUser extends org.ossmeter.repository.model.Person {
	
	
	
	public RedmineUser() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.redmine.Person");
		LOGIN.setOwningType("org.ossmeter.repository.model.redmine.RedmineUser");
	}
	
	public static StringQueryProducer LOGIN = new StringQueryProducer("login"); 
	
	
	public String getLogin() {
		return parseString(dbObject.get("login")+"", "");
	}
	
	public RedmineUser setLogin(String login) {
		dbObject.put("login", login);
		notifyChanged();
		return this;
	}
	
	
	
	
}