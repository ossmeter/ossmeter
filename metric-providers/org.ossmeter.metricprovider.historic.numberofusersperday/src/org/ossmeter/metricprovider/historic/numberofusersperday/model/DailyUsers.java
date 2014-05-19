package org.ossmeter.metricprovider.historic.numberofusersperday.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class DailyUsers extends Pongo {
	
	protected List<DailyNewsgroupData> newsgroups = null;
	
	
	public DailyUsers() { 
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