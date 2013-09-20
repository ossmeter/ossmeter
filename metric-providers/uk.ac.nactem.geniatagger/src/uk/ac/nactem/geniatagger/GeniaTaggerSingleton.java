package uk.ac.nactem.geniatagger;

import java.io.IOException;

import uk.ac.nactem.tools.GeniaTagger;

public class GeniaTaggerSingleton {

	private static GeniaTaggerSingleton singleton = new GeniaTaggerSingleton( );
	private static GeniaTagger tagger;

	/* A private Constructor prevents any other 
	 * class from instantiating.
	 */
	private GeniaTaggerSingleton(){
        String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
        if (path.endsWith("bin/"))
        	path = path.substring(0, path.lastIndexOf("bin/"));
        try {
        	tagger = new GeniaTagger(path + "GeniaTaggerModels");
		} catch (IOException e) {
			System.err.println("Error while initialising GeniaTagger.");
			e.printStackTrace();
		}
    }
	   
	/* Static 'instance' method */
	public static GeniaTaggerSingleton getInstance( ) {
		return singleton;
	}
	
	/* Other methods protected by singleton-ness */
	public GeniaTagger getTagger( ) {
		return tagger;
	}

}
