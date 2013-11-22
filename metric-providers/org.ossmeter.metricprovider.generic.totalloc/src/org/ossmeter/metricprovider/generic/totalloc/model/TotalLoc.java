package org.ossmeter.metricprovider.generic.totalloc.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class TotalLoc extends Pongo {
	
	
	
	public TotalLoc() { 
		super();
		URL.setOwningType("org.ossmeter.metricprovider.generic.totalloc.model.TotalLoc");
		REPOTYPE.setOwningType("org.ossmeter.metricprovider.generic.totalloc.model.TotalLoc");
		REVISION.setOwningType("org.ossmeter.metricprovider.generic.totalloc.model.TotalLoc");
		TOTALLINES.setOwningType("org.ossmeter.metricprovider.generic.totalloc.model.TotalLoc");
		SOURCELINES.setOwningType("org.ossmeter.metricprovider.generic.totalloc.model.TotalLoc");
		COMMENTEDLINES.setOwningType("org.ossmeter.metricprovider.generic.totalloc.model.TotalLoc");
		EMPTYLINES.setOwningType("org.ossmeter.metricprovider.generic.totalloc.model.TotalLoc");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer REPOTYPE = new StringQueryProducer("repoType"); 
	public static StringQueryProducer REVISION = new StringQueryProducer("revision"); 
	public static NumericalQueryProducer TOTALLINES = new NumericalQueryProducer("totalLines");
	public static NumericalQueryProducer SOURCELINES = new NumericalQueryProducer("sourceLines");
	public static NumericalQueryProducer COMMENTEDLINES = new NumericalQueryProducer("commentedLines");
	public static NumericalQueryProducer EMPTYLINES = new NumericalQueryProducer("emptyLines");
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public TotalLoc setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public String getRepoType() {
		return parseString(dbObject.get("repoType")+"", "");
	}
	
	public TotalLoc setRepoType(String repoType) {
		dbObject.put("repoType", repoType);
		notifyChanged();
		return this;
	}
	public String getRevision() {
		return parseString(dbObject.get("revision")+"", "");
	}
	
	public TotalLoc setRevision(String revision) {
		dbObject.put("revision", revision);
		notifyChanged();
		return this;
	}
	public long getTotalLines() {
		return parseLong(dbObject.get("totalLines")+"", 0);
	}
	
	public TotalLoc setTotalLines(long totalLines) {
		dbObject.put("totalLines", totalLines);
		notifyChanged();
		return this;
	}
	public long getSourceLines() {
		return parseLong(dbObject.get("sourceLines")+"", 0);
	}
	
	public TotalLoc setSourceLines(long sourceLines) {
		dbObject.put("sourceLines", sourceLines);
		notifyChanged();
		return this;
	}
	public long getCommentedLines() {
		return parseLong(dbObject.get("commentedLines")+"", 0);
	}
	
	public TotalLoc setCommentedLines(long commentedLines) {
		dbObject.put("commentedLines", commentedLines);
		notifyChanged();
		return this;
	}
	public long getEmptyLines() {
		return parseLong(dbObject.get("emptyLines")+"", 0);
	}
	
	public TotalLoc setEmptyLines(long emptyLines) {
		dbObject.put("emptyLines", emptyLines);
		notifyChanged();
		return this;
	}
	
	
	
	
}