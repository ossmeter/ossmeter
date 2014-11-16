package org.ossmeter.platform.client.api;

import java.net.UnknownHostException;
import java.text.ParseException;

import org.ossmeter.platform.Configuration;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.visualisation.MetricVisualisation;
import org.ossmeter.platform.visualisation.MetricVisualisationExtensionPointManager;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectRepository;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.QueryBuilder;

public class MetricVisualisationResource extends ServerResource {

	@Get
	public Representation represent() {
		Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
		if (responseHeaders == null) {
		    responseHeaders = new Series(Header.class);
		    getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
		}
		responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
		responseHeaders.add(new Header("Access-Control-Allow-Methods", "GET"));
		
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
		
		Mongo mongo;
		try {
			mongo = Configuration.getInstance().getMongoConnection();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
			getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
			return Util.generateErrorMessageRepresentation(generateRequestJson(projectName, metricId), "The API was unable to connect to the database.");
		}
		Platform platform = new Platform(mongo);
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
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode r = mapper.createObjectNode();
		
		r.put("project", projectName);
		r.put("metricId", metricId);
		
		return r;
	}
}
