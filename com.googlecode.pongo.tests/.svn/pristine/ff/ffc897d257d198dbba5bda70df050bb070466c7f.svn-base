package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class GITContent extends Pongo {
	
	
	
	public GITContent() { 
		super();
		TYPE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITContent");
		ENVODING.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITContent");
		SIZE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITContent");
		NAME.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITContent");
		PATH.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITContent");
		SHA.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITContent");
	}
	
	public static StringQueryProducer TYPE = new StringQueryProducer("type"); 
	public static StringQueryProducer ENVODING = new StringQueryProducer("envoding"); 
	public static NumericalQueryProducer SIZE = new NumericalQueryProducer("size");
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer PATH = new StringQueryProducer("path"); 
	public static StringQueryProducer SHA = new StringQueryProducer("sha"); 
	
	
	public String getType() {
		return parseString(dbObject.get("type")+"", "");
	}
	
	public GITContent setType(String type) {
		dbObject.put("type", type);
		notifyChanged();
		return this;
	}
	public String getEnvoding() {
		return parseString(dbObject.get("envoding")+"", "");
	}
	
	public GITContent setEnvoding(String envoding) {
		dbObject.put("envoding", envoding);
		notifyChanged();
		return this;
	}
	public int getSize() {
		return parseInteger(dbObject.get("size")+"", 0);
	}
	
	public GITContent setSize(int size) {
		dbObject.put("size", size);
		notifyChanged();
		return this;
	}
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public GITContent setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public String getPath() {
		return parseString(dbObject.get("path")+"", "");
	}
	
	public GITContent setPath(String path) {
		dbObject.put("path", path);
		notifyChanged();
		return this;
	}
	public String getSha() {
		return parseString(dbObject.get("sha")+"", "");
	}
	
	public GITContent setSha(String sha) {
		dbObject.put("sha", sha);
		notifyChanged();
		return this;
	}
	
	
	
	
}