package org.ossmeter.metricprovider.generic.numberofrequestsrepliesperday.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class DailyNorr extends Pongo {
	
	protected List<DailyNewsgroupData> newsgroups = null;
	
	
	public DailyNorr() { 
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