package org.ossmeter.metricprovider.generic.totalloc.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class TLocRepositoryDataCollection extends PongoCollection<TLocRepositoryData> {
	
	public TLocRepositoryDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url");
		createIndex("repoType");
	}
	
	public Iterable<TLocRepositoryData> findById(String id) {
		return new IteratorIterable<TLocRepositoryData>(new PongoCursorIterator<TLocRepositoryData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<TLocRepositoryData> findByUrl(String q) {
		return new IteratorIterable<TLocRepositoryData>(new PongoCursorIterator<TLocRepositoryData>(this, dbCollection.find(new BasicDBObject("url", q + ""))));
	}
	
	public TLocRepositoryData findOneByUrl(String q) {
		TLocRepositoryData tLocRepositoryData = (TLocRepositoryData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url", q + "")));
		if (tLocRepositoryData != null) {
			tLocRepositoryData.setPongoCollection(this);
		}
		return tLocRepositoryData;
	}

	public long countByUrl(String q) {
		return dbCollection.count(new BasicDBObject("url", q + ""));
	}
	public Iterable<TLocRepositoryData> findByRepoType(String q) {
		return new IteratorIterable<TLocRepositoryData>(new PongoCursorIterator<TLocRepositoryData>(this, dbCollection.find(new BasicDBObject("repoType", q + ""))));
	}
	
	public TLocRepositoryData findOneByRepoType(String q) {
		TLocRepositoryData tLocRepositoryData = (TLocRepositoryData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("repoType", q + "")));
		if (tLocRepositoryData != null) {
			tLocRepositoryData.setPongoCollection(this);
		}
		return tLocRepositoryData;
	}

	public long countByRepoType(String q) {
		return dbCollection.count(new BasicDBObject("repoType", q + ""));
	}
	
	
	@Override
	public Iterator<TLocRepositoryData> iterator() {
		return new PongoCursorIterator<TLocRepositoryData>(this, dbCollection.find());
	}
	
	public void add(TLocRepositoryData tLocRepositoryData) {
		super.add(tLocRepositoryData);
	}
	
	public void remove(TLocRepositoryData tLocRepositoryData) {
		super.remove(tLocRepositoryData);
	}
	
}