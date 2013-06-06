package org.ossmeter.metricprovider.activeusers.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class User extends Pongo {
	
	
	
	public User() { 
		super();
		USERID.setOwningType("org.ossmeter.metricprovider.activeusers.model.User");
		LASTACTIVITYDATE.setOwningType("org.ossmeter.metricprovider.activeusers.model.User");
	}
	
	public static StringQueryProducer USERID = new StringQueryProducer("userId"); 
	public static StringQueryProducer LASTACTIVITYDATE = new StringQueryProducer("lastActivityDate"); 
	
	
	public String getUserId() {
		return parseString(dbObject.get("userId")+"", "");
	}
	
	public User setUserId(String userId) {
		dbObject.put("userId", userId);
		notifyChanged();
		return this;
	}
	public String getLastActivityDate() {
		return parseString(dbObject.get("lastActivityDate")+"", "");
	}
	
	public User setLastActivityDate(String lastActivityDate) {
		dbObject.put("lastActivityDate", lastActivityDate);
		notifyChanged();
		return this;
	}
	
	
	
	
}