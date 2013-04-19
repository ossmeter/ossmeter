package org.ossmeter.repository.model.googlecode;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class GoogleIssueTracker extends org.ossmeter.repository.model.BugTrackingSystem {
	
	protected List<GoogleIssue> issues = null;
	
	
	public GoogleIssueTracker() { 
		super();
		dbObject.put("issues", new BasicDBList());
	}
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public GoogleIssueTracker setUrl(String url) {
		dbObject.put("url", url + "");
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