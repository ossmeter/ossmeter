package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class MetricGridEntry extends GridEntry {
	
	
	
	public MetricGridEntry() { 
		super();
		super.setSuperTypes("model.GridEntry");
		COL.setOwningType("model.MetricGridEntry");
		ROW.setOwningType("model.MetricGridEntry");
		SIZEX.setOwningType("model.MetricGridEntry");
		SIZEY.setOwningType("model.MetricGridEntry");
		PROJECTID.setOwningType("model.MetricGridEntry");
		METRICID.setOwningType("model.MetricGridEntry");
	}
	
	public static NumericalQueryProducer COL = new NumericalQueryProducer("col");
	public static NumericalQueryProducer ROW = new NumericalQueryProducer("row");
	public static NumericalQueryProducer SIZEX = new NumericalQueryProducer("sizeX");
	public static NumericalQueryProducer SIZEY = new NumericalQueryProducer("sizeY");
	public static StringQueryProducer PROJECTID = new StringQueryProducer("projectId"); 
	public static StringQueryProducer METRICID = new StringQueryProducer("metricId"); 
	
	
	public String getProjectId() {
		return parseString(dbObject.get("projectId")+"", "");
	}
	
	public MetricGridEntry setProjectId(String projectId) {
		dbObject.put("projectId", projectId);
		notifyChanged();
		return this;
	}
	public String getMetricId() {
		return parseString(dbObject.get("metricId")+"", "");
	}
	
	public MetricGridEntry setMetricId(String metricId) {
		dbObject.put("metricId", metricId);
		notifyChanged();
		return this;
	}
	
	
	
	
}