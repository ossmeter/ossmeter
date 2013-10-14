package org.ossmeter.repository.model.cc.nntp;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NntpNewsGroup extends org.ossmeter.repository.model.CommunicationChannel {
	
	
	
	public NntpNewsGroup() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.cc.nntp.CommunicationChannel");
		AUTHENTICATIONREQUIRED.setOwningType("org.ossmeter.repository.model.cc.nntp.NntpNewsGroup");
		USERNAME.setOwningType("org.ossmeter.repository.model.cc.nntp.NntpNewsGroup");
		PASSWORD.setOwningType("org.ossmeter.repository.model.cc.nntp.NntpNewsGroup");
		PORT.setOwningType("org.ossmeter.repository.model.cc.nntp.NntpNewsGroup");
		DESCRIPTION.setOwningType("org.ossmeter.repository.model.cc.nntp.NntpNewsGroup");
		NAME.setOwningType("org.ossmeter.repository.model.cc.nntp.NntpNewsGroup");
	}
	
	public static StringQueryProducer AUTHENTICATIONREQUIRED = new StringQueryProducer("authenticationRequired"); 
	public static StringQueryProducer USERNAME = new StringQueryProducer("username"); 
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	public static NumericalQueryProducer PORT = new NumericalQueryProducer("port");
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	
	
	public boolean getAuthenticationRequired() {
		return parseBoolean(dbObject.get("authenticationRequired")+"", false);
	}
	
	public NntpNewsGroup setAuthenticationRequired(boolean authenticationRequired) {
		dbObject.put("authenticationRequired", authenticationRequired);
		notifyChanged();
		return this;
	}
	public String getUsername() {
		return parseString(dbObject.get("username")+"", "");
	}
	
	public NntpNewsGroup setUsername(String username) {
		dbObject.put("username", username);
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public NntpNewsGroup setPassword(String password) {
		dbObject.put("password", password);
		notifyChanged();
		return this;
	}
	public int getPort() {
		return parseInteger(dbObject.get("port")+"", 0);
	}
	
	public NntpNewsGroup setPort(int port) {
		dbObject.put("port", port);
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public NntpNewsGroup setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public NntpNewsGroup setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	
	
	
	
}