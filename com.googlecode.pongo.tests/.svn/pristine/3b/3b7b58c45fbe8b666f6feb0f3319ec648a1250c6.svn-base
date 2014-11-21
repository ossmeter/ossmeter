package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class GoogleDownload extends Pongo {
	
	protected List<GoogleLabel> labels = null;
	
	
	public GoogleDownload() { 
		super();
		dbObject.put("labels", new BasicDBList());
		STARRED.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GoogleDownload");
		FILENAME.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GoogleDownload");
		UPLOADED_AT.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GoogleDownload");
		UPDATED_AT.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GoogleDownload");
		SIZE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GoogleDownload");
		DOWNLOADCOUNTS.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GoogleDownload");
	}
	
	public static StringQueryProducer STARRED = new StringQueryProducer("starred"); 
	public static StringQueryProducer FILENAME = new StringQueryProducer("fileName"); 
	public static StringQueryProducer UPLOADED_AT = new StringQueryProducer("uploaded_at"); 
	public static StringQueryProducer UPDATED_AT = new StringQueryProducer("updated_at"); 
	public static NumericalQueryProducer SIZE = new NumericalQueryProducer("size");
	public static NumericalQueryProducer DOWNLOADCOUNTS = new NumericalQueryProducer("downloadCounts");
	
	
	public boolean getStarred() {
		return parseBoolean(dbObject.get("starred")+"", false);
	}
	
	public GoogleDownload setStarred(boolean starred) {
		dbObject.put("starred", starred);
		notifyChanged();
		return this;
	}
	public String getFileName() {
		return parseString(dbObject.get("fileName")+"", "");
	}
	
	public GoogleDownload setFileName(String fileName) {
		dbObject.put("fileName", fileName);
		notifyChanged();
		return this;
	}
	public String getUploaded_at() {
		return parseString(dbObject.get("uploaded_at")+"", "");
	}
	
	public GoogleDownload setUploaded_at(String uploaded_at) {
		dbObject.put("uploaded_at", uploaded_at);
		notifyChanged();
		return this;
	}
	public String getUpdated_at() {
		return parseString(dbObject.get("updated_at")+"", "");
	}
	
	public GoogleDownload setUpdated_at(String updated_at) {
		dbObject.put("updated_at", updated_at);
		notifyChanged();
		return this;
	}
	public int getSize() {
		return parseInteger(dbObject.get("size")+"", 0);
	}
	
	public GoogleDownload setSize(int size) {
		dbObject.put("size", size);
		notifyChanged();
		return this;
	}
	public int getDownloadCounts() {
		return parseInteger(dbObject.get("downloadCounts")+"", 0);
	}
	
	public GoogleDownload setDownloadCounts(int downloadCounts) {
		dbObject.put("downloadCounts", downloadCounts);
		notifyChanged();
		return this;
	}
	
	
	public List<GoogleLabel> getLabels() {
		if (labels == null) {
			labels = new PongoList<GoogleLabel>(this, "labels", true);
		}
		return labels;
	}
	
	
}