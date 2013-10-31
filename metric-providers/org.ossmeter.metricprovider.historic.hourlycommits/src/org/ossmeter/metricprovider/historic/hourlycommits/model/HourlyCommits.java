package org.ossmeter.metricprovider.historic.hourlycommits.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class HourlyCommits extends Pongo {
	
	protected List<Hour> hours = null;
	
	
	public HourlyCommits() { 
		super();
		dbObject.put("hours", new BasicDBList());
	}
	
	
	
	
	
	public List<Hour> getHours() {
		if (hours == null) {
			hours = new PongoList<Hour>(this, "hours", true);
		}
		return hours;
	}
	
	
}