package org.ossmeter.platform.client.api;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class MetricsResource extends ServerResource {

	@Get
	public String represent() {
		String projectName = (String) getRequest().getAttributes().get("name");
		String metricId = (String) getRequest().getAttributes().get("metricId");
		
		return "project: " + projectName + ", metric id: " + metricId;
	}
}
