package org.ossmeter.platform.visualisation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import sparkle.Sparkle;
import sparkle.dimensions.DateDimension;
import sparkle.dimensions.LinearDimension;
import sparkle.scales.DateScale;
import sparkle.scales.LinearScale;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBObject;
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
	
	/*
	 * This stores the high/low etc values of the spark
	 */
	protected ObjectNode sparkData;
	
	public String getMetricId() {
		return metricId;
	}
	
	public MetricVisualisation(Chart chart, JsonNode metricSpecification, JsonNode vis) {
		this.chart = chart;
		this.metricSpecification = metricSpecification;
		this.vis = vis;
		this.metricId = metricSpecification.path("metricid").textValue();
	}
	
	public JsonNode getVis() {
		return vis;
	}
	public JsonNode visualise(DB db) {
		return visualise(db, new BasicDBObject());
		
	}
	
	public JsonNode visualise(DB db, BasicDBObject query) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode visualisation = mapper.createObjectNode();
		
		DBCollection collection = db.getCollection(metricId); // TODO metric ID won't always be the correct identififer
		ArrayNode datatable = chart.createDatatable(vis.get("datatable"), collection, query);
		
		visualisation.put("name", vis.path("name").textValue());
		visualisation.put("type", vis.path("type").textValue());
		visualisation.put("description", vis.path("description").textValue());
		visualisation.put("datatable", datatable);
		
		chart.completeFields(visualisation, vis);
		
		return visualisation;
	}
	
	public byte[] getSparky(DB db, BasicDBObject query) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode visualisation = mapper.createObjectNode();
		chart.completeFields(visualisation, vis);
		
		String xColName = vis.get("x").asText();
		String yColName = vis.get("y").asText();
		
		DBCollection collection = db.getCollection(metricId); // TODO metric ID won't always be the correct identififer
		
		ArrayNode datatable = chart.createDatatable(vis.get("datatable"), collection, query);
		Iterator<JsonNode> it = datatable.iterator();
		
		// FIXME: This is hardcoded to Dates and Doubles
		List<Date> xdata = new ArrayList<>();
		List<Double> ydata = new ArrayList<>();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		
		while (it.hasNext()) {
			JsonNode obj = it.next();
			
			Date x = null;
			try {
				x = format.parse(obj.get(xColName).asText());
			} catch (ParseException e) {
				e.printStackTrace(); // FIXME handle
			}
			yColName = yColName.replace("\"", "");
			Double y = obj.path(yColName).asDouble();
			
			xdata.add(x);
			ydata.add(y);
		}
		
		int height = 60;
		int width = 300;
		int padding = 12;
		
		DateDimension xdim = new DateDimension(xdata, width-padding, padding);
		LinearDimension ydim = new LinearDimension(ydata, height-padding, padding);
		
		Sparkle sparkle = new Sparkle(height, width, padding);
		sparkle.setHeadless(true);

		byte[] bytes = sparkle.renderToByteArray(xdim, ydim);
		
		// Set the spark data
		sparkData = mapper.createObjectNode();
		sparkData.put("name", vis.path("nicename").textValue());
		sparkData.put("low", ydim.getMinValue());
		sparkData.put("high", ydim.getMaxValue());
		sparkData.put("first", ydata.get(0));
		sparkData.put("last", ydata.get(ydata.size()-1));
		
		return bytes;
	}
	
	public ObjectNode getSparkData() {
		return sparkData;	
	}
}
