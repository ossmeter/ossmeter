package org.ossmeter.metricprovider.historic.numberofrequestsrepliesperthread.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class AverageRRThread extends Pongo {
	
	protected List<AverageArticlesPerThreadData> articles = null;
	protected List<AverageRequestsPerThreadData> requests = null;
	protected List<AverageRepliesPerThreadData> replies = null;
	
	
	public AverageRRThread() { 
		super();
		dbObject.put("articles", new BasicDBList());
		dbObject.put("requests", new BasicDBList());
		dbObject.put("replies", new BasicDBList());
	}
	
	
	
	
	
	public List<AverageArticlesPerThreadData> getArticles() {
		if (articles == null) {
			articles = new PongoList<AverageArticlesPerThreadData>(this, "articles", true);
		}
		return articles;
	}
	public List<AverageRequestsPerThreadData> getRequests() {
		if (requests == null) {
			requests = new PongoList<AverageRequestsPerThreadData>(this, "requests", true);
		}
		return requests;
	}
	public List<AverageRepliesPerThreadData> getReplies() {
		if (replies == null) {
			replies = new PongoList<AverageRepliesPerThreadData>(this, "replies", true);
		}
		return replies;
	}
	
	
}