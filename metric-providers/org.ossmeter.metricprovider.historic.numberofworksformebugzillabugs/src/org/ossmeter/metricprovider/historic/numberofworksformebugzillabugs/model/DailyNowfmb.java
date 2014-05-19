package org.ossmeter.metricprovider.historic.numberofworksformebugzillabugs.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class DailyNowfmb extends Pongo {
	
	protected List<DailyBugzillaData> bugzillas = null;
	
	
	public DailyNowfmb() { 
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