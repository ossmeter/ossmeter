package org.ossmeter.metricprovider.threads.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class Threads extends PongoDB {
	
	public Threads() {}
	
	public Threads(DB db) {
		setDb(db);
	}
	
	protected ArticleDataCollection articles = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public ArticleDataCollection getArticles() {
		return articles;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		articles = new ArticleDataCollection(db.getCollection("Threads.articles"));
		pongoCollections.add(articles);
	}
}