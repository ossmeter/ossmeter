package org.ossmeter.platform.client.api;

import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.MetricProviderExecution;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectRepository;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class RawMetricResource extends ServerResource {

	/**
	 * TODO: Incomplete. [12th Sept, 2013]
	 * @return
	 */
	@Get
	public String represent() {
		Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
		if (responseHeaders == null) {
		    responseHeaders = new Series(Header.class);
		    getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
		}
		responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
		responseHeaders.add(new Header("Access-Control-Allow-Methods", "GET"));
		
//		responseHeaders.add("Access-Control-Allow-Origin", "*");
//	    responseHeaders.add("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
//	    responseHeaders.add("Access-Control-Allow-Headers", "Content-Type");
//	    responseHeaders.add("Access-Control-Allow-Headers", "authCode");
//	    responseHeaders.add("Access-Control-Allow-Headers", "origin, x-requested-with, content-type");
	    
		
		
		String projectName = (String) getRequest().getAttributes().get("name");
		String metricName = (String) getRequest().getAttributes().get("metricId");
		
		Platform platform = Platform.getInstance();
		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
		
		Project project = projectRepo.getProjects().findOneByShortName(projectName);
		if (project == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return Util.generateErrorMessage(generateRequestJson(projectName, metricName), "No project was found with the requested name.").toString();
		}

		// Get collection from DB
		DB projectDB = platform.getMetricsRepository(project).getDb();
		
		// TODO: essentially we want a mongoexport of the collection. Can we stream it?
		// However this may also want to be filtered and aggregated? 
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode n = mapper.createObjectNode();
		n.put("status", "unimplemented - sorry :(");
		return n.toString();
	}
	
	
	private JsonNode generateRequestJson(String projectName, String metricId) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode r = mapper.createObjectNode();
		
		r.put("project", projectName);
		r.put("metricId", metricId);
		
		return r;
	}
}
