package org.ossmeter.platform.client.api;

import java.util.Map;

import org.ossmeter.platform.visualisation.MetricVisualisation;
import org.ossmeter.platform.visualisation.MetricVisualisationExtensionPointManager;
import org.restlet.engine.header.Header;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class MetricListResource extends ServerResource {

	@Get("json")
    public String represent() {
		Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
		if (responseHeaders == null) {
		    responseHeaders = new Series(Header.class);
		    getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
		}
		responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
		responseHeaders.add(new Header("Access-Control-Allow-Methods", "GET"));
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode metrics = mapper.createArrayNode();
		
		MetricVisualisationExtensionPointManager manager = MetricVisualisationExtensionPointManager.getInstance();
		Map<String, MetricVisualisation> vizs = manager.getRegisteredVisualisations();
		
		for (MetricVisualisation vis : vizs.values()) {
//			FIXME: only want to return id, name, and description 
			metrics.add(vis.getVis());
		}
		
		return metrics.toString();
	}
}
