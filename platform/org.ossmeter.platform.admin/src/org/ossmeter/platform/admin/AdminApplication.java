package org.ossmeter.platform.admin;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class AdminApplication extends Application {
	
	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		router.attach("/", AdminIndex.class);
		router.attach("/performance/projects", ProjectListAnalysis.class);
		router.attach("/performance/metrics", MetricListAnalysis.class);
		router.attach("/performance/projects/{projectId}/m/{metricId}", ProjectMetricAnalysis.class);
		router.attach("/performance/metrics/{metricId}", FullMetricAnalysis.class);
		return router;
	}
}
