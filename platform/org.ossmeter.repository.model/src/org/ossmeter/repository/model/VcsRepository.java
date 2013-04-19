package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public abstract class VcsRepository extends Pongo {
	
	
	
	public VcsRepository() { 
		super();
	}
	
	public String getCreated_at() {
		return parseString(dbObject.get("created_at")+"", "");
	}
	
	public VcsRepository setCreated_at(String created_at) {
		dbObject.put("created_at", created_at + "");
		notifyChanged();
		return this;
	}
	public String getUpdated_at() {
		return parseString(dbObject.get("updated_at")+"", "");
	}
	
	public VcsRepository setUpdated_at(String updated_at) {
		dbObject.put("updated_at", updated_at + "");
		notifyChanged();
		return this;
	}
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public VcsRepository setUrl(String url) {
		dbObject.put("url", url + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}