package org.ossmeter.platform.admin;

import java.net.UnknownHostException;

import org.restlet.engine.header.Header;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class ProjectListAnalysis extends ServerResource {

	// FIXME: This is pretty horrible, but I'm tired and want something working
	@Get("json")
	public String represent() {
		Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
		if (responseHeaders == null) {
		    responseHeaders = new Series(Header.class);
		    getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
		}
		responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
		responseHeaders.add(new Header("Access-Control-Allow-Methods", "GET"));
		
		try {
			Mongo mongo = new Mongo();
			
			DB db = mongo.getDB("ossmeter");
			DBCollection col = db.getCollection("metricAnalysis");
			
			ObjectMapper mapper = new ObjectMapper();
			ArrayNode results = mapper.createArrayNode();
			
			for (Object p : col.distinct("projectId")) {
				results.add(p.toString());
			}
			
			return results.toString();
			
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return null;
	}
}
