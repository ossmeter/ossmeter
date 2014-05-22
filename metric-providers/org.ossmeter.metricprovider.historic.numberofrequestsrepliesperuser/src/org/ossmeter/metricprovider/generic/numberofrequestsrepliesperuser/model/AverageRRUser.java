package org.ossmeter.metricprovider.generic.numberofrequestsrepliesperuser.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class AverageRRUser extends Pongo {
	
	protected List<AverageArticlesPerUserData> articles = null;
	protected List<AverageRequestsPerUserData> requests = null;
	protected List<AverageRepliesPerUserData> replies = null;
	
	
	public AverageRRUser() { 
		super();
		dbObject.put("articles", new BasicDBList());
		dbObject.put("requests", new BasicDBList());
		dbObject.put("replies", new BasicDBList());
	}
	
	
	
	
	
	public List<AverageArticlesPerUserData> getArticles() {
		if (articles == null) {
			articles = new PongoList<AverageArticlesPerUserData>(this, "articles", true);
		}
		return articles;
	}
	public List<AverageRequestsPerUserData> getRequests() {
		if (requests == null) {
			requests = new PongoList<AverageRequestsPerUserData>(this, "requests", true);
		}
		return requests;
	}
	public List<AverageRepliesPerUserData> getReplies() {
		if (replies == null) {
			replies = new PongoList<AverageRepliesPerUserData>(this, "replies", true);
		}
		return replies;
	}
	
	
}