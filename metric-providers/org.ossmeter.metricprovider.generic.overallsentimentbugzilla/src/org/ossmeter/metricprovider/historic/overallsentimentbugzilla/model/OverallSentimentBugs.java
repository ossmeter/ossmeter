package org.ossmeter.metricprovider.historic.overallsentimentbugzilla.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class OverallSentimentBugs extends Pongo {
	
	protected List<DailyBugzillaData> bugzillas = null;
	
	
	public OverallSentimentBugs() { 
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