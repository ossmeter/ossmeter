package org.ossmeter.platform.client.api;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class ApiApplication extends Application {

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		router.attach("/projects", ProjectsResource.class);
		router.attach("/projects/", ProjectsResource.class);
		router.attach("/projects/{page}", ProjectsResource.class);
		router.attach("/projects/{page}/", ProjectsResource.class);
		router.attach("/projects/p/{name}", ProjectResource.class);
		router.attach("/projects/p/{name}/", ProjectResource.class);
		router.attach("/projects/p/{name}/m/{metricId}", MetricsResource.class);
		router.attach("/projects/p/{name}/m/{metricId}/", MetricsResource.class);
		
		return router;
	}
}
