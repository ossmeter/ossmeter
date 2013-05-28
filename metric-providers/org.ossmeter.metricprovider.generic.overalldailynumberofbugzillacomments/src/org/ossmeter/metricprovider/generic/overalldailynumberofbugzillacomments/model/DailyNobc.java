package org.ossmeter.metricprovider.generic.overalldailynumberofbugzillacomments.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class DailyNobc extends Pongo {
	
	protected List<DailyBugzillaData> bugzillas = null;
	
	
	public DailyNobc() { 
		super();
		dbObject.put("bugzillas", new BasicDBList());
	}
	
	
	
	public List<DailyBugzillaData> getBugzillas() {
		if (bugzillas == null) {
			bugzillas = new PongoList<DailyBugzillaData>(this, "bugzillas", true);
		}
		return bugzillas;
	}
	
	
}