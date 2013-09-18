package org.ossmeter.platform.client.api;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.ossmeter.platform.util.ExtensionPointHelper;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DBCollection;

/**
 * TODO: Make this more efficient. 
 * @author James Williams
 *
 */
public class VisualisationExtensionPointManager {
	
	private static VisualisationExtensionPointManager instance;
	
	public static VisualisationExtensionPointManager getInstance() {
		if (instance == null) {
			instance = new VisualisationExtensionPointManager();
		}
		return instance;
	}

	public List<PongoViz> getVisualisersForMetricProvider(String metricProviderId, DBCollection projectDb) {
		List<PongoViz> vizs = new ArrayList<PongoViz>();
		
		for(IConfigurationElement configurationElement : ExtensionPointHelper.getConfigurationElementsForExtensionPoint("org.ossmeter.platform.client.api.pongoviz")) {
			if (configurationElement.getAttribute("metricProviderId").equals(metricProviderId)) {
				try {
					PongoViz viz = (PongoViz) configurationElement.createExecutableExtension("pongoVisualiser");
					viz.setProjectCollection(projectDb);
					vizs.add(viz);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
		return vizs;
	}
}
