package org.ossmeter.repository.model.jira;

import com.mongodb.*;

import java.util.*;

import com.googlecode.pongo.runtime.*;

// protected region custom-imports on begin
// protected region custom-imports end

public class JiraBugTrackingSystem extends org.ossmeter.repository.model.BugTrackingSystem {
	
	
	// protected region custom-fields-and-methods on begin
    @Override
    public String getBugTrackerType() {
        return "jira";
    }

    @Override
    public String getInstanceId() {
        return getUrl() + ':' + getProject();
    }
    
	// protected region custom-fields-and-methods end
	
	public JiraBugTrackingSystem() { 
		super();
	}
	
	public String getProject() {
		return parseString(dbObject.get("project")+"", "");
	}
	
	public JiraBugTrackingSystem setProject(String project) {
		dbObject.put("project", project + "");
		notifyChanged();
		return this;
	}
	public String getUser() {
		return parseString(dbObject.get("user")+"", "");
	}
	
	public JiraBugTrackingSystem setUser(String user) {
		dbObject.put("user", user + "");
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public JiraBugTrackingSystem setPassword(String password) {
		dbObject.put("password", password + "");
		notifyChanged();
		return this;
	}

   
	
	
	
}