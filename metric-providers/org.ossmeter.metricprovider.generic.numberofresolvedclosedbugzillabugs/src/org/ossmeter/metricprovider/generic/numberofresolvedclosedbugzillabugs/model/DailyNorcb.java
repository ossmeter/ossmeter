package org.ossmeter.metricprovider.generic.numberofresolvedclosedbugzillabugs.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class DailyNorcb extends Pongo {
	
	protected List<DailyBugzillaData> bugzillas = null;
	
	
	public DailyNorcb() { 
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