package org.ossmeter.metricprovider.trans.contentclassesnntp.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class ContentClassesNNTP extends PongoDB {
	
	public ContentClassesNNTP() {}
	
	public ContentClassesNNTP(DB db) {
		setDb(db);
	}
	
	protected NewsgroupDataCollection newsgroups = null;
	protected ContentClassCollection contentClasses = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public NewsgroupDataCollection getNewsgroups() {
		return newsgroups;
	}
	
	public ContentClassCollection getContentClasses() {
		return contentClasses;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		newsgroups = new NewsgroupDataCollection(db.getCollection("ContentClassesNNTP.newsgroups"));
		pongoCollections.add(newsgroups);
		contentClasses = new ContentClassCollection(db.getCollection("ContentClassesNNTP.contentClasses"));
		pongoCollections.add(contentClasses);
	}
}