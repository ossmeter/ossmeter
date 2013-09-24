package org.ossmeter.repository.model.googlecode;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class GoogleIssueTracker extends org.ossmeter.repository.model.BugTrackingSystem {
	
	protected List<GoogleIssue> issues = null;
	
	
	public GoogleIssueTracker() { 
		super();
		dbObject.put("issues", new BasicDBList());
		super.setSuperTypes("org.ossmeter.repository.model.googlecode.BugTrackingSystem");
		URL.setOwningType("org.ossmeter.repository.model.googlecode.GoogleIssueTracker");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public GoogleIssueTracker setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	
	
	public List<GoogleIssue> getIssues() {
		if (issues == null) {
			issues = new PongoList<GoogleIssue>(this, "issues", true);
		}
		return issues;
	}
	
	
}