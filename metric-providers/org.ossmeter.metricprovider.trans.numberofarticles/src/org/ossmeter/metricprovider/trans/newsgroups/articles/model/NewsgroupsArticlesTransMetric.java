package org.ossmeter.metricprovider.trans.newsgroups.articles.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class NewsgroupsArticlesTransMetric extends PongoDB {
	
	public NewsgroupsArticlesTransMetric() {}
	
	public NewsgroupsArticlesTransMetric(DB db) {
		setDb(db);
	}
	
	protected NewsgroupDataCollection newsgroups = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public NewsgroupDataCollection getNewsgroups() {
		return newsgroups;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		newsgroups = new NewsgroupDataCollection(db.getCollection("NewsgroupsArticlesTransMetric.newsgroups"));
		pongoCollections.add(newsgroups);
	}
}