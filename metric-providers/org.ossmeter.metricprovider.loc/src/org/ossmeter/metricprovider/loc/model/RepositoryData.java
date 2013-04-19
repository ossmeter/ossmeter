package org.ossmeter.metricprovider.loc.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class RepositoryData extends Pongo {
	
	protected List<LinesOfCodeData> linesOfCode = null;
	
	
	public RepositoryData() { 
		super();
		dbObject.put("linesOfCode", new BasicDBList());
	}
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public RepositoryData setUrl(String url) {
		dbObject.put("url", url + "");
		notifyChanged();
		return this;
	}
	public String getRepoType() {
		return parseString(dbObject.get("repoType")+"", "");
	}
	
	public RepositoryData setRepoType(String repoType) {
		dbObject.put("repoType", repoType + "");
		notifyChanged();
		return this;
	}
	public String getRevision() {
		return parseString(dbObject.get("revision")+"", "");
	}
	
	public RepositoryData setRevision(String revision) {
		dbObject.put("revision", revision + "");
		notifyChanged();
		return this;
	}
	
	
	public List<LinesOfCodeData> getLinesOfCode() {
		if (linesOfCode == null) {
			linesOfCode = new PongoList<LinesOfCodeData>(this, "linesOfCode", false);
		}
		return linesOfCode;
	}
	
	
}