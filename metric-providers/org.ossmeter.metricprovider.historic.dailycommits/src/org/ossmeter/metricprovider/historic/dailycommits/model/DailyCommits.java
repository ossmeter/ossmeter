package org.ossmeter.metricprovider.historic.dailycommits.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class DailyCommits extends Pongo {
	
	protected List<Day> days = null;
	
	
	public DailyCommits() { 
		super();
		dbObject.put("days", new BasicDBList());
	}
	
	
	
	
	
	public List<Day> getDays() {
		if (days == null) {
			days = new PongoList<Day>(this, "days", true);
		}
		return days;
	}
	
	
}