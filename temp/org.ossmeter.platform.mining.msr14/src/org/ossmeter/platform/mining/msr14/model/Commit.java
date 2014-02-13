package org.ossmeter.platform.mining.msr14.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Commit extends Countable {
	
	
	
	public Commit() { 
		super();
		super.setSuperTypes("org.ossmeter.platform.mining.msr14.model.Countable");
		NUMBER.setOwningType("org.ossmeter.platform.mining.msr14.model.Commit");
		FREQUENCY.setOwningType("org.ossmeter.platform.mining.msr14.model.Commit");
		TOTAL.setOwningType("org.ossmeter.platform.mining.msr14.model.Commit");
		ADDITIONS.setOwningType("org.ossmeter.platform.mining.msr14.model.Commit");
		DELETIONS.setOwningType("org.ossmeter.platform.mining.msr14.model.Commit");
		TOTALFILES.setOwningType("org.ossmeter.platform.mining.msr14.model.Commit");
		AVERAGEFILESPERCOMMIT.setOwningType("org.ossmeter.platform.mining.msr14.model.Commit");
	}
	
	public static NumericalQueryProducer NUMBER = new NumericalQueryProducer("number");
	public static NumericalQueryProducer FREQUENCY = new NumericalQueryProducer("frequency");
	public static NumericalQueryProducer TOTAL = new NumericalQueryProducer("total");
	public static NumericalQueryProducer ADDITIONS = new NumericalQueryProducer("additions");
	public static NumericalQueryProducer DELETIONS = new NumericalQueryProducer("deletions");
	public static NumericalQueryProducer TOTALFILES = new NumericalQueryProducer("totalFiles");
	public static NumericalQueryProducer AVERAGEFILESPERCOMMIT = new NumericalQueryProducer("averageFilesPerCommit");
	
	
	public int getTotal() {
		return parseInteger(dbObject.get("total")+"", 0);
	}
	
	public Commit setTotal(int total) {
		dbObject.put("total", total);
		notifyChanged();
		return this;
	}
	public int getAdditions() {
		return parseInteger(dbObject.get("additions")+"", 0);
	}
	
	public Commit setAdditions(int additions) {
		dbObject.put("additions", additions);
		notifyChanged();
		return this;
	}
	public int getDeletions() {
		return parseInteger(dbObject.get("deletions")+"", 0);
	}
	
	public Commit setDeletions(int deletions) {
		dbObject.put("deletions", deletions);
		notifyChanged();
		return this;
	}
	public int getTotalFiles() {
		return parseInteger(dbObject.get("totalFiles")+"", 0);
	}
	
	public Commit setTotalFiles(int totalFiles) {
		dbObject.put("totalFiles", totalFiles);
		notifyChanged();
		return this;
	}
	public double getAverageFilesPerCommit() {
		return parseDouble(dbObject.get("averageFilesPerCommit")+"", 0.0d);
	}
	
	public Commit setAverageFilesPerCommit(double averageFilesPerCommit) {
		dbObject.put("averageFilesPerCommit", averageFilesPerCommit);
		notifyChanged();
		return this;
	}
	
	
	
	
}