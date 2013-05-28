package org.ossmeter.metricprovider.generic.overalldailynumberofarticles.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class DailyNoa extends Pongo {
	
	protected List<DailyBugzillaData> newsgroups = null;
	
	
	public DailyNoa() { 
		super();
		dbObject.put("newsgroups", new BasicDBList());
	}
	
	
	
	public List<DailyBugzillaData> getNewsgroups() {
		if (newsgroups == null) {
			newsgroups = new PongoList<DailyBugzillaData>(this, "newsgroups", true);
		}
		return newsgroups;
	}
	
	
}