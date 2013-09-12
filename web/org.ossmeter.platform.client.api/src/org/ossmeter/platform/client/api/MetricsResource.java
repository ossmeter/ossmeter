package org.ossmeter.platform.client.api;

import org.ossmeter.platform.Platform;
import org.ossmeter.platform.client.api.mixins.bkp.MetricProviderMixin;
import org.ossmeter.repository.model.MetricProvider;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectRepository;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MetricsResource extends ServerResource {

	@Get
	public String represent() {
		String projectName = (String) getRequest().getAttributes().get("name");
		String metricId = (String) getRequest().getAttributes().get("metricId");
		
		Platform platform = Platform.getInstance();
		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
		
		Project project = projectRepo.getProjects().findOneByShortName(projectName);
		if (project == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return Util.generateErrorMessage(generateRequestJson(projectName, metricId), "No project was found with the requested name.");
		}

		MetricProvider metricProvider = null;
		for (MetricProvider mp : project.getMetricProviders()) {
			if (mp.getId().equals(metricId)) {
				metricProvider = mp;
				break;
			}
		}
		
		if (metricProvider == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return Util.generateErrorMessage(generateRequestJson(projectName, metricId), "No metric was found with the requested identifier.");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixInAnnotations(MetricProvider.class, MetricProviderMixin.class);
		try {
			return mapper.writeValueAsString(metricProvider);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Util.generateErrorMessage(generateRequestJson(projectName, metricId), "An error occurred whilst converting the metric information to JSON: " + e.getMessage());
		}
	}
	
	private String generateRequestJson(String projectName, String metricId) {
		return "{\"project\" : \"" + projectName + "\", \"metricId\" : \"" + metricId + "\" }";
	}
}
