package org.ossmeter.repository.model.eclipseforge;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class Article extends Pongo {
	
	
	
	public Article() { 
		super();
	}
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public Article setUrl(String url) {
		dbObject.put("url", url + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}