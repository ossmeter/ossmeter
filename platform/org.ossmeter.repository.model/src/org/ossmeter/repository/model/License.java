package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class License extends Pongo {
	
	
	
	public License() { 
		super();
	}
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public License setUrl(String url) {
		dbObject.put("url", url + "");
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
		dbObject.put("type", type + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}