package org.ossmeter.platform.admin;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

public class AdminApplication extends Application {

	
	
	public static final String ROOT_URI = "file:///Applications/XAMPP/xamppfiles/htdocs/vocab/";
	
	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		Directory directory = new Directory(getContext(), ROOT_URI);
		router.attach("/	", directory);

//		Guard guard = new Guard(getContext(), ChallengeScheme.HTTP_BASIC, "Restlet tutorial");
//		guard.getSecrets().put("scott", "tiger".toCharArray());
//		router.attach("/docs/", guard);
		
		return router;
	}
}
