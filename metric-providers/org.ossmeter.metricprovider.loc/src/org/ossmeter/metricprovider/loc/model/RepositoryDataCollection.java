package org.ossmeter.metricprovider.loc.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class RepositoryDataCollection extends PongoCollection<RepositoryData> {
	
	public RepositoryDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url");
		createIndex("repoType");
	}
	
	public Iterable<RepositoryData> findById(String id) {
		return new IteratorIterable<RepositoryData>(new PongoCursorIterator<RepositoryData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<RepositoryData> findByUrl(String q) {
		return new IteratorIterable<RepositoryData>(new PongoCursorIterator<RepositoryData>(this, dbCollection.find(new BasicDBObject("url", q + ""))));
	}
	
	public RepositoryData findOneByUrl(String q) {
		RepositoryData repositoryData = (RepositoryData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url", q + "")));
		if (repositoryData != null) {
			repositoryData.setPongoCollection(this);
		}
		return repositoryData;
	}

	public long countByUrl(String q) {
		return dbCollection.count(new BasicDBObject("url", q + ""));
	}
	public Iterable<RepositoryData> findByRepoType(String q) {
		return new IteratorIterable<RepositoryData>(new PongoCursorIterator<RepositoryData>(this, dbCollection.find(new BasicDBObject("repoType", q + ""))));
	}
	
	public RepositoryData findOneByRepoType(String q) {
		RepositoryData repositoryData = (RepositoryData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("repoType", q + "")));
		if (repositoryData != null) {
			repositoryData.setPongoCollection(this);
		}
		return repositoryData;
	}

	public long countByRepoType(String q) {
		return dbCollection.count(new BasicDBObject("repoType", q + ""));
	}

	@Override
	public Iterator<RepositoryData> iterator() {
		return new PongoCursorIterator<RepositoryData>(this, dbCollection.find());
	}
	
	public void add(RepositoryData repositoryData) {
		super.add(repositoryData);
	}
	
	public void remove(RepositoryData repositoryData) {
		super.remove(repositoryData);
	}
	
}