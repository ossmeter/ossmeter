
package org.ossmeter.platform.client.api;

import java.util.Map;

import org.ossmeter.platform.visualisation.MetricVisualisation;
import org.ossmeter.platform.visualisation.MetricVisualisationExtensionPointManager;
import org.restlet.representation.Representation;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class MetricListResource extends AbstractApiResource {

    public Representation doRepresent() {
		ArrayNode metrics = mapper.createArrayNode();
		
		MetricVisualisationExtensionPointManager manager = MetricVisualisationExtensionPointManager.getInstance();
		Map<String, MetricVisualisation> vizs = manager.getRegisteredVisualisations();
		
		for (MetricVisualisation vis : vizs.values()) {
			metrics.add(vis.getVis());
		}
		
		return Util.createJsonRepresentation(metrics);
	}
}
