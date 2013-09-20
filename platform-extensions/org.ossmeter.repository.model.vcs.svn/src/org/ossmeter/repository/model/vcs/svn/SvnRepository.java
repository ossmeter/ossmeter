package org.ossmeter.repository.model.vcs.svn;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class SvnRepository extends org.ossmeter.repository.model.VcsRepository {
	
	
	
	public SvnRepository() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.vcs.svn.VcsRepository");
		BROWSE.setOwningType("org.ossmeter.repository.model.vcs.svn.SvnRepository");
		USERNAME.setOwningType("org.ossmeter.repository.model.vcs.svn.SvnRepository");
		PASSWORD.setOwningType("org.ossmeter.repository.model.vcs.svn.SvnRepository");
	}
	
	public static StringQueryProducer BROWSE = new StringQueryProducer("browse"); 
	public static StringQueryProducer USERNAME = new StringQueryProducer("username"); 
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	
	
	public String getBrowse() {
		return parseString(dbObject.get("browse")+"", "");
	}
	
	public SvnRepository setBrowse(String browse) {
		dbObject.put("browse", browse);
		notifyChanged();
		return this;
	}
	public String getUsername() {
		return parseString(dbObject.get("username")+"", "");
	}
	
	public SvnRepository setUsername(String username) {
		dbObject.put("username", username);
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public SvnRepository setPassword(String password) {
		dbObject.put("password", password);
		notifyChanged();
		return this;
	}
	
	
	
	
}