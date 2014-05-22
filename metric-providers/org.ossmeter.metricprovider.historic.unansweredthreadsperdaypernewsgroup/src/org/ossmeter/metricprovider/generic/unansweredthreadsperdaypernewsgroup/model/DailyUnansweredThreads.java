package org.ossmeter.metricprovider.generic.unansweredthreadsperdaypernewsgroup.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class DailyUnansweredThreads extends Pongo {
	
	protected List<DailyNewsgroupData> newsgroups = null;
	
	
	public DailyUnansweredThreads() { 
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