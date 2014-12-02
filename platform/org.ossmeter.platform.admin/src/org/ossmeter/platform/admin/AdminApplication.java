/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
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
		//router.attach("/logger", LoggingInformation.class);
		router.attach("/home", directory); 
		return router;
	}
}
