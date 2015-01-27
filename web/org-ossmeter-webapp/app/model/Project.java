package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Project extends Pongo {
	
	protected List<Tag> tags = null;
	
	
	public Project() { 
		super();
		dbObject.put("tags", new BasicDBList());
		ID.setOwningType("model.Project");
		NAME.setOwningType("model.Project");
	}
	
	public static StringQueryProducer ID = new StringQueryProducer("id"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer ANALYSED = new StringQueryProducer("analysed");

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
	
	
	public List<Tag> getTags() {
		if (tags == null) {
			tags = new PongoList<Tag>(this, "tags", true);
		}
		return tags;
	}
	
	public boolean getAnalysed() {
		return parseBoolean(dbObject.get("analysed")+"", false);
	}

	public Project setAnalysed(boolean analysed) {
		dbObject.put("analysed", analysed);
		notifyChanged();
		return this;
	}
	
}