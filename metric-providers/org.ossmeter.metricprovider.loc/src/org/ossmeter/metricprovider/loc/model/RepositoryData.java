package org.ossmeter.metricprovider.loc.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class RepositoryData extends Pongo {
	
	protected List<LinesOfCodeData> linesOfCode = null;
	
	
	public RepositoryData() { 
		super();
		dbObject.put("linesOfCode", new BasicDBList());
		URL.setOwningType("org.ossmeter.metricprovider.loc.model.RepositoryData");
		REPOTYPE.setOwningType("org.ossmeter.metricprovider.loc.model.RepositoryData");
		REVISION.setOwningType("org.ossmeter.metricprovider.loc.model.RepositoryData");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer REPOTYPE = new StringQueryProducer("repoType"); 
	public static StringQueryProducer REVISION = new StringQueryProducer("revision"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public RepositoryData setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public String getRepoType() {
		return parseString(dbObject.get("repoType")+"", "");
	}
	
	public RepositoryData setRepoType(String repoType) {
		dbObject.put("repoType", repoType);
		notifyChanged();
		return this;
	}
	public String getRevision() {
		return parseString(dbObject.get("revision")+"", "");
	}
	
	public RepositoryData setRevision(String revision) {
		dbObject.put("revision", revision);
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