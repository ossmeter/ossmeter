package org.ossmeter.platform.visualisation;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class ChartTest {
	
	Mongo mongo;
	public ChartTest() throws Exception {
		mongo = new Mongo();
	}
	@Test
	public void testChartCreation() throws Exception {
		ChartUtil.loadChart("charts/linechart.json");
	}
	
 	@Test
	public void testDatatableWithoutRowDefinition() throws Exception {
		DBCollection collection = ChartUtil.getCollection(mongo, "Epsilon","org.ossmeter.metricprovider.historic.commitsovertime.CommitsOverTimeHistoricMetricProvider");
		JsonNode node = ChartUtil.loadJsonFile("data/commitsovertime.json");
		
		ArrayNode vis = (ArrayNode) node.get("vis");
		JsonNode datatable = vis.get(0).get("datatable");
		
		Chart chart = ChartUtil.loadChart("");
		ArrayNode table = chart.createDatatable(datatable, collection, null);
		System.out.println(table);
	}
	
	@Test
	public void testDatatableWithRowDefinition() throws Exception {
		DBCollection collection = ChartUtil.getCollection(mongo, "Epsilon", "org.ossmeter.metricprovider.historic.avgnumberofrequestsreplies");
		JsonNode node = ChartUtil.loadJsonFile("data/articlesrequestsreplies.json");
		
		ArrayNode vis = (ArrayNode) node.get("vis");
		JsonNode datatable = vis.get(0).get("datatable");
		
		Chart chart = ChartUtil.loadChart("");
		ArrayNode table = chart.createDatatable(datatable, collection, null);
		System.out.println(table);
	}
}
