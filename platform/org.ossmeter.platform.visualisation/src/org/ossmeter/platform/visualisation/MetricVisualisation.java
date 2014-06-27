package org.ossmeter.platform.visualisation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class MetricVisualisation {
	
	/*
	 * The associated chart.
	 */
	protected final Chart chart;
	/*
	 * The global definition of the visualisation for the metric
	 */
	protected final JsonNode metricSpecification;
	/* 
	 * The specific visualisation for the metric.
	 */
	protected final JsonNode vis;
	
	protected final String metricId;
	
	public String getMetricId() {
		return metricId;
	}
	
	public MetricVisualisation(Chart chart, JsonNode metricSpecification, JsonNode vis) {
		this.chart = chart;
		this.metricSpecification = metricSpecification;
		this.vis = vis;
		this.metricId = metricSpecification.path("metricid").textValue();
	}
	
	public JsonNode visualise(DB db) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode visualisation = mapper.createObjectNode();
		
		DBCollection collection = db.getCollection(metricId); // TODO metric ID won't always be the correct identififer
		ArrayNode datatable = chart.createDatatable(vis.get("datatable"), collection);
		
		visualisation.put("name", vis.path("name").textValue());
		visualisation.put("type", vis.path("type").textValue());
		visualisation.put("description", vis.path("description").textValue());
		visualisation.put("datatable", datatable);
		
		chart.completeFields(visualisation, metricSpecification);
		
		return visualisation;
	}
}
