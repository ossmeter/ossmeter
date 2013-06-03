package org.ossmeter.metricprovider.numberofnewbugzillabugs.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class BugzillaData extends Pongo {
	
	
	
	public BugzillaData() { 
		super();
	}
	
	public String getUrl_prod_comp() {
		return parseString(dbObject.get("url_prod_comp")+"", "");
	}
	
	public BugzillaData setUrl_prod_comp(String url_prod_comp) {
		dbObject.put("url_prod_comp", url_prod_comp + "");
		notifyChanged();
		return this;
	}
	public int getNumberOfBugs() {
		return parseInteger(dbObject.get("numberOfBugs")+"", 0);
	}
	
	public BugzillaData setNumberOfBugs(int numberOfBugs) {
		dbObject.put("numberOfBugs", numberOfBugs + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}