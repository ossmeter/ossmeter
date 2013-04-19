package org.ossmeter.platform;

import org.ossmeter.repository.model.Project;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class MetricsRepository {
	
	protected Project project;
	protected Mongo mongo;
	protected DB db;
	
	public MetricsRepository(Project project, Mongo mongo) {
		super();
		this.project = project;
		this.mongo = mongo;
		this.db = mongo.getDB(project.getName());
	}
	
	public DB getDb() {
		return db;
	}
	
	/*
	@SuppressWarnings("rawtypes")
	public List<MetricCollection> getMetricCollections() {
		throw new UnsupportedOperationException();
	}
	
	@SuppressWarnings("rawtypes")
	public MetricCollection getMetricCollection(String name) {
		return new MetricCollection(db.getCollection(name));
	}
	
	@SuppressWarnings("rawtypes")
	public List<MetricCollection> getMetricCollections(IMetricProvider metricProvider) {
		ArrayList<MetricCollection> metricCollections = new ArrayList<MetricCollection>();
		for (String metricCollectionName : metricProvider.getMetricCollectionNames(mongo, project)) {
			metricCollections.add(getMetricCollection(metricCollectionName));
		}
		return metricCollections;
	}*/
}
