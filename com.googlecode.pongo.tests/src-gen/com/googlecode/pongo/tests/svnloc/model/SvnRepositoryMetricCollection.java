package com.googlecode.pongo.tests.svnloc.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class SvnRepositoryMetricCollection extends PongoCollection<SvnRepositoryMetric> {
	
	public SvnRepositoryMetricCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url");
	}
	
	public Iterable<SvnRepositoryMetric> findById(String id) {
		return new IteratorIterable<SvnRepositoryMetric>(new PongoCursorIterator<SvnRepositoryMetric>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<SvnRepositoryMetric> findByUrl(String q) {
		return new IteratorIterable<SvnRepositoryMetric>(new PongoCursorIterator<SvnRepositoryMetric>(this, dbCollection.find(new BasicDBObject("url", q + ""))));
	}
	
	public SvnRepositoryMetric findOneByUrl(String q) {
		return (SvnRepositoryMetric) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url", q + "")));
	}

	public long countByUrl(String q) {
		return dbCollection.count(new BasicDBObject("url", q + ""));
	}
	
	@Override
	public Iterator<SvnRepositoryMetric> iterator() {
		return new PongoCursorIterator<SvnRepositoryMetric>(this, dbCollection.find());
	}
	
	public void add(SvnRepositoryMetric svnRepositoryMetric) {
		super.add(svnRepositoryMetric);
	}
	
	public void remove(SvnRepositoryMetric svnRepositoryMetric) {
		super.remove(svnRepositoryMetric);
	}
	
}