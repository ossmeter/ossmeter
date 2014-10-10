package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Project extends Pongo {
	
	
	
	public Project() { 
		super();
		ID.setOwningType("model.Project");
		NAME.setOwningType("model.Project");
	}
	
	public static StringQueryProducer ID = new StringQueryProducer("id"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	
	
	public String getId() {
		return parseString(dbObject.get("id")+"", "");
	}
	
	public Project setId(String id) {
		dbObject.put("id", id);
		notifyChanged();
		return this;
	}
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public Project setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	
	
	
	
}