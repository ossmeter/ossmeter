package org.ossmeter.platform.client.api;

import org.ossmeter.platform.AbstractFactoidMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.Platform;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class FactoidResource extends ServerResource {

	@Get("json")
    public Representation represent() {
		Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
		if (responseHeaders == null) {
		    responseHeaders = new Series(Header.class);
		    getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
		}
		responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
		responseHeaders.add(new Header("Access-Control-Allow-Methods", "GET"));
		
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode node = mapper.createArrayNode();
		for (IMetricProvider imp : Platform.getInstance().getMetricProviderManager().getMetricProviders()) {
			if (imp instanceof AbstractFactoidMetricProvider) {
				ObjectNode factoid = mapper.createObjectNode();
				factoid.put("id", imp.getIdentifier());
				factoid.put("name", imp.getFriendlyName());
				factoid.put("summary", imp.getSummaryInformation());
				node.add(factoid);
			}
		}
		
		getResponse().setStatus(Status.SUCCESS_OK);
		StringRepresentation resp = new StringRepresentation(node.toString());
		resp.setMediaType(MediaType.APPLICATION_JSON);
		return resp;
	}
}
