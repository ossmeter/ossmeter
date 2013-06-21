package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class BugTrackingSystem extends Pongo {
	
	
	
	public BugTrackingSystem() { 
		super();
	}
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public BugTrackingSystem setUrl(String url) {
		dbObject.put("url", url + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}