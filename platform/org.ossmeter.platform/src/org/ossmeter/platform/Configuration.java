package org.ossmeter.platform;

import java.net.UnknownHostException;

import com.mongodb.Mongo;

public class Configuration {

	private static Configuration instance;
	protected Configuration() {
		
	}
	
	public static Configuration getInstance() {
		if (instance == null) {
			instance = new Configuration();
		}
		return instance;
	}
	
	public Mongo getMongoConnection() throws UnknownHostException {
		return new Mongo();
	}
}
