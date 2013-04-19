package org.ossmeter.metricprovider.generic.totalloc.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class TLocRepositoryData extends Pongo {
	
	
	
	public TLocRepositoryData() { 
		super();
	}
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public TLocRepositoryData setUrl(String url) {
		dbObject.put("url", url + "");
		notifyChanged();
		return this;
	}
	public String getRepoType() {
		return parseString(dbObject.get("repoType")+"", "");
	}
	
	public TLocRepositoryData setRepoType(String repoType) {
		dbObject.put("repoType", repoType + "");
		notifyChanged();
		return this;
	}
	public String getRevision() {
		return parseString(dbObject.get("revision")+"", "");
	}
	
	public TLocRepositoryData setRevision(String revision) {
		dbObject.put("revision", revision + "");
		notifyChanged();
		return this;
	}
	public long getTotalLines() {
		return parseLong(dbObject.get("totalLines")+"", 0);
	}
	
	public TLocRepositoryData setTotalLines(long totalLines) {
		dbObject.put("totalLines", totalLines + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}