package org.ossmeter.platform.client.api;


import org.restlet.data.Status;
import org.restlet.representation.Representation;

public class PingResource extends AbstractApiResource {
	
	public Representation doRepresent() {
		getResponse().setStatus(new Status(200));
		return Util.createJsonRepresentation("{'status':200, 'message' : 'hello :)'}");
	}
}
