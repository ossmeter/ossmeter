package com.googlecode.pongo.tests.svnloc.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class LinesOfCodeMetric extends Pongo {
	
	protected RevisionNumber revisionNumber = null;
	
	
	public LinesOfCodeMetric() { 
		super();
	}
	
	public String getFile() {
		return parseString(dbObject.get("file")+"", "");
	}
	
	public LinesOfCodeMetric setFile(String file) {
		dbObject.put("file", file + "");
		notifyChanged();
		return this;
	}
	public int getLines() {
		return parseInteger(dbObject.get("lines")+"", 0);
	}
	
	public LinesOfCodeMetric setLines(int lines) {
		dbObject.put("lines", lines + "");
		notifyChanged();
		return this;
	}
	
	
	
	
	public RevisionNumber getRevisionNumber() {
		if (revisionNumber == null && dbObject.containsField("revisionNumber")) {
			revisionNumber = (RevisionNumber) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("revisionNumber"));
		}
		return revisionNumber;
	}
	
	public LinesOfCodeMetric setRevisionNumber(RevisionNumber revisionNumber) {
		if (this.revisionNumber != revisionNumber) {
			if (revisionNumber == null) {
				dbObject.removeField("revisionNumber");
			}
			else {
				dbObject.put("revisionNumber", revisionNumber.getDbObject());
			}
			this.revisionNumber = revisionNumber;
			notifyChanged();
		}
		return this;
	}
}