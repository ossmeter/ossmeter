package org.ossmeter.metricprovider.loc.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class LinesOfCodeData extends Pongo {
	
	
	
	public LinesOfCodeData() { 
		super();
		FILE.setOwningType("org.ossmeter.metricprovider.loc.model.LinesOfCodeData");
		LINES.setOwningType("org.ossmeter.metricprovider.loc.model.LinesOfCodeData");
		REVISIONNUMBER.setOwningType("org.ossmeter.metricprovider.loc.model.LinesOfCodeData");
		DATE.setOwningType("org.ossmeter.metricprovider.loc.model.LinesOfCodeData");
	}
	
	public static StringQueryProducer FILE = new StringQueryProducer("file"); 
	public static NumericalQueryProducer LINES = new NumericalQueryProducer("lines");
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
	public long getLines() {
		return parseLong(dbObject.get("lines")+"", 0);
	}
	
	public LinesOfCodeData setLines(long lines) {
		dbObject.put("lines", lines);
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