package org.ossmeter.metricprovider.generic.numberofactiveusersperdaypernewsgroup.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class DailyActiveUsers extends Pongo {
	
	protected List<DailyNewsgroupData> newsgroups = null;
	
	
	public DailyActiveUsers() { 
		super();
		dbObject.put("newsgroups", new BasicDBList());
	}
	
	
	
	
	
	public List<DailyNewsgroupData> getNewsgroups() {
		if (newsgroups == null) {
			newsgroups = new PongoList<DailyNewsgroupData>(this, "newsgroups", true);
		}
		return newsgroups;
	}
	
	
}