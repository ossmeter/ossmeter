package org.ossmeter.platform.client.api;

import java.util.List;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;

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

	public List<PongoViz> getVisualisersForMetricProvider(String metricProviderId, DB projectDb) {
		throw new RuntimeException("Visualisation currently under construction.");
//		List<PongoViz> vizs = new ArrayList<PongoViz>();
//		
////		System.err.println("Registered visualisations (looking for '"+metricProviderId+"'):");
//		for(IConfigurationElement configurationElement : ExtensionPointHelper.getConfigurationElementsForExtensionPoint("org.ossmeter.platform.client.api.pongoviz")) {
////			System.err.println("\t"+configurationElement.getAttribute("metricProviderId") + " == " + metricProviderId);
//			if (configurationElement.getAttribute("metricProviderId").equals(metricProviderId)) {
//				try {
//					PongoViz viz = (PongoViz) configurationElement.createExecutableExtension("pongoVisualiser");
////					viz.setProjectDB(projectDb);
//					vizs.add(viz);
//				} catch (CoreException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return vizs;
	}
}
