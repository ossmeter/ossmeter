package org.ossmeter.metricprovider.threads.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ArticleDataCollection extends PongoCollection<ArticleData> {
	
	public ArticleDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url_name");
		createIndex("articleNumber");
	}
	
	public Iterable<ArticleData> findById(String id) {
		return new IteratorIterable<ArticleData>(new PongoCursorIterator<ArticleData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<ArticleData> findByUrl_name(String q) {
		return new IteratorIterable<ArticleData>(new PongoCursorIterator<ArticleData>(this, dbCollection.find(new BasicDBObject("url_name", q + ""))));
	}
	
	public ArticleData findOneByUrl_name(String q) {
		ArticleData articleData = (ArticleData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url_name", q + "")));
		if (articleData != null) {
			articleData.setPongoCollection(this);
		}
		return articleData;
	}
	

	public long countByUrl_name(String q) {
		return dbCollection.count(new BasicDBObject("url_name", q + ""));
	}
	public Iterable<ArticleData> findByArticleNumber(int q) {
		return new IteratorIterable<ArticleData>(new PongoCursorIterator<ArticleData>(this, dbCollection.find(new BasicDBObject("articleNumber", q + ""))));
	}
	
	public ArticleData findOneByArticleNumber(int q) {
		ArticleData articleData = (ArticleData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("articleNumber", q + "")));
		if (articleData != null) {
			articleData.setPongoCollection(this);
		}
		return articleData;
	}
	

	public long countByArticleNumber(int q) {
		return dbCollection.count(new BasicDBObject("articleNumber", q + ""));
	}
	
	@Override
	public Iterator<ArticleData> iterator() {
		return new PongoCursorIterator<ArticleData>(this, dbCollection.find());
	}
	
	public void add(ArticleData articleData) {
		super.add(articleData);
	}
	
	public void remove(ArticleData articleData) {
		super.remove(articleData);
	}
	
}