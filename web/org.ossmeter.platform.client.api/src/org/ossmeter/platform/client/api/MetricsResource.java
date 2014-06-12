package org.ossmeter.platform.client.api;

import java.io.IOException;
import java.util.List;

import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.MetricProvider;
import org.ossmeter.repository.model.MetricProviderExecution;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectRepository;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Options;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class MetricsResource extends ServerResource {

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
			return Util.generateErrorMessage(generateRequestJson(projectName, metricName), "No project was found with the requested name.");
		}

		IMetricProvider metricProvider = null;
		for (MetricProviderExecution mp : project.getExecutionInformation().getMetricProviderData()) {
			for (IMetricProvider imp : platform.getMetricProviderManager().getMetricProviders()) { // This is ugly. Required as repo.model.MetricProvider doesn't have the info
				if (imp.getShortIdentifier().equals(metricName)) {
					metricProvider = imp;
					break;
				}
			}
		}
		
		if (metricProvider == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return Util.generateErrorMessage(generateRequestJson(projectName, metricName), "No metric was found with the requested identifier.");
		}

		// Get collection from DB
		DB projectDB = platform.getMetricsRepository(project).getDb();
		DBCollection collection = projectDB.getCollection(metricProvider.getIdentifier());
		
		
		
		List<PongoViz> vizs = VisualisationExtensionPointManager.getInstance().getVisualisersForMetricProvider(metricProvider.getIdentifier(), projectDB);
		for (PongoViz v : vizs) {
			String vizString = v.getViz("json"); // FIXME hardcoded.
			
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory factory = mapper.getFactory();
			JsonParser parser;
			ObjectNode root;
			try {
				System.out.println(vizString);
				parser = factory.createParser(vizString);
				root = parser.readValueAsTree();
			} catch (IOException e) {
				e.printStackTrace();
				return Util.generateErrorMessage(generateRequestJson(projectName, metricName), "An error occurred during JSON generation: " + e.getMessage());
			}
			JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
			root.set("friendlyName", nodeFactory.textNode(metricProvider.getFriendlyName()));
			root.set("summary", nodeFactory.textNode(metricProvider.getSummaryInformation()));

			return root.toString();// FIXME: only returns one.
		}
		
		return Util.generateErrorMessage(generateRequestJson(projectName, metricName), "No visualiser found.");
	}
	
	
	private String generateRequestJson(String projectName, String metricId) {
		return "{\"project\" : \"" + projectName + "\", \"metricId\" : \"" + metricId + "\" }";
	}
}
