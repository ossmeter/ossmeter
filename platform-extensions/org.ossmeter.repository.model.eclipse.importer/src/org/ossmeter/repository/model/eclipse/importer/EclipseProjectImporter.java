package org.ossmeter.repository.model.eclipse.importer;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ossmeter.repository.model.eclipse.importer.util.XML;
import org.ossmeter.repository.model.eclipseforge.EclipseForgeProject;
import org.w3c.dom.Element;



public class EclipseProjectImporter {
	
	private Logger logger;
	
	
	public EclipseForgeProject importProject(String projectId) {
		logger=Logger.getLogger(this.getClass().getName());
	
		EclipseForgeProject ep = new EclipseForgeProject();
		String URL_PROJECT = "http://projects.eclipse.org/projects/" + projectId;
		
		String HTML = null;
		
		ep.setDescriptionUrl(getAttribute("descriptionUrl", HTML));
		ep.setShortName(getAttribute("shortName", HTML));
		
		
	    return ep;
	}


	public String getAttribute(String name, String HTML){
		
		return null;
	}
	

	
	
}
