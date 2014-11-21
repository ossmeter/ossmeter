package com.googlecode.pongo.tests.svnloc.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class SvnRepositoryMetric extends Pongo {
	
	protected List<LinesOfCodeMetric> linesOfCodeMetrics = null;
	
	
	public SvnRepositoryMetric() { 
		super();
		dbObject.put("linesOfCodeMetrics", new BasicDBList());
	}
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public SvnRepositoryMetric setUrl(String url) {
		dbObject.put("url", url + "");
		notifyChanged();
		return this;
	}
	public long getRevisionNumber() {
		return parseLong(dbObject.get("revisionNumber")+"", 0);
	}
	
	public SvnRepositoryMetric setRevisionNumber(long revisionNumber) {
		dbObject.put("revisionNumber", revisionNumber + "");
		notifyChanged();
		return this;
	}
	
	
	public List<LinesOfCodeMetric> getLinesOfCodeMetrics() {
		if (linesOfCodeMetrics == null) {
			linesOfCodeMetrics = new PongoList<LinesOfCodeMetric>(this, "linesOfCodeMetrics", false);
		}
		return linesOfCodeMetrics;
	}
	
	
}