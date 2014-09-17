package org.ossmeter.platform.client.api;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class ApiApplication extends Application {

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		router.attach("/", PingResource.class);
		router.attach("/search", SearchProjectResource.class);
		router.attach("/search/", SearchProjectResource.class);
		router.attach("/metrics", MetricListResource.class);
		router.attach("/metrics/", MetricListResource.class);
		router.attach("/projects", ProjectListResource.class);
		router.attach("/projects/", ProjectListResource.class);
		router.attach("/projects/p/{projectid}", ProjectResource.class);
		router.attach("/projects/p/{projectid}/", ProjectResource.class);
		router.attach("/projects/p/{projectid}/m/{metricid}", MetricVisualisationResource.class);
		router.attach("/projects/p/{projectid}/m/{metricid}/", MetricVisualisationResource.class);
		
		router.attach("/raw/metrics", RawMetricListResource.class);
		router.attach("/raw/metrics/", RawMetricListResource.class);
		router.attach("/raw/projects", ProjectListResource.class);
		router.attach("/raw/projects/", ProjectListResource.class);
		router.attach("/raw/projects/{page}", ProjectListResource.class);
		router.attach("/raw/projects/{page}/", ProjectListResource.class);
		router.attach("/raw/projects/p/{projectid}", ProjectResource.class);
		router.attach("/raw/projects/p/{projectid}/", ProjectResource.class);
		router.attach("/raw/projects/p/{projectid}/m/{metricid}", RawMetricResource.class);
		router.attach("/raw/projects/p/{projectid}/m/{metricid}/", RawMetricResource.class);
		
		return router;
	}
}
