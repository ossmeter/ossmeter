package org.ossmeter.platform.client.api;

import java.io.IOException;
import java.util.List;

import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.MetricProvider;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectRepository;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

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
		for (MetricProvider mp : project.getMetricProviderData()) {
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
		
		List<PongoViz> vizs = VisualisationExtensionPointManager.getInstance().getVisualisersForMetricProvider(metricProvider.getIdentifier(), collection);
		for (PongoViz v : vizs) {
			String vizString = v.getViz("gcharts"); // FIXME hardcoded.
			
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory factory = mapper.getFactory();
			JsonParser parser;
			ObjectNode root;
			try {
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
