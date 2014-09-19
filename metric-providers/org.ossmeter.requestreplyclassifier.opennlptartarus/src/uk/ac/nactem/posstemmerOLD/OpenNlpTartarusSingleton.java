package uk.ac.nactem.posstemmerOLD;

import uk.ac.nactem.posstemmerOLD.OpenNlpTartarus;
import uk.ac.nactem.posstemmerOLD.OpenNlpTartarusSingleton;

public class OpenNlpTartarusSingleton {

	private static OpenNlpTartarusSingleton singleton = new OpenNlpTartarusSingleton( );
	private static OpenNlpTartarus tagger;
	
	/* A private Constructor prevents any other 
	 * class from instantiating.
	 */
	private OpenNlpTartarusSingleton(){
//      String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
//      if (path.endsWith("bin/"))
//		path = path.substring(0, path.lastIndexOf("bin/"));
		tagger = new OpenNlpTartarus();
    }
	   
	/* Static 'instance' method */
	public static OpenNlpTartarusSingleton getInstance( ) {
		return singleton;
	}
	
	/* Other methods protected by singleton-ness */
	public OpenNlpTartarus getTagger( ) {
		return tagger;
	}
	
}
