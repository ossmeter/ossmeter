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
//		System.out.println("Mongo hosts: " + properties.getProperty(MONGO_HOSTS, "localhost:27017"));
		String[] hosts = properties.getProperty(MONGO_HOSTS, "localhost:27017").split(",");
		
		if (hosts.length > 1) {
			List<ServerAddress> mongoHostAddresses = new ArrayList<>();
			for (String host : hosts) {
				String[] s = host.split(":");
				mongoHostAddresses.add(new ServerAddress(s[0], Integer.valueOf(s[1])));
			}
			
//			MongoOptions options = new MongoOptions();
//			options.connectTimeout = 1000;
			return new Mongo(mongoHostAddresses);//,options);
			
		} else {
			return new Mongo();//hosts[0]);
		}
		
		
	}
}
