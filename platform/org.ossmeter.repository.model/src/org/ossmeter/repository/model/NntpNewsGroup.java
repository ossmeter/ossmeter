package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class NntpNewsGroup extends CommunicationChannel {
	
	
	
	public NntpNewsGroup() { 
		super();
	}
	
	public boolean getAuthenticationRequired() {
		return parseBoolean(dbObject.get("authenticationRequired")+"", false);
	}
	
	public NntpNewsGroup setAuthenticationRequired(boolean authenticationRequired) {
		dbObject.put("authenticationRequired", authenticationRequired + "");
		notifyChanged();
		return this;
	}
	public String getUsername() {
		return parseString(dbObject.get("username")+"", "");
	}
	
	public NntpNewsGroup setUsername(String username) {
		dbObject.put("username", username + "");
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public NntpNewsGroup setPassword(String password) {
		dbObject.put("password", password + "");
		notifyChanged();
		return this;
	}
	public int getInterval() {
		return parseInteger(dbObject.get("interval")+"", 0);
	}
	
	public NntpNewsGroup setInterval(int interval) {
		dbObject.put("interval", interval + "");
		notifyChanged();
		return this;
	}
	public int getPort() {
		return parseInteger(dbObject.get("port")+"", 0);
	}
	
	public NntpNewsGroup setPort(int port) {
		dbObject.put("port", port + "");
		notifyChanged();
		return this;
	}
	public String getLastArticleChecked() {
		return parseString(dbObject.get("lastArticleChecked")+"", "");
	}
	
	public NntpNewsGroup setLastArticleChecked(String lastArticleChecked) {
		dbObject.put("lastArticleChecked", lastArticleChecked + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}