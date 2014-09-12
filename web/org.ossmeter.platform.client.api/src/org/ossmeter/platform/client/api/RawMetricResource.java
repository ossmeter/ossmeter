package org.ossmeter.platform.client.api;

import java.io.IOException;
import java.text.ParseException;

import org.ossmeter.platform.Date;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.visualisation.MetricVisualisation;
import org.ossmeter.platform.visualisation.MetricVisualisationExtensionPointManager;
import org.ossmeter.repository.model.MetricProviderExecution;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectRepository;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.QueryBuilder;

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
		
		String projectId = (String) getRequest().getAttributes().get("projectid");
		String metricId = (String) getRequest().getAttributes().get("metricid");
		
		// FIXME: This and MetricVisualisationResource.java are EXACTLY the same.
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
			return Util.generateErrorMessage(generateRequestJson(projectId, metricId), "Invalid date. Format must be YYYYMMDD.").toString();
		}
		
		BasicDBObject query = (BasicDBObject) builder.get(); 
		
		Platform platform = Platform.getInstance();
		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
		
		Project project = projectRepo.getProjects().findOneByShortName(projectId);
		if (project == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return Util.generateErrorMessage(generateRequestJson(projectId, metricId), "No project was found with the requested name.").toString();
		}

		// Get collection from DB
		DB projectDB = platform.getMetricsRepository(project).getDb();
		
		// TODO: essentially we want a mongoexport of the collection. 
		// Can we stream it? Page it?
		// However this may also want to be filtered and aggregated?
		// TODO: Need to lookup the collection name from the ID.
		
		MetricVisualisationExtensionPointManager manager = MetricVisualisationExtensionPointManager.getInstance();
		manager.getRegisteredVisualisations();
		MetricVisualisation vis = manager.findVisualisationById(metricId);
		
		if (vis == null) {
			return Util.generateErrorMessage(generateRequestJson(projectId, metricId), "No visualiser found with specified ID.").toString();
		}
		
		// TODO: okay, so we only allow people to get raw HISTORIC metrics? How would we
		// return multiple collections???
		
		DBCursor cursor = projectDB.getCollection(vis.getMetricId()).find(query);
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode results = mapper.createArrayNode();
		
		while (cursor.hasNext()) {
			try {
				results.add(mapper.readTree(cursor.next().toString()));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		cursor.close();
		
		return results.toString();
	}
	
	
	private JsonNode generateRequestJson(String projectName, String metricId) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode r = mapper.createObjectNode();
		
		r.put("project", projectName);
		r.put("metricId", metricId);
		
		return r;
	}
}
