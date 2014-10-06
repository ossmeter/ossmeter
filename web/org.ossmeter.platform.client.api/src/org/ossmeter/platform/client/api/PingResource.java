package org.ossmeter.platform.client.api;


import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class PingResource extends ServerResource {
	
	@Get
	public String represent() {
		getResponse().setStatus(new Status(200));
		return "{'status':200}";
	}
}
