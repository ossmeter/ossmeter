package org.ossmeter.repository.model.jira;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;

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
		super.setSuperTypes("org.ossmeter.repository.model.jira.BugTrackingSystem");
		PROJECT.setOwningType("org.ossmeter.repository.model.jira.JiraBugTrackingSystem");
		LOGIN.setOwningType("org.ossmeter.repository.model.jira.JiraBugTrackingSystem");
		PASSWORD.setOwningType("org.ossmeter.repository.model.jira.JiraBugTrackingSystem");
	}
	
	public static StringQueryProducer PROJECT = new StringQueryProducer("project"); 
	public static StringQueryProducer LOGIN = new StringQueryProducer("login"); 
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	
	
	public String getProject() {
		return parseString(dbObject.get("project")+"", "");
	}
	
	public JiraBugTrackingSystem setProject(String project) {
		dbObject.put("project", project);
		notifyChanged();
		return this;
	}
	public String getLogin() {
		return parseString(dbObject.get("login")+"", "");
	}
	
	public JiraBugTrackingSystem setLogin(String login) {
		dbObject.put("login", login);
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public JiraBugTrackingSystem setPassword(String password) {
		dbObject.put("password", password);
		notifyChanged();
		return this;
	}
	
	
	
	
}