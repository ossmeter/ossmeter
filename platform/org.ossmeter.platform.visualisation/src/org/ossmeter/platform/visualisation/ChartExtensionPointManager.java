package org.ossmeter.platform.visualisation;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChartExtensionPointManager {

	protected String extensionPointId = "";
	protected Map<String, Chart> chartMap;
	
	public Map<String, Chart> getRegisteredCharts() {
		if (chartMap == null) {
			chartMap = new HashMap<>();
		}
		
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(extensionPointId);

		if (extensionPoint != null) {
			for (IExtension element : extensionPoint.getExtensions()) {
				String name = element.getContributor().getName();
				Bundle bundle = Platform.getBundle(name);

				for (IConfigurationElement ice : element.getConfigurationElements()) {
					String url = ice.getAttribute("url");
					if (url != null) {
						// TODO: More validation is needed here, as it's very susceptible
						// to error.
						JsonNode json;
						try {
							json = loadJsonFile(url);
						} catch (Exception e) {
							e.printStackTrace(); // FIXME
							continue;
						}
						chartMap.put(json.path("type").textValue(), new Chart(json));
					}
				}
			}
		}
		return chartMap;
	}
	
	protected JsonNode loadJsonFile(String path) throws Exception {
		File jsonFile = new File(this.getClass().getResource(path).toURI());
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readTree(jsonFile);
	}
	
}
