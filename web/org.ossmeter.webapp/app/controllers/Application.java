package controllers;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.ossmeter.repository.model.Project;

import play.libs.F.Function;
import play.libs.Json;
import play.libs.WS;
import play.mvc.Controller;
import play.mvc.Result;

import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class Application extends Controller {

	public static Result index() {
		return redirect(routes.Application.projects(1));
	}

	public static Result projects(Integer page) {
		String url ="http://localhost:9000/api/projects";
		
		if (page > 0) {
			url += "/" + page;
		}
		
		return async(WS.url(url).get()
				.map(new Function<WS.Response, Result>() {
					public Result apply(WS.Response response) {
						List<Project> projects = new ArrayList<Project>();
						JsonNode projectsJson = Json.parse(response.getBody());
						
						for (JsonNode node : projectsJson) {
							DBObject dbObject = (DBObject) JSON.parse(node.toString());
							Project project = (Project) PongoFactory.getInstance().createPongo(dbObject);
							projects.add(project);
						}
						
						return ok(views.html.index.render(projects));
					}
				}));
	}
	
	public static Result getProject(String name) {
		return async(WS.url("http://localhost:9000/api/project/"+name).get()
				.map(new Function<WS.Response, Result>() {
					public Result apply(WS.Response response) {
						JsonNode projectJson = Json.parse(response.getBody());
						DBObject dbObject = (DBObject) JSON.parse(projectJson.toString());
						Project project = (Project) PongoFactory.getInstance().createPongo(dbObject);
						
						return ok(views.html.project.render(project));
					}
				}));
	}

	public static Result getMetrics(String projectName) {
		return TODO;
	}

	public static Result search() {
		return TODO;
	}
}
