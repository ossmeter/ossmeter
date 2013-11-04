package controllers;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.ossmeter.repository.model.Project;

import play.libs.F.Function;
import play.libs.Json;
import play.libs.WS;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.defaultpages.error;

import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import org.ossmeter.repository.model.Project;

public class Application extends Controller {

	// static String apiUrl ="http://localhost:8000/p/";
	static String apiUrl ="http://localhost:8182/projects/";	

	public static Result index() {
		// return redirect(routes.Application.projects(1));
		return ok(views.html.index.render());
	}

	public static Result projects() {
		return ok(views.html.projectlist.render());
	}
	
	public static Result getProject(String name) {
		System.err.println("Requesting: " + apiUrl+"p/"+name);
		return async(WS.url(apiUrl+"p/"+name).get()
				.map(new Function<WS.Response, Result>() {
					public Result apply(WS.Response response) {
						
						System.err.println(response.getBody());

						JsonNode projectJson = Json.parse(response.getBody());
						
						try {
							// FIXME: this shouldn't depend on the platform...
							Project project = (Project)PongoFactory.getInstance().createPongo((DBObject)JSON.parse(projectJson.toString()));
							
							// ObjectMapper mapper = new ObjectMapper();
							// Project project = mapper.readValue(projectJson.toString(), Project.class);
							return ok(views.html.project.render(project));//Json.toString()));
						} catch (Exception e) {
							e.printStackTrace(); //FIXME: handle better
							return internalServerError(e.getMessage());
						}
					}
				}));
	}

	public static Result getMetric(String projectName, String id) {
		return async(WS.url(apiUrl+"p/"+projectName+"/m/"+id).get()
				.map(new Function<WS.Response, Result>() {
					public Result apply(WS.Response response) {
						//TODO: check for error message.
						JsonNode result = Json.parse(response.getBody());
						return ok(result);
					}
				}));
	}

	public static Result compare() {
		return ok(views.html.compare.render());
	}

	public static Result about() {
		return ok(views.html.about.render());
	}

	public static Result search() {
		return TODO;
	}
}
