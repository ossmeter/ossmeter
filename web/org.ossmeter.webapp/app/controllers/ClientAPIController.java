package controllers;

import java.net.UnknownHostException;

import org.codehaus.jackson.JsonNode;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class ClientAPIController extends Controller {
  
    public static Result projects() {
    	try {
 			String projectsJson = "[";
 			Mongo mongo = new Mongo();
			DB db = mongo.getDB("ossmeter");
			DBCollection projects = db.getCollection("projects");
			
			DBCursor cursor = projects.find();
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				projectsJson += obj.toString();
				if (cursor.hasNext()) projectsJson += ",";
			}
			projectsJson += "]";
			
			JsonNode response = Json.parse(projectsJson);
			return ok(response);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return badRequest(e.getMessage()).as("application/json");// TODO: more detail / as Json
		}
    }
 
 	public static Result getProject(String name) {
 		return TODO;
 	} 
 	
 	public static Result getMetric(String proj, String id) {
 		return TODO;
 	}
}
