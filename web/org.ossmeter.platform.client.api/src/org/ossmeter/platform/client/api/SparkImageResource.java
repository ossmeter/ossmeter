package org.ossmeter.platform.client.api;

import org.ossmeter.platform.client.api.cache.SparkCache;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.representation.ByteArrayRepresentation;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

/**	
 * 
 * The reason for separating these, and using a unique ID for each spark instead of
 * resolving using the projectid and metricid is that we can make numerous sparks
 * for the same project-metric combo. I.e. different date ranges or aggregation methods.
 * 
 * If the cache misses, the cache misses. 
 * @author jimmy
 *
 */
public class SparkImageResource extends ServerResource {

	@Get
	public Representation represent() {
		Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
		if (responseHeaders == null) {
		    responseHeaders = new Series(Header.class);
		    getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
		}
		responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
		responseHeaders.add(new Header("Access-Control-Allow-Methods", "GET"));
		
		String sparkId = (String) getRequest().getAttributes().get("sparkid");

		byte[] s = SparkCache.getSparkCache().getSpark(sparkId);
			
		if (s == null){
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return new EmptyRepresentation();
		} else {
			return new ByteArrayRepresentation(s, MediaType.IMAGE_PNG);
		}
	}
}
