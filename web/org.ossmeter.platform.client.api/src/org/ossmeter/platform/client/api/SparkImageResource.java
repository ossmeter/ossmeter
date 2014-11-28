package org.ossmeter.platform.client.api;

import org.ossmeter.platform.client.api.cache.SparkCache;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.ByteArrayRepresentation;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;

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
public class SparkImageResource extends AbstractApiResource {

	public Representation doRepresent() {
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
