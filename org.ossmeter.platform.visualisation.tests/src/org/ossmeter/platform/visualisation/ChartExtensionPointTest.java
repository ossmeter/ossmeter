package org.ossmeter.platform.visualisation;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class ChartExtensionPointTest {

	@Test
	public void test() {
		ChartExtensionPointManager manager = new ChartExtensionPointManager();
		Map<String, Chart> registeredCharts = manager.getRegisteredCharts();
		assertEquals(1, registeredCharts.size());
		System.out.println(registeredCharts.keySet().toArray()[0]);
		System.out.println(registeredCharts.get(registeredCharts.keySet().toArray()[0]));
	}

}
