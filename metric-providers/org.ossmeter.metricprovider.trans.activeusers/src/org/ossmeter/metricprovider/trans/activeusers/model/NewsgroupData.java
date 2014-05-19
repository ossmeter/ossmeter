package org.ossmeter.metricprovider.trans.activeusers.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class NewsgroupData extends Pongo {
	
	
	
	public NewsgroupData() { 
		super();
		URL_NAME.setOwningType("org.ossmeter.metricprovider.trans.activeusers.model.NewsgroupData");
		ACTIVEUSERS.setOwningType("org.ossmeter.metricprovider.trans.activeusers.model.NewsgroupData");
		INACTIVEUSERS.setOwningType("org.ossmeter.metricprovider.trans.activeusers.model.NewsgroupData");
		PREVIOUSUSERS.setOwningType("org.ossmeter.metricprovider.trans.activeusers.model.NewsgroupData");
		USERS.setOwningType("org.ossmeter.metricprovider.trans.activeusers.model.NewsgroupData");
		DAYS.setOwningType("org.ossmeter.metricprovider.trans.activeusers.model.NewsgroupData");
	}
	
	public static StringQueryProducer URL_NAME = new StringQueryProducer("url_name"); 
	public static NumericalQueryProducer ACTIVEUSERS = new NumericalQueryProducer("activeUsers");
	public static NumericalQueryProducer INACTIVEUSERS = new NumericalQueryProducer("inactiveUsers");
	public static NumericalQueryProducer PREVIOUSUSERS = new NumericalQueryProducer("previousUsers");
	public static NumericalQueryProducer USERS = new NumericalQueryProducer("users");
	public static NumericalQueryProducer DAYS = new NumericalQueryProducer("days");
	
	
	public String getUrl_name() {
		return parseString(dbObject.get("url_name")+"", "");
	}
	
	public NewsgroupData setUrl_name(String url_name) {
		dbObject.put("url_name", url_name);
		notifyChanged();
		return this;
	}
	public int getActiveUsers() {
		return parseInteger(dbObject.get("activeUsers")+"", 0);
	}
	
	public NewsgroupData setActiveUsers(int activeUsers) {
		dbObject.put("activeUsers", activeUsers);
		notifyChanged();
		return this;
	}
	public int getInactiveUsers() {
		return parseInteger(dbObject.get("inactiveUsers")+"", 0);
	}
	
	public NewsgroupData setInactiveUsers(int inactiveUsers) {
		dbObject.put("inactiveUsers", inactiveUsers);
		notifyChanged();
		return this;
	}
	public int getPreviousUsers() {
		return parseInteger(dbObject.get("previousUsers")+"", 0);
	}
	
	public NewsgroupData setPreviousUsers(int previousUsers) {
		dbObject.put("previousUsers", previousUsers);
		notifyChanged();
		return this;
	}
	public int getUsers() {
		return parseInteger(dbObject.get("users")+"", 0);
	}
	
	public NewsgroupData setUsers(int users) {
		dbObject.put("users", users);
		notifyChanged();
		return this;
	}
	public int getDays() {
		return parseInteger(dbObject.get("days")+"", 0);
	}
	
	public NewsgroupData setDays(int days) {
		dbObject.put("days", days);
		notifyChanged();
		return this;
	}
	
	
	
	
}