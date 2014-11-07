package org.ossmeter.platform;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class Configuration {

	public static String LOCAL_STORAGE = "storage_path";
	public static String MAVEN_EXECUTABLE= "maven_executable";
	public static String MONGO_HOSTS= "mongo_hosts";
	
	private static Configuration instance;
	
	protected Properties properties = new Properties();
	protected Configuration() {}
	
	public static Configuration getInstance() {
		if (instance == null) {
			instance = new Configuration();
		}
		return instance;
	}
	
	public void setConfigurationProperties(Properties props) {
		this.properties = props;
	}
	
	public String getProperty(String property, String defaultValue) {
		return properties.getProperty(property, defaultValue);
	}
	
	public Mongo getMongoConnection() throws UnknownHostException {
		
		String[] hosts = properties.getProperty(MONGO_HOSTS, "localhost:27017").split(",");
		List<ServerAddress> mongoHostAddresses = new ArrayList<>();
		for (String host : hosts) {
			mongoHostAddresses.add(new ServerAddress(host));
		}
		
		return new Mongo(mongoHostAddresses);
	}
}
