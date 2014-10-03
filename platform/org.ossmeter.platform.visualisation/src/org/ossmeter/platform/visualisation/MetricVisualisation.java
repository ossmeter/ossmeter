package org.ossmeter.platform.visualisation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import sparky.LinearScale;

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
		
		String xColName = vis.get("x").toString();
		String yColName = vis.get("y").toString();
		
		DBCollection collection = db.getCollection(metricId); // TODO metric ID won't always be the correct identififer
		
		ArrayNode datatable = chart.createDatatable(vis.get("datatable"), collection, query);
		Iterator<JsonNode> it = datatable.iterator();
		
		List<Object> xs = new ArrayList<>();
		List<Object> ys = new ArrayList<>();
		
		while (it.hasNext()) {
			JsonNode obj = it.next();
			
			Object x = obj.get(xColName);
			Object y = obj.get(yColName);
			
			xs.add(x);
			ys.add(y);
		}
		
		int height = 30;
		int width = 150;
		int padding = 6;
		
		BufferedImage im = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = (Graphics2D) im.getGraphics();
		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g.setRenderingHints(rh);
		
	    List<Integer> xdata = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12);
	    List<Integer> ydata = Arrays.asList(3,2,6,14,7,23,7,8,12,6,8,2,4);
	    
	    Integer maxX = Collections.max(xdata);
		Integer minX = Collections.min(xdata);
		
	    Integer maxY = Collections.max(ydata);
		Integer minY = Collections.min(ydata);

		LinearScale xScale = new LinearScale(maxX, minX, width-padding, padding);
		LinearScale yScale = new LinearScale(maxY, minY, height-padding, padding);
	    
	    g.setColor(Color.white);
	    g.fillRect(0, 0, width, height);

	    Color c = new Color(0, 115, 180);
	    g.setColor(c);
	    BasicStroke stroke = new BasicStroke(2);
	    g.setStroke(stroke);
	    
	    // Draw lines
	    int prevX = xScale.scale((double)xdata.get(0));
	    int prevY = yScale.scale((double)ydata.get(0));
	    
	    for (int i = 1; i< xdata.size(); i++) {
	    	int x = xScale.scale((double)xdata.get(i));
	    	int y = yScale.scale((double)ydata.get(i));
	    	
	    	g.drawLine(prevX, prevY, x, y);
	    	prevX = x;
	    	prevY = y;
	    }
	    
	    // Draw high and low
	    Color orange = new Color(255, 148, 0);
	    g.setColor(orange);
	    
	    int x1 = xdata.get(ydata.indexOf(minY));
	    int x2 = xdata.get(ydata.indexOf(maxY));
	    
	    g.drawOval(xScale.scale((double)x1)-1, yScale.scale((double)minY)-1, 2, 2);
	    g.drawOval(xScale.scale((double)x2)-1, yScale.scale((double)maxY)-1, 2, 2);
	    
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(im, "png", baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		
		// Set the spark data
		sparkData = mapper.createObjectNode();
		sparkData.put("name", vis.path("nicename").textValue());
		sparkData.put("low", minY);
		sparkData.put("high", maxY);
		sparkData.put("first", ydata.get(0));
		sparkData.put("last", xdata.get(0));
		
		return imageInByte;
	}
	
	public ObjectNode getSparkData() {
		return sparkData;	
	}
}
