package org.ossmeter.repository.model.redmine;

import com.mongodb.*;

import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;

//protected region custom-imports on begin
//protected region custom-imports end


public class RedmineBugIssueTracker extends org.ossmeter.repository.model.BugTrackingSystem {
	
	protected List<RedmineIssue> issues = null;
	
	// protected region custom-fields-and-methods on begin
    @Override
    public String getBugTrackerType() {
        return "redmine";
    }

    @Override
    public String getInstanceId() {
        return "TODO";
    }
    // protected region custom-fields-and-methods end
	
	public RedmineBugIssueTracker() { 
		super();
		dbObject.put("issues", new BasicDBList());
		super.setSuperTypes("org.ossmeter.repository.model.redmine.BugTrackingSystem");
		NAME.setOwningType("org.ossmeter.repository.model.redmine.RedmineBugIssueTracker");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public RedmineBugIssueTracker setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	
	
	public List<RedmineIssue> getIssues() {
		if (issues == null) {
			issues = new PongoList<RedmineIssue>(this, "issues", true);
		}
		return issues;
	}
	
	
}