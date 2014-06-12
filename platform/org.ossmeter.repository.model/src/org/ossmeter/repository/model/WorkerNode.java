package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class WorkerNode extends Pongo {
	
	protected List<String> currentLoad = null;
	
	
	public WorkerNode() { 
		super();
		dbObject.put("currentLoad", new BasicDBList());
		IDENTIFIER.setOwningType("org.ossmeter.repository.model.WorkerNode");
		CURRENTLOAD.setOwningType("org.ossmeter.repository.model.WorkerNode");
	}
	
	public static StringQueryProducer IDENTIFIER = new StringQueryProducer("identifier"); 
	public static ArrayQueryProducer CURRENTLOAD = new ArrayQueryProducer("currentLoad");
	
	
	public String getIdentifier() {
		return parseString(dbObject.get("identifier")+"", "");
	}
	
	public WorkerNode setIdentifier(String identifier) {
		dbObject.put("identifier", identifier);
		notifyChanged();
		return this;
	}
	
	public List<String> getCurrentLoad() {
		if (currentLoad == null) {
			currentLoad = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("currentLoad"));
		}
		return currentLoad;
	}
	
	
	
}