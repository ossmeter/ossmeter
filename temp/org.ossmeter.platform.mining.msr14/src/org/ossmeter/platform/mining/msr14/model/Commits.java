package org.ossmeter.platform.mining.msr14.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Commits extends Pongo {
	
	protected List<String> commitTimes = null;
	
	
	public Commits() { 
		super();
		dbObject.put("commitTimes", new BasicDBList());
		COUNT.setOwningType("org.ossmeter.platform.mining.msr14.model.Commits");
		TOTALCHANGES.setOwningType("org.ossmeter.platform.mining.msr14.model.Commits");
		ADDITIONS.setOwningType("org.ossmeter.platform.mining.msr14.model.Commits");
		DELETIONS.setOwningType("org.ossmeter.platform.mining.msr14.model.Commits");
		ASAUTHOR.setOwningType("org.ossmeter.platform.mining.msr14.model.Commits");
		ASCOMMITTER.setOwningType("org.ossmeter.platform.mining.msr14.model.Commits");
		TOTALFILES.setOwningType("org.ossmeter.platform.mining.msr14.model.Commits");
		AVERAGEFILESPERCOMMIT.setOwningType("org.ossmeter.platform.mining.msr14.model.Commits");
		COMMITTIMES.setOwningType("org.ossmeter.platform.mining.msr14.model.Commits");
	}
	
	public static NumericalQueryProducer COUNT = new NumericalQueryProducer("count");
	public static NumericalQueryProducer TOTALCHANGES = new NumericalQueryProducer("totalChanges");
	public static NumericalQueryProducer ADDITIONS = new NumericalQueryProducer("additions");
	public static NumericalQueryProducer DELETIONS = new NumericalQueryProducer("deletions");
	public static NumericalQueryProducer ASAUTHOR = new NumericalQueryProducer("asAuthor");
	public static NumericalQueryProducer ASCOMMITTER = new NumericalQueryProducer("asCommitter");
	public static NumericalQueryProducer TOTALFILES = new NumericalQueryProducer("totalFiles");
	public static NumericalQueryProducer AVERAGEFILESPERCOMMIT = new NumericalQueryProducer("averageFilesPerCommit");
	public static ArrayQueryProducer COMMITTIMES = new ArrayQueryProducer("commitTimes");
	
	
	public int getCount() {
		return parseInteger(dbObject.get("count")+"", 0);
	}
	
	public Commits setCount(int count) {
		dbObject.put("count", count);
		notifyChanged();
		return this;
	}
	public int getTotalChanges() {
		return parseInteger(dbObject.get("totalChanges")+"", 0);
	}
	
	public Commits setTotalChanges(int totalChanges) {
		dbObject.put("totalChanges", totalChanges);
		notifyChanged();
		return this;
	}
	public int getAdditions() {
		return parseInteger(dbObject.get("additions")+"", 0);
	}
	
	public Commits setAdditions(int additions) {
		dbObject.put("additions", additions);
		notifyChanged();
		return this;
	}
	public int getDeletions() {
		return parseInteger(dbObject.get("deletions")+"", 0);
	}
	
	public Commits setDeletions(int deletions) {
		dbObject.put("deletions", deletions);
		notifyChanged();
		return this;
	}
	public int getAsAuthor() {
		return parseInteger(dbObject.get("asAuthor")+"", 0);
	}
	
	public Commits setAsAuthor(int asAuthor) {
		dbObject.put("asAuthor", asAuthor);
		notifyChanged();
		return this;
	}
	public int getAsCommitter() {
		return parseInteger(dbObject.get("asCommitter")+"", 0);
	}
	
	public Commits setAsCommitter(int asCommitter) {
		dbObject.put("asCommitter", asCommitter);
		notifyChanged();
		return this;
	}
	public int getTotalFiles() {
		return parseInteger(dbObject.get("totalFiles")+"", 0);
	}
	
	public Commits setTotalFiles(int totalFiles) {
		dbObject.put("totalFiles", totalFiles);
		notifyChanged();
		return this;
	}
	public double getAverageFilesPerCommit() {
		return parseDouble(dbObject.get("averageFilesPerCommit")+"", 0.0d);
	}
	
	public Commits setAverageFilesPerCommit(double averageFilesPerCommit) {
		dbObject.put("averageFilesPerCommit", averageFilesPerCommit);
		notifyChanged();
		return this;
	}
	
	public List<String> getCommitTimes() {
		if (commitTimes == null) {
			commitTimes = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("commitTimes"));
		}
		return commitTimes;
	}
	
	
	
}