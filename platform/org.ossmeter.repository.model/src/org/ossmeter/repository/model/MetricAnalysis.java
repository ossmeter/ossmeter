package org.ossmeter.repository.model;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class MetricAnalysis extends Pongo {
	
	
	
	public MetricAnalysis() { 
		super();
		METRICID.setOwningType("org.ossmeter.platform.performance.MetricAnalysis");
		ANALYSISDATE.setOwningType("org.ossmeter.platform.performance.MetricAnalysis");
		EXECUTIONDATE.setOwningType("org.ossmeter.platform.performance.MetricAnalysis");
		MILLISTAKEN.setOwningType("org.ossmeter.platform.performance.MetricAnalysis");
	}
	
	public static StringQueryProducer METRICID = new StringQueryProducer("metricId"); 
	public static StringQueryProducer ANALYSISDATE = new StringQueryProducer("analysisDate"); 
	public static StringQueryProducer EXECUTIONDATE = new StringQueryProducer("executionDate"); 
	public static NumericalQueryProducer MILLISTAKEN = new NumericalQueryProducer("millisTaken");
	
	
	public String getMetricId() {
		return parseString(dbObject.get("metricId")+"", "");
	}
	
	public MetricAnalysis setMetricId(String metricId) {
		dbObject.put("metricId", metricId);
		notifyChanged();
		return this;
	}
	public Date getDate() {
		String date = dbObject.get("analysisDate")+"";
		
		if (date == null || "".equals(date)) {
			return new Date(); //FIXME There is probably a better solution than this.
		}
		
		DateFormat formatter = new SimpleDateFormat();
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date(); //FIXME
		}
	}
	
	public Date getAnalysisDate() {
		String date = dbObject.get("analysisDate")+"";
		
		if (date == null || "".equals(date)) {
			return new Date(); //FIXME There is probably a better solution than this.
		}
		
		DateFormat formatter = new SimpleDateFormat();
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date(); //FIXME
		}
	}
	
	public MetricAnalysis setAnalysisDate(Date analysisDate) {
		dbObject.put("analysisDate", analysisDate);
		notifyChanged();
		return this;
	}

	public Date getExecutionDate() {
		String date = dbObject.get("executionDate")+"";
		
		if (date == null || "".equals(date)) {
			return new Date(); //FIXME There is probably a better solution than this.
		}
		
		DateFormat formatter = new SimpleDateFormat();
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date(); //FIXME
		}
	}
	
	public MetricAnalysis setExecutionDate(Date executionDate) {
		dbObject.put("executionDate", executionDate);
		notifyChanged();
		return this;
	}
	public double getMillisTaken() {
		return parseDouble(dbObject.get("millisTaken")+"", 0.0d);
	}
	
	public MetricAnalysis setMillisTaken(double millisTaken) {
		dbObject.put("millisTaken", millisTaken);
		notifyChanged();
		return this;
	}
	
	
	
	
}