package org.ossmeter.metricprovider.activeusers.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class User extends Pongo {
	
	
	
	public User() { 
		super();
		URL_NAME.setOwningType("org.ossmeter.metricprovider.activeusers.model.User");
		USERID.setOwningType("org.ossmeter.metricprovider.activeusers.model.User");
		LASTACTIVITYDATE.setOwningType("org.ossmeter.metricprovider.activeusers.model.User");
	}
	
	public static StringQueryProducer URL_NAME = new StringQueryProducer("url_name"); 
	public static StringQueryProducer USERID = new StringQueryProducer("userId"); 
	public static StringQueryProducer LASTACTIVITYDATE = new StringQueryProducer("lastActivityDate"); 
	
	
	public String getUrl_name() {
		return parseString(dbObject.get("url_name")+"", "");
	}
	
	public User setUrl_name(String url_name) {
		dbObject.put("url_name", url_name);
		notifyChanged();
		return this;
	}
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