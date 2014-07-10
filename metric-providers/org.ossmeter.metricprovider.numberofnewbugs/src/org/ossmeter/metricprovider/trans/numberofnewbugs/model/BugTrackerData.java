package org.ossmeter.metricprovider.trans.numberofnewbugs.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class BugTrackerData extends Pongo {
	
	
	
	public BugTrackerData() { 
		super();
	}
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugTrackerData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId + "");
		notifyChanged();
		return this;
	}
	public int getNumberOfBugs() {
		return parseInteger(dbObject.get("numberOfBugs")+"", 0);
	}
	
	public BugTrackerData setNumberOfBugs(int numberOfBugs) {
		dbObject.put("numberOfBugs", numberOfBugs + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}