package org.ossmeter.platform.communicationchannel.nntp.local.model;

import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsgroupData extends Pongo {
	
	
	
	public NewsgroupData() { 
		super();
		NAME.setOwningType("org.ossmeter.platform.communicationchannel.nntp.local.model.NewsgroupData");
		URL.setOwningType("org.ossmeter.platform.communicationchannel.nntp.local.model.NewsgroupData");
		AUTHENTICATIONREQUIRED.setOwningType("org.ossmeter.platform.communicationchannel.nntp.local.model.NewsgroupData");
		USERNAME.setOwningType("org.ossmeter.platform.communicationchannel.nntp.local.model.NewsgroupData");
		PASSWORD.setOwningType("org.ossmeter.platform.communicationchannel.nntp.local.model.NewsgroupData");
		PORT.setOwningType("org.ossmeter.platform.communicationchannel.nntp.local.model.NewsgroupData");
		INTERVAL.setOwningType("org.ossmeter.platform.communicationchannel.nntp.local.model.NewsgroupData");
		LASTARTICLECHECKED.setOwningType("org.ossmeter.platform.communicationchannel.nntp.local.model.NewsgroupData");
		FIRSTARTICLE.setOwningType("org.ossmeter.platform.communicationchannel.nntp.local.model.NewsgroupData");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer AUTHENTICATIONREQUIRED = new StringQueryProducer("authenticationRequired"); 
	public static StringQueryProducer USERNAME = new StringQueryProducer("username"); 
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	public static NumericalQueryProducer PORT = new NumericalQueryProducer("port");
	public static NumericalQueryProducer INTERVAL = new NumericalQueryProducer("interval");
	public static StringQueryProducer LASTARTICLECHECKED = new StringQueryProducer("lastArticleChecked"); 
	public static StringQueryProducer FIRSTARTICLE = new StringQueryProducer("firstArticle"); 
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public NewsgroupData setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public NewsgroupData setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public boolean getAuthenticationRequired() {
		return parseBoolean(dbObject.get("authenticationRequired")+"", false);
	}
	
	public NewsgroupData setAuthenticationRequired(boolean authenticationRequired) {
		dbObject.put("authenticationRequired", authenticationRequired);
		notifyChanged();
		return this;
	}
	public String getUsername() {
		return parseString(dbObject.get("username")+"", "");
	}
	
	public NewsgroupData setUsername(String username) {
		dbObject.put("username", username);
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public NewsgroupData setPassword(String password) {
		dbObject.put("password", password);
		notifyChanged();
		return this;
	}
	public int getPort() {
		return parseInteger(dbObject.get("port")+"", 0);
	}
	
	public NewsgroupData setPort(int port) {
		dbObject.put("port", port);
		notifyChanged();
		return this;
	}
	public int getInterval() {
		return parseInteger(dbObject.get("interval")+"", 0);
	}
	
	public NewsgroupData setInterval(int interval) {
		dbObject.put("interval", interval);
		notifyChanged();
		return this;
	}
	public String getLastArticleChecked() {
		return parseString(dbObject.get("lastArticleChecked")+"", "");
	}
	
	public NewsgroupData setLastArticleChecked(String lastArticleChecked) {
		dbObject.put("lastArticleChecked", lastArticleChecked);
		notifyChanged();
		return this;
	}
	public String getFirstArticle() {
		return parseString(dbObject.get("firstArticle")+"", "");
	}
	
	public NewsgroupData setFirstArticle(String firstArticle) {
		dbObject.put("firstArticle", firstArticle);
		notifyChanged();
		return this;
	}
	
	
	
	
}