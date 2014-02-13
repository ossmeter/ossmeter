package org.ossmeter.platform.mining.msr14.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Artefact extends Pongo {
	
	
	
	public Artefact() { 
		super();
		EXTENSION.setOwningType("org.ossmeter.platform.mining.msr14.model.Artefact");
		NUMBEROFPROJECTS.setOwningType("org.ossmeter.platform.mining.msr14.model.Artefact");
		COUNT.setOwningType("org.ossmeter.platform.mining.msr14.model.Artefact");
	}
	
	public static StringQueryProducer EXTENSION = new StringQueryProducer("extension"); 
	public static NumericalQueryProducer NUMBEROFPROJECTS = new NumericalQueryProducer("numberOfProjects");
	public static NumericalQueryProducer COUNT = new NumericalQueryProducer("count");
	
	
	public String getExtension() {
		return parseString(dbObject.get("extension")+"", "");
	}
	
	public Artefact setExtension(String extension) {
		dbObject.put("extension", extension);
		notifyChanged();
		return this;
	}
	public int getNumberOfProjects() {
		return parseInteger(dbObject.get("numberOfProjects")+"", 0);
	}
	
	public Artefact setNumberOfProjects(int numberOfProjects) {
		dbObject.put("numberOfProjects", numberOfProjects);
		notifyChanged();
		return this;
	}
	public int getCount() {
		return parseInteger(dbObject.get("count")+"", 0);
	}
	
	public Artefact setCount(int count) {
		dbObject.put("count", count);
		notifyChanged();
		return this;
	}
	
	
	
	
}