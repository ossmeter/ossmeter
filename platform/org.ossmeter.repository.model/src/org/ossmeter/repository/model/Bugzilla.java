package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Bugzilla extends BugTrackingSystem {
	
	
	
	public Bugzilla() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.BugTrackingSystem");
		URL.setOwningType("org.ossmeter.repository.model.Bugzilla");
		PRODUCT.setOwningType("org.ossmeter.repository.model.Bugzilla");
		COMPONENT.setOwningType("org.ossmeter.repository.model.Bugzilla");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer PRODUCT = new StringQueryProducer("product"); 
	public static StringQueryProducer COMPONENT = new StringQueryProducer("component"); 
	
	
	public String getProduct() {
		return parseString(dbObject.get("product")+"", "");
	}
	
	public Bugzilla setProduct(String product) {
		dbObject.put("product", product);
		notifyChanged();
		return this;
	}
	public String getComponent() {
		return parseString(dbObject.get("component")+"", "");
	}
	
	public Bugzilla setComponent(String component) {
		dbObject.put("component", component);
		notifyChanged();
		return this;
	}
	
	
	
	
}