package org.ossmeter.platform.admin;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

public class AdminApplication extends Application {
	
	public static final String ROOT_URI = "file:///Users/nikos/git/ossmeter/ossmeter_code/platform/org.ossmeter.platform.admin/static/";
	
	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		Directory directory =  new Directory(getContext(), ROOT_URI);

		router.attach("/", AdminIndex.class);
		router.attach("/performance/projects", ProjectListAnalysis.class);
		router.attach("/performance/metrics", MetricListAnalysis.class);
		router.attach("/performance/projects/{projectId}/m/{metricId}", ProjectMetricAnalysis.class);
		router.attach("/performance/metrics/{metricId}", FullMetricAnalysis.class);
		router.attach("/home", directory); 
		return router;
	}
}
