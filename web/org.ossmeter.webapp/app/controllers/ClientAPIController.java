package controllers;

import java.net.UnknownHostException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class ClientAPIController extends Controller {

    private static int pageSize = 1; // FIXME querystring? uri?
    
    public static Result projects(int page) {
    	try {
 			Mongo mongo = new Mongo();
			DB db = mongo.getDB("ossmeter");
			DBCollection projects = db.getCollection("projects");
			
			String projectsJson = "[";
			DBCursor cursor = projects.find().skip(pageSize*(page-1)).limit(pageSize);
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
			ObjectNode response = Json.newObject();
			response.put("status","error");
			response.put("message", e.getMessage());
			return badRequest(response);
		}
    }
 
    
    
 	public static Result getProject(String name) {
 		Mongo mongo;
		try {
			mongo = new Mongo();
			DB db = mongo.getDB("ossmeter");
			DBCollection projects = db.getCollection("projects");

			DBObject obj = projects.findOne(new BasicDBObject("name", name));
			return ok(Json.parse(obj.toString()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
			ObjectNode response = Json.newObject();
			response.put("status","error");
			response.put("message", e.getMessage());
			return badRequest(response);
		}
 	} 
 	
 	public static Result getMetric(String proj, String id) {
 		return TODO;
 	}
}
