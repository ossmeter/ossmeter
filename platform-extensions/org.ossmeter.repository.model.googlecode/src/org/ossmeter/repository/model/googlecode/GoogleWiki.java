package org.ossmeter.repository.model.googlecode;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class GoogleWiki extends Pongo {
	
	
	
	public GoogleWiki() { 
		super();
	}
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public GoogleWiki setUrl(String url) {
		dbObject.put("url", url + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}