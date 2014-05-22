package org.ossmeter.metricprovider.generic.avgnumberofrequestsreplies.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class AverageRR extends Pongo {
	
	protected List<ArticleData> articles = null;
	protected List<RequestData> requests = null;
	protected List<ReplyData> replies = null;
	
	
	public AverageRR() { 
		super();
		dbObject.put("articles", new BasicDBList());
		dbObject.put("requests", new BasicDBList());
		dbObject.put("replies", new BasicDBList());
	}
	
	
	
	
	
	public List<ArticleData> getArticles() {
		if (articles == null) {
			articles = new PongoList<ArticleData>(this, "articles", true);
		}
		return articles;
	}
	public List<RequestData> getRequests() {
		if (requests == null) {
			requests = new PongoList<RequestData>(this, "requests", true);
		}
		return requests;
	}
	public List<ReplyData> getReplies() {
		if (replies == null) {
			replies = new PongoList<ReplyData>(this, "replies", true);
		}
		return replies;
	}
	
	
}