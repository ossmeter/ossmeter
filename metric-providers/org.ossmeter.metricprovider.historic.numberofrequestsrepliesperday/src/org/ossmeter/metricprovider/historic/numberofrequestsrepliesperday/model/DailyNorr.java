package org.ossmeter.metricprovider.historic.numberofrequestsrepliesperday.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class DailyNorr extends Pongo {
	
	protected List<DailyNewsgroupRequestsData> requests = null;
	protected List<DailyNewsgroupRepliesData> replies = null;
	
	
	public DailyNorr() { 
		super();
		dbObject.put("requests", new BasicDBList());
		dbObject.put("replies", new BasicDBList());
	}
	
	
	
	
	
	public List<DailyNewsgroupRequestsData> getRequests() {
		if (requests == null) {
			requests = new PongoList<DailyNewsgroupRequestsData>(this, "requests", true);
		}
		return requests;
	}
	public List<DailyNewsgroupRepliesData> getReplies() {
		if (replies == null) {
			replies = new PongoList<DailyNewsgroupRepliesData>(this, "replies", true);
		}
		return replies;
	}
	
	
}