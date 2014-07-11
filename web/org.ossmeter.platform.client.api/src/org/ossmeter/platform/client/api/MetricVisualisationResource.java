package org.ossmeter.platform.client.api;

import org.ossmeter.platform.Platform;
import org.ossmeter.platform.visualisation.MetricVisualisation;
import org.ossmeter.platform.visualisation.MetricVisualisationExtensionPointManager;
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

public class MetricVisualisationResource extends ServerResource {

	@Get
	public String represent() {
		Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
		if (responseHeaders == null) {
		    responseHeaders = new Series(Header.class);
		    getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
		}
		responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
		responseHeaders.add(new Header("Access-Control-Allow-Methods", "GET"));
		
		String projectName = (String) getRequest().getAttributes().get("name");
		String metricName = (String) getRequest().getAttributes().get("metricId");
		String queryString = null;// FIXME: need to implement this
		
		Platform platform = Platform.getInstance();
		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
		
		Project project = projectRepo.getProjects().findOneByShortName(projectName);
		if (project == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return Util.generateErrorMessage(generateRequestJson(projectName, metricName), "No project was found with the requested name.").toString();
		}
		
		MetricVisualisationExtensionPointManager manager = MetricVisualisationExtensionPointManager.getInstance();
		MetricVisualisation vis = manager.findVisualisationById(metricName);
		
		if (vis == null) {
			return Util.generateErrorMessage(generateRequestJson(projectName, metricName), "No visualiser found with specified ID.").toString();
		}
		
		DB db = platform.getMetricsRepository(project).getDb();
		JsonNode visualisation = vis.visualise(db);
		return visualisation.toString();
	}
	
	private JsonNode generateRequestJson(String projectName, String metricId) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode r = mapper.createObjectNode();
		
		r.put("project", projectName);
		r.put("metricId", metricId);
		
		return r;
	}
}
