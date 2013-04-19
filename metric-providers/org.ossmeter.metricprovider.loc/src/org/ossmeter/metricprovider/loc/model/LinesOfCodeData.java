package org.ossmeter.metricprovider.loc.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class LinesOfCodeData extends Pongo {
	
	
	
	public LinesOfCodeData() { 
		super();
	}
	
	public String getFile() {
		return parseString(dbObject.get("file")+"", "");
	}
	
	public LinesOfCodeData setFile(String file) {
		dbObject.put("file", file + "");
		notifyChanged();
		return this;
	}
	public long getLines() {
		return parseLong(dbObject.get("lines")+"", 0);
	}
	
	public LinesOfCodeData setLines(long lines) {
		dbObject.put("lines", lines + "");
		notifyChanged();
		return this;
	}
	public long getRevisionNumber() {
		return parseLong(dbObject.get("revisionNumber")+"", 0);
	}
	
	public LinesOfCodeData setRevisionNumber(long revisionNumber) {
		dbObject.put("revisionNumber", revisionNumber + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}