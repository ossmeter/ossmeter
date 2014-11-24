package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class GITDownload extends Pongo {
	
	
	
	public GITDownload() { 
		super();
		URL.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITDownload");
		HTML_URL.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITDownload");
		NAME.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITDownload");
		DESCRIPTION.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITDownload");
		SIZE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITDownload");
		DOWNLOAD_COUNT.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITDownload");
		CONTENT_TYPE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITDownload");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer HTML_URL = new StringQueryProducer("html_url"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static NumericalQueryProducer SIZE = new NumericalQueryProducer("size");
	public static NumericalQueryProducer DOWNLOAD_COUNT = new NumericalQueryProducer("download_count");
	public static StringQueryProducer CONTENT_TYPE = new StringQueryProducer("content_type"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public GITDownload setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public String getHtml_url() {
		return parseString(dbObject.get("html_url")+"", "");
	}
	
	public GITDownload setHtml_url(String html_url) {
		dbObject.put("html_url", html_url);
		notifyChanged();
		return this;
	}
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public GITDownload setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public GITDownload setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public int getSize() {
		return parseInteger(dbObject.get("size")+"", 0);
	}
	
	public GITDownload setSize(int size) {
		dbObject.put("size", size);
		notifyChanged();
		return this;
	}
	public int getDownload_count() {
		return parseInteger(dbObject.get("download_count")+"", 0);
	}
	
	public GITDownload setDownload_count(int download_count) {
		dbObject.put("download_count", download_count);
		notifyChanged();
		return this;
	}
	public String getContent_type() {
		return parseString(dbObject.get("content_type")+"", "");
	}
	
	public GITDownload setContent_type(String content_type) {
		dbObject.put("content_type", content_type);
		notifyChanged();
		return this;
	}
	
	
	
	
}