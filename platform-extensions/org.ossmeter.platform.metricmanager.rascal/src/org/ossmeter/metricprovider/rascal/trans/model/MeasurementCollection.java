package org.ossmeter.metricprovider.rascal.trans.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class MeasurementCollection extends PongoCollection<Measurement> {
	
	public MeasurementCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("uri");
		createIndex("date");
	}
	
	public Iterable<Measurement> findById(String id) {
		return new IteratorIterable<Measurement>(new PongoCursorIterator<Measurement>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Measurement> findByUri(String q) {
		return new IteratorIterable<Measurement>(new PongoCursorIterator<Measurement>(this, dbCollection.find(new BasicDBObject("uri", q + ""))));
	}
	
	public Measurement findOneByUri(String q) {
		Measurement measurement = (Measurement) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("uri", q + "")));
		if (measurement != null) {
			measurement.setPongoCollection(this);
		}
		return measurement;
	}
	
	
	public Iterable<Measurement> findIntegerMeasurementsByUri(String q) {
		return new IteratorIterable<Measurement>(new PongoCursorIterator<Measurement>(this, dbCollection.find(new BasicDBObject("uri", q + "").append("_type", "org.ossmeter.metricprovider.rascal.trans.model.IntegerMeasurement"))));
	}
	
	public Measurement findOneIntegerMeasurementByUri(String q) {
		Measurement measurement = (Measurement) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("uri", q + "").append("_type", "org.ossmeter.metricprovider.rascal.trans.model.IntegerMeasurement")));
		if (measurement != null) {
			measurement.setPongoCollection(this);
		}
		return measurement;
	}
	
	
	public Iterable<Measurement> findRealMeasurementsByUri(String q) {
		return new IteratorIterable<Measurement>(new PongoCursorIterator<Measurement>(this, dbCollection.find(new BasicDBObject("uri", q + "").append("_type", "org.ossmeter.metricprovider.rascal.trans.model.RealMeasurement"))));
	}
	
	public Measurement findOneRealMeasurementByUri(String q) {
		Measurement measurement = (Measurement) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("uri", q + "").append("_type", "org.ossmeter.metricprovider.rascal.trans.model.RealMeasurement")));
		if (measurement != null) {
			measurement.setPongoCollection(this);
		}
		return measurement;
	}
	
	
	public Iterable<Measurement> findBooleanMeasurementsByUri(String q) {
		return new IteratorIterable<Measurement>(new PongoCursorIterator<Measurement>(this, dbCollection.find(new BasicDBObject("uri", q + "").append("_type", "org.ossmeter.metricprovider.rascal.trans.model.BooleanMeasurement"))));
	}
	
	public Measurement findOneBooleanMeasurementByUri(String q) {
		Measurement measurement = (Measurement) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("uri", q + "").append("_type", "org.ossmeter.metricprovider.rascal.trans.model.BooleanMeasurement")));
		if (measurement != null) {
			measurement.setPongoCollection(this);
		}
		return measurement;
	}
	
	
	public Iterable<Measurement> findStringMeasurementsByUri(String q) {
		return new IteratorIterable<Measurement>(new PongoCursorIterator<Measurement>(this, dbCollection.find(new BasicDBObject("uri", q + "").append("_type", "org.ossmeter.metricprovider.rascal.trans.model.StringMeasurement"))));
	}
	
	public Measurement findOneStringMeasurementByUri(String q) {
		Measurement measurement = (Measurement) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("uri", q + "").append("_type", "org.ossmeter.metricprovider.rascal.trans.model.StringMeasurement")));
		if (measurement != null) {
			measurement.setPongoCollection(this);
		}
		return measurement;
	}
	

	public long countByUri(String q) {
		return dbCollection.count(new BasicDBObject("uri", q + ""));
	}
	public Iterable<Measurement> findByDate(String q) {
		return new IteratorIterable<Measurement>(new PongoCursorIterator<Measurement>(this, dbCollection.find(new BasicDBObject("date", q + ""))));
	}
	
	public Measurement findOneByDate(String q) {
		Measurement measurement = (Measurement) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("date", q + "")));
		if (measurement != null) {
			measurement.setPongoCollection(this);
		}
		return measurement;
	}
	
	
	public Iterable<Measurement> findIntegerMeasurementsByDate(String q) {
		return new IteratorIterable<Measurement>(new PongoCursorIterator<Measurement>(this, dbCollection.find(new BasicDBObject("date", q + "").append("_type", "org.ossmeter.metricprovider.rascal.trans.model.IntegerMeasurement"))));
	}
	
	public Measurement findOneIntegerMeasurementByDate(String q) {
		Measurement measurement = (Measurement) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("date", q + "").append("_type", "org.ossmeter.metricprovider.rascal.trans.model.IntegerMeasurement")));
		if (measurement != null) {
			measurement.setPongoCollection(this);
		}
		return measurement;
	}
	
	
	public Iterable<Measurement> findRealMeasurementsByDate(String q) {
		return new IteratorIterable<Measurement>(new PongoCursorIterator<Measurement>(this, dbCollection.find(new BasicDBObject("date", q + "").append("_type", "org.ossmeter.metricprovider.rascal.trans.model.RealMeasurement"))));
	}
	
	public Measurement findOneRealMeasurementByDate(String q) {
		Measurement measurement = (Measurement) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("date", q + "").append("_type", "org.ossmeter.metricprovider.rascal.trans.model.RealMeasurement")));
		if (measurement != null) {
			measurement.setPongoCollection(this);
		}
		return measurement;
	}
	
	
	public Iterable<Measurement> findBooleanMeasurementsByDate(String q) {
		return new IteratorIterable<Measurement>(new PongoCursorIterator<Measurement>(this, dbCollection.find(new BasicDBObject("date", q + "").append("_type", "org.ossmeter.metricprovider.rascal.trans.model.BooleanMeasurement"))));
	}
	
	public Measurement findOneBooleanMeasurementByDate(String q) {
		Measurement measurement = (Measurement) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("date", q + "").append("_type", "org.ossmeter.metricprovider.rascal.trans.model.BooleanMeasurement")));
		if (measurement != null) {
			measurement.setPongoCollection(this);
		}
		return measurement;
	}
	
	
	public Iterable<Measurement> findStringMeasurementsByDate(String q) {
		return new IteratorIterable<Measurement>(new PongoCursorIterator<Measurement>(this, dbCollection.find(new BasicDBObject("date", q + "").append("_type", "org.ossmeter.metricprovider.rascal.trans.model.StringMeasurement"))));
	}
	
	public Measurement findOneStringMeasurementByDate(String q) {
		Measurement measurement = (Measurement) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("date", q + "").append("_type", "org.ossmeter.metricprovider.rascal.trans.model.StringMeasurement")));
		if (measurement != null) {
			measurement.setPongoCollection(this);
		}
		return measurement;
	}
	

	public long countByDate(String q) {
		return dbCollection.count(new BasicDBObject("date", q + ""));
	}
	
	@Override
	public Iterator<Measurement> iterator() {
		return new PongoCursorIterator<Measurement>(this, dbCollection.find());
	}
	
	public void add(Measurement measurement) {
		super.add(measurement);
	}
	
	public void remove(Measurement measurement) {
		super.remove(measurement);
	}
	
}