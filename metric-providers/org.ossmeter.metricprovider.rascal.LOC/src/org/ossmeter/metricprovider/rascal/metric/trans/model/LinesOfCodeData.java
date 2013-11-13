package org.ossmeter.metricprovider.rascal.metric.trans.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class LinesOfCodeData extends Pongo {
	
	
	
	public LinesOfCodeData() { 
		super();
		FILE.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.LinesOfCodeData");
		TOTALLINES.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.LinesOfCodeData");
		COMMENTEDLINES.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.LinesOfCodeData");
		EMPTYLINES.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.LinesOfCodeData");
		SOURCELINES.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.LinesOfCodeData");
		REVISIONNUMBER.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.LinesOfCodeData");
		DATE.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.LinesOfCodeData");
	}
	
	public static StringQueryProducer FILE = new StringQueryProducer("file"); 
	public static NumericalQueryProducer TOTALLINES = new NumericalQueryProducer("totalLines");
	public static NumericalQueryProducer COMMENTEDLINES = new NumericalQueryProducer("commentedLines");
	public static NumericalQueryProducer EMPTYLINES = new NumericalQueryProducer("emptyLines");
	public static NumericalQueryProducer SOURCELINES = new NumericalQueryProducer("sourceLines");
	public static StringQueryProducer REVISIONNUMBER = new StringQueryProducer("revisionNumber"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	
	
	public String getFile() {
		return parseString(dbObject.get("file")+"", "");
	}
	
	public LinesOfCodeData setFile(String file) {
		dbObject.put("file", file);
		notifyChanged();
		return this;
	}
	public long getTotalLines() {
		return parseLong(dbObject.get("totalLines")+"", 0);
	}
	
	public LinesOfCodeData setTotalLines(long totalLines) {
		dbObject.put("totalLines", totalLines);
		notifyChanged();
		return this;
	}
	public long getCommentedLines() {
		return parseLong(dbObject.get("commentedLines")+"", 0);
	}
	
	public LinesOfCodeData setCommentedLines(long commentedLines) {
		dbObject.put("commentedLines", commentedLines);
		notifyChanged();
		return this;
	}
	public long getEmptyLines() {
		return parseLong(dbObject.get("emptyLines")+"", 0);
	}
	
	public LinesOfCodeData setEmptyLines(long emptyLines) {
		dbObject.put("emptyLines", emptyLines);
		notifyChanged();
		return this;
	}
	public long getSourceLines() {
		return parseLong(dbObject.get("sourceLines")+"", 0);
	}
	
	public LinesOfCodeData setSourceLines(long sourceLines) {
		dbObject.put("sourceLines", sourceLines);
		notifyChanged();
		return this;
	}
	public String getRevisionNumber() {
		return parseString(dbObject.get("revisionNumber")+"", "");
	}
	
	public LinesOfCodeData setRevisionNumber(String revisionNumber) {
		dbObject.put("revisionNumber", revisionNumber);
		notifyChanged();
		return this;
	}
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public LinesOfCodeData setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	
	
	
	
}