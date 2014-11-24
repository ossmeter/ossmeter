package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class License extends Pongo {
	
	
	
	public License() { 
		super();
		URL.setOwningType("com.googlecode.pongo.tests.ossmeter.model.License");
		TYPE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.License");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer TYPE = new StringQueryProducer("type"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public License setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public LicenseType getType() {
		LicenseType type = null;
		try {
			type = LicenseType.valueOf(dbObject.get("type")+"");
		}
		catch (Exception ex) {}
		return type;
	}
	
	public License setType(LicenseType type) {
		dbObject.put("type", type.toString());
		notifyChanged();
		return this;
	}
	
	
	
	
}