package org.ossmeter.metricprovider.generic.numberofactivecommittersperday.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class DailyActiveCommitters extends Pongo {
	
	protected List<DailyNewsgroupData> newsgroups = null;
	
	
	public DailyActiveCommitters() { 
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