package org.ossmeter.platform.client.api;

import org.ossmeter.platform.Platform;
import org.ossmeter.platform.factoids.Factoid;
import org.ossmeter.platform.factoids.Factoids;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectRepository;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class FactoidResource extends ServerResource {

	@Get("json")
    public Representation represent() {
		Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
		if (responseHeaders == null) {
		    responseHeaders = new Series(Header.class);
		    getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
		}
		responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
		responseHeaders.add(new Header("Access-Control-Allow-Methods", "GET"));
		
		String projectName = (String) getRequest().getAttributes().get("projectid");
		String id = (String) getRequest().getAttributes().get("factoidid");
		
		ObjectMapper mapper = new ObjectMapper();
		
		Platform platform = Platform.getInstance();
		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
		
		Project project = projectRepo.getProjects().findOneByShortName(projectName);
		if (project == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			ObjectNode node = mapper.createObjectNode();
			node.put("status", "error");
			node.put("msg", "No project was found with the requested name.");
			node.put("request", generateRequestJson(projectName, id));
			return Util.createJsonRepresentation(node);
		}
	
		Factoids factoids = new Factoids(platform.getMetricsRepository(project).getDb());
		if (id == null || id.equals("")) {
			String filter = getQueryValue("cat"); // filter by category --unimplemented
			
			ArrayNode arr = mapper.createArrayNode();
			
			for (Factoid f : factoids.getFactoids()) {
				ObjectNode factoid = mapper.createObjectNode();
				factoid.put("id", f.getMetricId());
				factoid.put("factoid", f.getFactoid());
				factoid.put("stars", f.getStars().toString());
				arr.add(factoid);
			}
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return Util.createJsonRepresentation(arr);
			
			
		} else {		
			Factoid f = factoids.getFactoids().findOneByMetricId(id);
			
			if (f == null) {
				getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
				ObjectNode node = mapper.createObjectNode();
				node.put("status", "error");
				node.put("msg", "No factoid was found with the requested identifier.");
				node.put("request", generateRequestJson(projectName, id));
				return Util.createJsonRepresentation(node);
			} else {
				ObjectNode factoid = mapper.createObjectNode();
				factoid.put("id", f.getMetricId());
				factoid.put("factoid", f.getFactoid());
				factoid.put("stars", f.getStars().toString());
				
				getResponse().setStatus(Status.SUCCESS_OK);
				return Util.createJsonRepresentation(factoid);
			}
		}
	}
	
	private JsonNode generateRequestJson(String projectName, String factoidid) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode r = mapper.createObjectNode();
		
		r.put("project", projectName);
		r.put("factoidId", factoidid);
		
		return r;
	}
	
}
