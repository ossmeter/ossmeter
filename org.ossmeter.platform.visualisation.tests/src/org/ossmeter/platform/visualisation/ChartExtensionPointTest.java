package org.ossmeter.platform.visualisation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;

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
	public void testLoadVis() {
		MetricVisualisationExtensionPointManager manager = MetricVisualisationExtensionPointManager.getInstance();
		ChartExtensionPointManager.getInstance().getRegisteredCharts();
		Map<String, MetricVisualisation> registeredVis = manager.getRegisteredVisualisations();
		assertEquals(4, registeredVis.size());
		System.out.println(registeredVis.keySet().toArray()[0]);
		System.out.println(registeredVis.get(registeredVis.keySet().toArray()[0]));
		
		MetricVisualisation vis = manager.findVisualisationById("avgnumberofrequests");
		assertNotNull(vis);
	}
	
}
