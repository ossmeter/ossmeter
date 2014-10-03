package org.ossmeter.platform.client.api;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.UUID;

import org.ossmeter.platform.Date;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.client.api.cache.SparkCache;
import org.ossmeter.platform.visualisation.MetricVisualisation;
import org.ossmeter.platform.visualisation.MetricVisualisationExtensionPointManager;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectRepository;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.representation.ByteArrayRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.QueryBuilder;

public class SparkResource extends ServerResource {

	@Get
	public Representation represent() {
		Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
		if (responseHeaders == null) {
		    responseHeaders = new Series(Header.class);
		    getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
		}
		responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
		responseHeaders.add(new Header("Access-Control-Allow-Methods", "GET"));
		
		// Check cache
		ObjectNode sd = SparkCache.getSparkCache().getSparkData(getRequest().getResourceRef().toString());
		if (sd != null) {
			return Util.createJsonRepresentation(sd);
		}
		
		// Miss. Hit database.
		String projectId = (String) getRequest().getAttributes().get("projectid");
		String metricId = (String) getRequest().getAttributes().get("metricid");

		String agg = getQueryValue("agg");
		String start = getQueryValue("startDate");
		String end = getQueryValue("endDate");
		
		ObjectMapper mapper = new ObjectMapper();
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
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			ObjectNode node = mapper.createObjectNode();
			node.put("status", "error");
			node.put("msg", "Invalid date. Format must be YYYYMMDD.");
			node.put("request", generateRequestJson(projectId, metricId));
			return Util.createJsonRepresentation(node);
		}
		
		BasicDBObject query = (BasicDBObject) builder.get(); 
		
		Platform platform = Platform.getInstance();
		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
		
		Project project = projectRepo.getProjects().findOneByShortName(projectId);
		if (project == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			ObjectNode node = mapper.createObjectNode();
			node.put("status", "error");
			node.put("msg", "No project matched that requested.");
			node.put("request", generateRequestJson(projectId, metricId));
			return Util.createJsonRepresentation(node);
		}
		
		MetricVisualisationExtensionPointManager manager = MetricVisualisationExtensionPointManager.getInstance();
		Map<String, MetricVisualisation> registeredVisualisations = manager.getRegisteredVisualisations();
		System.out.println(registeredVisualisations.keySet());
		MetricVisualisation vis = manager.findVisualisationById(metricId);
		
		if (vis == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			ObjectNode node = mapper.createObjectNode();
			node.put("status", "error");
			node.put("msg", "No visualiser found with specified ID.");
			node.put("request", generateRequestJson(projectId, metricId));
			return Util.createJsonRepresentation(node);
		}
		
		DB db = platform.getMetricsRepository(project).getDb();
		
		System.setProperty("java.awt.headless", "true");
		
		byte[] sparky;
		try {
			sparky = vis.getSparky(db, query);
			String uuid = UUID.randomUUID().toString();
			SparkCache.getSparkCache().putSpark(uuid, sparky);
			
			ObjectNode sparkData = vis.getSparkData();
			sparkData.put("spark", "/spark/"+uuid);
			sparkData.put("metricId", metricId);
			sparkData.put("projectId", projectId);
			
			// Put in the cache
			SparkCache.getSparkCache().putSparkData(getRequest().getResourceRef().toString(), sparkData);
			
			return Util.createJsonRepresentation(sparkData);
			
		} catch (IOException e) {
			e.printStackTrace(); // FIXME
			getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
			ObjectNode node = mapper.createObjectNode();
			node.put("status", "error");
			node.put("msg", "Error whilst generating sparky.");
			node.put("request", generateRequestJson(projectId, metricId));
			return Util.createJsonRepresentation(node);
		}
	}
	
	private JsonNode generateRequestJson(String projectName, String metricId) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode r = mapper.createObjectNode();
		
		r.put("project", projectName);
		r.put("metricId", metricId);
		
		return r;
	}
}
