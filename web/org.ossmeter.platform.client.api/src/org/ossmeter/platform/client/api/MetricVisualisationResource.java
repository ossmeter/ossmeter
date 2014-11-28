package org.ossmeter.platform.client.api;

import java.text.ParseException;

import org.ossmeter.platform.Date;
import org.ossmeter.platform.visualisation.MetricVisualisation;
import org.ossmeter.platform.visualisation.MetricVisualisationExtensionPointManager;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectRepository;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.QueryBuilder;

public class MetricVisualisationResource extends AbstractApiResource {

	public Representation doRepresent() {
		String projectName = (String) getRequest().getAttributes().get("projectid");
		String metricId = (String) getRequest().getAttributes().get("metricid");
		
		String agg = getQueryValue("agg");
		String start = getQueryValue("startDate");
		String end = getQueryValue("endDate");
		
		QueryBuilder builder = QueryBuilder.start();
		if (agg != null && agg != "") {
//			builder.... // TODO
		}
		try {
			if (start != null && start != "") {
				builder.and("__datetime").greaterThanEquals(new Date(start).toJavaDate());
			}
			if (end != null && end != "") {
				builder.and("__datetime").lessThanEquals(new Date(end).toJavaDate());
			}
		} catch (ParseException e) {
			return Util.generateErrorMessageRepresentation(generateRequestJson(projectName, metricId), "Invalid date. Format must be YYYYMMDD.");
		}
		
		BasicDBObject query = (BasicDBObject) builder.get(); 
		
		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
		
		Project project = projectRepo.getProjects().findOneByShortName(projectName);
		if (project == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return Util.generateErrorMessageRepresentation(generateRequestJson(projectName, metricId), "No project was found with the requested name.");
		}
		
		MetricVisualisationExtensionPointManager manager = MetricVisualisationExtensionPointManager.getInstance();
		manager.getRegisteredVisualisations();

		MetricVisualisation vis = manager.findVisualisationById(metricId);
		
		if (vis == null) {
			return Util.generateErrorMessageRepresentation(generateRequestJson(projectName, metricId), "No visualiser found with specified ID.");
		}
		
		DB db = platform.getMetricsRepository(project).getDb();
		JsonNode visualisation = vis.visualise(db, query);
		
		StringRepresentation resp = new StringRepresentation(visualisation.toString());
		resp.setMediaType(MediaType.APPLICATION_JSON);
		return resp;
	}
	
	private JsonNode generateRequestJson(String projectName, String metricId) {
		ObjectNode r = mapper.createObjectNode();
		
		r.put("project", projectName);
		r.put("metricId", metricId);
		
		return r;
	}
}
