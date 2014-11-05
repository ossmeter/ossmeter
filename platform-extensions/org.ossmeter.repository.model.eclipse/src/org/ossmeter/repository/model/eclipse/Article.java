package org.ossmeter.repository.model.eclipse;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class Article extends Pongo {
	
	
	
	public Article() { 
		super();
		URL.setOwningType("org.ossmeter.repository.model.eclipse.Article");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public Article setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	
	
	
	
}