package org.ossmeter.repository.app;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.io.FileUtils;


public class ServiceLoaderSetup {
	
	/**
	 * In order to use use java.util.ServiceLoader in Eclipse, each project that defines
	 * services needs to have its META-INF folder copied to the bin directory (by default 
	 * only the manifest is copied). This utility method handles that for you.
	 * 
	 * Works for both metric providers and VCS managers.
	 * 
	 * FIXME: Currently assumes all projects are physically located in the same directory,
	 * which may not be the case. 
	 */
	public static void copyMetaInfToBins() throws Exception {
	
		File workbench = new File("../");
		
		System.out.println("WORKBENCH: " + workbench.getAbsolutePath());
		
		FilenameFilter filter =  new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.equals("bin") || name.equals("META-INF");
			}
		};

		for (File project : workbench.listFiles()) {
			if (!project.isDirectory()) continue;
			if (!project.getName().startsWith("org.ossmeter.metricprovider.") && !project.getName().startsWith("org.ossmeter.platform.vcs.")) continue;
			if (project.getName().endsWith(".dependencies")) continue;
			System.out.println(project);
			
			File[] dirs = project.listFiles(filter);
			
			File bin = null;
			File metaInf = null;
			
			for (File d : dirs) {
				if (d.getName().equals("bin")) {
					bin = d;
				} else if (d.getName().equals("META-INF")) {
					metaInf = d;
				}
			}
			
			if (metaInf == null) throw new Exception("Did not find a META-INF directory in " + project.getName());
			if (bin == null) throw new Exception("Did not find a bin directory in " + project.getName());
			
			FileUtils.copyDirectoryToDirectory(metaInf, bin);
		}
	}
}
