package org.ossmeter.platform.client.api;

import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.eclipse.importer.EclipseProjectImporter;
import org.ossmeter.repository.model.github.importer.GitHubImporter;

public class ProjectImporter {

	
//	TODO: This should be smarter and return helpful error messages
	public Project importProject(String url) {
		Project p = null;
		
		url = url.replace("http://", "");
		url = url.replace("https://", "");
		url = url.replace("www.", "");
		
		System.out.println("front stripped: " + url);
		
		if (url.startsWith("projects.eclipse.org") || url.startsWith("eclipse.org")) {
			
			url = url.replace("projects/", "");
			url = url.replace("projects.", "");
			url = url.replace("eclipse.org/", "");
			if (url.contains("?")) {
				url = url.substring(0, url.indexOf("?"));
			}
			if (url.contains("/")) {
				url = url.substring(0, url.indexOf("/"));
			}
			
			System.out.println("url to import: " + url);
			try {
				EclipseProjectImporter importer = new EclipseProjectImporter();
				p = importer.importProject(url, Platform.getInstance());
			} catch (Exception e) {
				e.printStackTrace(); // FIXME better handling
				return null;
			}
		} else if (url.startsWith("github.com")) {
			url= url.replace("github.com/", "");
			String[] ps = url.split("/");
			if (ps.length != 2) {
				System.err.println("Invalid GithUb url");
				return null; // FIXME
			}
			String uName = ps[0];
			String pName = ps[1];
			
			GitHubImporter importer = new GitHubImporter();//"f280531cd5712b6cbff971b7610155cecc134b02"); //FIXME Temporary token
			p = importer.importRepository(uName+"/" + pName, Platform.getInstance());
		}
		
		return p;
	}
}
