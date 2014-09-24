package org.ossmeter.platform.client.api;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class ApiApplication extends Application {

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		router.attach("/metrics", MetricListResource.class);
		router.attach("/metrics/", MetricListResource.class);
		router.attach("/projects", ProjectListResource.class);
		router.attach("/projects/", ProjectListResource.class);
		router.attach("/projects/{page}", ProjectListResource.class);
		router.attach("/projects/{page}/", ProjectListResource.class);
		router.attach("/projects/p/{name}", ProjectResource.class);
		router.attach("/projects/p/{name}/", ProjectResource.class);
		router.attach("/projects/p/{name}/m/{metricId}", MetricVisualisationResource.class);
		router.attach("/projects/p/{name}/m/{metricId}/", MetricVisualisationResource.class);
		
		router.attach("/raw/metrics", RawMetricListResource.class);
		router.attach("/raw/metrics/", RawMetricListResource.class);
		router.attach("/raw/projects", ProjectListResource.class);
		router.attach("/raw/projects/", ProjectListResource.class);
		router.attach("/raw/projects/{page}", ProjectListResource.class);
		router.attach("/raw/projects/{page}/", ProjectListResource.class);
		router.attach("/raw/projects/p/{name}", ProjectResource.class);
		router.attach("/raw/projects/p/{name}/", ProjectResource.class);
		router.attach("/raw/projects/p/{name}/m/{metricId}", RawMetricResource.class);
		router.attach("/raw/projects/p/{name}/m/{metricId}/", RawMetricResource.class);
		
		return router;
	}
}
