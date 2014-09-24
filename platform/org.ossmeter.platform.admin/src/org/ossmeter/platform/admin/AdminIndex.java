package org.ossmeter.platform.admin;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class AdminIndex extends ServerResource {

	@Get("html")
	public String represent() {
		
		return "<html><strong>s</strong>illy</html>";
	}
}
