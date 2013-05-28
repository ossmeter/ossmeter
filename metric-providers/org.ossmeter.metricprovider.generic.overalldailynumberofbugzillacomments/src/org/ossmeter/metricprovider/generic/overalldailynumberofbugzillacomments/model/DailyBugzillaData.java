package org.ossmeter.metricprovider.generic.overalldailynumberofbugzillacomments.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class DailyBugzillaData extends Pongo {
	
	
	
	public DailyBugzillaData() { 
		super();
	}
	
	public int getNumberOfComments() {
		return parseInteger(dbObject.get("numberOfComments")+"", 0);
	}
	
	public DailyBugzillaData setNumberOfComments(int numberOfComments) {
		dbObject.put("numberOfComments", numberOfComments + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}