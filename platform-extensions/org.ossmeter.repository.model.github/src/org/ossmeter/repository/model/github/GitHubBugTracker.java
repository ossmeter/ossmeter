package org.ossmeter.repository.model.github;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class GitHubBugTracker extends org.ossmeter.repository.model.BugTrackingSystem {
	
	protected List<GitHubIssue> issues = null;
	
	
	public GitHubBugTracker() { 
		super();
		dbObject.put("issues", new BasicDBList());
		super.setSuperTypes("org.ossmeter.repository.model.github.BugTrackingSystem");
	}
	
	
	
	
	
	public List<GitHubIssue> getIssues() {
		if (issues == null) {
			issues = new PongoList<GitHubIssue>(this, "issues", true);
		}
		return issues;
	}
	
	
}