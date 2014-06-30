package org.ossmeter.platform.visualisation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class ChartExtensionPointTest {
	
	@Test
	public void testLoadChart() {
		ChartExtensionPointManager manager = ChartExtensionPointManager.getInstance();
		Map<String, Chart> registeredCharts = manager.getRegisteredCharts();
		assertEquals(1, registeredCharts.size());
		System.out.println(registeredCharts.keySet().toArray()[0]);
		System.out.println(registeredCharts.get(registeredCharts.keySet().toArray()[0]));
	}
	
	@Test
	public void testLookupChart() {
		ChartExtensionPointManager manager = ChartExtensionPointManager.getInstance();
		manager.getRegisteredCharts();
		Chart chart = manager.findChartByType("LineChart");
		assertNotNull(chart);
	}

	@Test
	public void testLoadVis() throws Exception{
		MetricVisualisationExtensionPointManager manager = MetricVisualisationExtensionPointManager.getInstance();
		ChartExtensionPointManager.getInstance().getRegisteredCharts();
		Map<String, MetricVisualisation> registeredVis = manager.getRegisteredVisualisations();
		assertEquals(4, registeredVis.size());
		
		MetricVisualisation vis = manager.findVisualisationById("avgnumberofrequests");
		assertNotNull(vis);
		
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Epsilon");
		for (String visId : manager.getListOfVisualisationIds()) {
			System.out.println(visId + ":");
			System.out.println(manager.findVisualisationById(visId).visualise(db));
		}
		
	}
	
}
