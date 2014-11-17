package org.ossmeter.platform.client.api;

import java.net.UnknownHostException;

import org.ossmeter.platform.Configuration;
import org.ossmeter.platform.Platform;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.Mongo;

public abstract class AbstractApiResource extends ServerResource {
	
	protected Platform platform;
	protected ObjectMapper mapper;
	
	abstract public Representation doRepresent();
	
	@Get("json")
	public final Representation represent() {		
		Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
		if (responseHeaders == null) {
		    responseHeaders = new Series(Header.class);
		    getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
		}
		responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
		responseHeaders.add(new Header("Access-Control-Allow-Methods", "GET"));
		
		mapper = new ObjectMapper();
		
		Mongo mongo;
		try {
			mongo = Configuration.getInstance().getMongoConnection();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
			getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
			return Util.generateErrorMessageRepresentation(generateRequestJson(mapper, null), "The API was unable to connect to the database.");
		}
		platform = new Platform(mongo);

		// Delegate to resource
		Representation rep = doRepresent();
		
		mongo.close();
		return rep;
	}
	
	protected JsonNode generateRequestJson(ObjectMapper mapper, String projectName) {
		ObjectNode n = mapper.createObjectNode();
		n.put("project", projectName);
		return n;
	}
}
