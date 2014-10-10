package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Notification extends Pongo {
	
	protected Project project = null;
	protected Metric metric = null;
	
	
	public Notification() { 
		super();
		dbObject.put("project", new Project().getDbObject());
		dbObject.put("metric", new Metric().getDbObject());
		THRESHOLD.setOwningType("model.Notification");
		ABOVETHRESHOLD.setOwningType("model.Notification");
	}
	
	public static NumericalQueryProducer THRESHOLD = new NumericalQueryProducer("threshold");
	public static StringQueryProducer ABOVETHRESHOLD = new StringQueryProducer("aboveThreshold"); 
	
	
	public double getThreshold() {
		return parseDouble(dbObject.get("threshold")+"", 0.0d);
	}
	
	public Notification setThreshold(double threshold) {
		dbObject.put("threshold", threshold);
		notifyChanged();
		return this;
	}
	public boolean getAboveThreshold() {
		return parseBoolean(dbObject.get("aboveThreshold")+"", false);
	}
	
	public Notification setAboveThreshold(boolean aboveThreshold) {
		dbObject.put("aboveThreshold", aboveThreshold);
		notifyChanged();
		return this;
	}
	
	
	
	
	public Project getProject() {
		if (project == null && dbObject.containsField("project")) {
			project = (Project) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("project"));
			project.setContainer(this);
		}
		return project;
	}
	
	public Notification setProject(Project project) {
		if (this.project != project) {
			if (project == null) {
				dbObject.removeField("project");
			}
			else {
				dbObject.put("project", project.getDbObject());
			}
			this.project = project;
			notifyChanged();
		}
		return this;
	}
	public Metric getMetric() {
		if (metric == null && dbObject.containsField("metric")) {
			metric = (Metric) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("metric"));
			metric.setContainer(this);
		}
		return metric;
	}
	
	public Notification setMetric(Metric metric) {
		if (this.metric != metric) {
			if (metric == null) {
				dbObject.removeField("metric");
			}
			else {
				dbObject.put("metric", metric.getDbObject());
			}
			this.metric = metric;
			notifyChanged();
		}
		return this;
	}
}