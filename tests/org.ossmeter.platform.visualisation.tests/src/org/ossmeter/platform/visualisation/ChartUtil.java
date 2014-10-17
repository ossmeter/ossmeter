package org.ossmeter.platform.visualisation;

import java.io.File;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class ChartUtil {
	
	public static Chart loadChart(String path) throws Exception {
		JsonNode node = loadJsonFile(path);
		System.out.println(node);
		return new Chart(node);
	}
	
	public static DBCollection getCollection(Mongo mongo, String dbName, String collectionName) {
		DB db = mongo.getDB(dbName);
		DBCollection collection = db.getCollection(collectionName);
		return collection;
	}
	
	public static JsonNode loadJsonFile(String path) throws Exception {
		File jsonFile = new File(ChartUtil.class.getResource(path).toURI());
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readTree(jsonFile);
	}
}
