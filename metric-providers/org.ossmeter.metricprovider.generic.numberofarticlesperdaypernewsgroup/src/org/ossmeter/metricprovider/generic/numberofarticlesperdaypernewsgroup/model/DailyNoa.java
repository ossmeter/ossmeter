package org.ossmeter.metricprovider.generic.numberofarticlesperdaypernewsgroup.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class DailyNoa extends Pongo {
	
	protected List<DailyNewsgroupData> newsgroups = null;
	
	
	public DailyNoa() { 
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