package org.ossmeter.platform.client.api;

import java.util.Iterator;

import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.Platform;
import org.restlet.engine.header.Header;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class RawMetricListResource extends ServerResource {

	
	
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
		ObjectNode res = mapper.createObjectNode();

		Platform platform = Platform.getInstance();
		ArrayNode metrics = mapper.createArrayNode();
		res.put("metrics", metrics);
		
		Iterator<IMetricProvider> it = platform.getMetricProviderManager().getMetricProviders().iterator();
		
		// TODO: do we want to return the list of all metrics, or the list of metrics that can be visualised?
		while (it.hasNext()) {
			ObjectNode metric = mapper.createObjectNode();
			metrics.add(metric);
			
			IMetricProvider ip =  it.next();
			
			metric.put("name", ip.getFriendlyName());
			metric.put("type", ip.getClass().getName());
			metric.put("description", ip.getSummaryInformation());
		}

		return res.toString();
	}

	
}
