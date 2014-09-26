package org.ossmeter.repository.model.redmine;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class RedmineRepositoryInfo extends Pongo {
	
	
	
	public RedmineRepositoryInfo() { 
		super();
		USERNAME.setOwningType("org.ossmeter.repository.model.redmine.RedmineRepositoryInfo");
		PASSWORD.setOwningType("org.ossmeter.repository.model.redmine.RedmineRepositoryInfo");
		TOKEN.setOwningType("org.ossmeter.repository.model.redmine.RedmineRepositoryInfo");
		BASEREPO.setOwningType("org.ossmeter.repository.model.redmine.RedmineRepositoryInfo");
	}
	
	public static StringQueryProducer USERNAME = new StringQueryProducer("username"); 
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	public static StringQueryProducer TOKEN = new StringQueryProducer("token"); 
	public static StringQueryProducer BASEREPO = new StringQueryProducer("baseRepo"); 
	
	
	public String getUsername() {
		return parseString(dbObject.get("username")+"", "");
	}
	
	public RedmineRepositoryInfo setUsername(String username) {
		dbObject.put("username", username);
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public RedmineRepositoryInfo setPassword(String password) {
		dbObject.put("password", password);
		notifyChanged();
		return this;
	}
	public String getToken() {
		return parseString(dbObject.get("token")+"", "");
	}
	
	public RedmineRepositoryInfo setToken(String token) {
		dbObject.put("token", token);
		notifyChanged();
		return this;
	}
	public String getBaseRepo() {
		return parseString(dbObject.get("baseRepo")+"", "");
	}
	
	public RedmineRepositoryInfo setBaseRepo(String baseRepo) {
		dbObject.put("baseRepo", baseRepo);
		notifyChanged();
		return this;
	}
	
	
	
	
}