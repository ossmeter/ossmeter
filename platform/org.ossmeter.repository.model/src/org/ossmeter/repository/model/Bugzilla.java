package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class Bugzilla extends BugTrackingSystem {
	
	
	
	public Bugzilla() { 
		super();
	}
	
	public String getProduct() {
		return parseString(dbObject.get("product")+"", "");
	}
	
	public Bugzilla setProduct(String product) {
		dbObject.put("product", product + "");
		notifyChanged();
		return this;
	}
	public String getComponent() {
		return parseString(dbObject.get("component")+"", "");
	}
	
	public Bugzilla setComponent(String component) {
		dbObject.put("component", component + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}