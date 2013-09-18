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

public class Application extends Controller {

	public static Result index() {
		return redirect(routes.Application.projects(1));
	}

	public static Result projects(Integer page) {
		String url ="http://localhost:8182/projects";
		
		if (page > 0) {
			url += "/" + page;
		}
		
		return async(WS.url(url).get()
				.map(new Function<WS.Response, Result>() {
					public Result apply(WS.Response response) {
						List<Project> projects = new ArrayList<Project>();
						JsonNode projectsJson = Json.parse(response.getBody());
						
						JsonNode projs = projectsJson.get("projects");
						
						for (JsonNode node : projectsJson.get("projects")) {
							
							try {
								ObjectMapper mapper = new ObjectMapper();
								Project p = mapper.readValue(node.toString(), Project.class);
								projects.add(p);
							} catch (Exception e) {
								e.printStackTrace(); //FIXME: handle better
								return internalServerError(e.getMessage());
							}
						}
						
						return ok(views.html.index.render(projects));
					}
				}));
	}
	
	public static Result getProject(String name) {
		return async(WS.url("http://localhost:8182/projects/p/"+name).get()
				.map(new Function<WS.Response, Result>() {
					public Result apply(WS.Response response) {
						JsonNode projectJson = Json.parse(response.getBody());
						
						try {
							Project project = (Project)PongoFactory.getInstance().createPongo((DBObject)JSON.parse(projectJson.toString()));
							
//							ObjectMapper mapper = new ObjectMapper();
//							Project project = mapper.readValue(projectJson.toString(), Project.class);
							return ok(views.html.project.render(project));
						} catch (Exception e) {
							e.printStackTrace(); //FIXME: handle better
							return internalServerError(e.getMessage());
						}
					}
				}));
	}

	public static Result getMetric(String projectName, String id) {
		String url = "http://localhost:8182/projects/p/"+projectName+"/m/"+id;
		return async(WS.url(url).get()
				.map(new Function<WS.Response, Result>() {
					public Result apply(WS.Response response) {
						//TODO: check for error message.
						JsonNode result = Json.parse(response.getBody());
						return ok(result);
					}
				}));
	}

	public static Result search() {
		return TODO;
	}
}
