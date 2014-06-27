package org.ossmeter.platform.visualisation;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class MetricVisualisationTest {
	
	final protected Mongo mongo;
	
	public MetricVisualisationTest() throws Exception {
		this.mongo = new Mongo();
	}

	@Test
	public void testVisualise() throws Exception{
		Chart chart = ChartUtil.loadChart("charts/linechart.json");
		JsonNode metricSpecification = ChartUtil.loadJsonFile("data/commitsovertime.json");
		JsonNode vis = metricSpecification.get("vis").get(0);
		
		MetricVisualisation mv = new MetricVisualisation(chart, metricSpecification, vis);
		
		DB db = mongo.getDB("Epsilon");
		
		System.out.println(mv.visualise(db));
	
	}

}
