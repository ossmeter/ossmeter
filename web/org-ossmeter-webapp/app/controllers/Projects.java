package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import models.*;
import play.*;
import play.mvc.*;
import play.data.*;
import play.data.validation.ValidationError;
import play.libs.ws.*;
import play.libs.F.Function;
import play.libs.F.Promise;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import static play.data.Form.*;

public class Projects extends Controller {
	
	private static final String jsonUrl = "http://localhost:1234/";
	
	public static List<Project> getProjects(){
		final Promise<List<Project>> resultPromise = WS.url(jsonUrl).get().map(
	            new Function<WSResponse, List<Project>>() {
	                public List<Project> apply(WSResponse response) {
	                	JsonNode json = response.asJson();
	                    ArrayNode results = (ArrayNode)json;

	                    List<Project> projects = new ArrayList<Project>();
	                    Iterator<JsonNode> it = results.iterator();
	                    while (it.hasNext()) {
	                        JsonNode node  = it.next();
	                        Project project = new Project();
	                        project.id = node.get("id").asLong();
	                        project.name = node.get("name").asText();
	                        project.shortName = node.get("shortName").asText();
	                        project.url = node.get("url").asText();
	                        project.desc = node.get("desc").asText();

	                        projects.add(project);
	                    }
	                	return projects;
	                }
	            }
	    );
		return resultPromise.get(120000);
	}
	
	public static Project getProject(String shortName){
		final Promise<Project> resultPromise = WS.url(jsonUrl+"p/"+shortName).get().map(
	            new Function<WSResponse, Project>() {
	                public Project apply(WSResponse response) {
	                	JsonNode json = response.asJson();
	                    ArrayNode results = (ArrayNode)json;

	                    Iterator<JsonNode> it = results.iterator();
	                    Project project = new Project();
	                    if (it.hasNext()) {
	                        JsonNode node  = it.next();
	                        project.id = node.get("id").asLong();
	                        project.name = node.get("name").asText();
	                        project.shortName = node.get("shortName").asText();
	                        project.url = node.get("url").asText();
	                        project.desc = node.get("desc").asText();
	                    }
                        return project;
	                }
	            }
	    );
		return resultPromise.get(120000);
	}
	
	public static Result projects() {
	    List<Project> projectList = getProjects();
	    return ok(views.html.projects.projects.render(projectList));
	}
	
	public static Result view(String shortName) {
		Project project  = getProject(shortName);
		return ok(views.html.projects.view_item.render(project));
	}
	
	public static Result create() {
		return ok(views.html.projects.form.render(form(Project.class)));
		
	}
	
	public static Result save() {
		Form<Project> form = form(Project.class).bindFromRequest();
		if (form.hasErrors()) {
	        return badRequest(views.html.projects.form.render(form));
	    } else {
			Project project = form.get();
			project.save();
			// flash.success("Project successfully created!");
	    }
	    return redirect(routes.Projects.projects());
	}
	
	public static Result edit(Long id) {
		Form<Project> form = form(Project.class).fill(models.Project.find.byId(id));
		return ok(views.html.projects.form_edit.render(form));
	}
	
	public static Result update(Long id) {
		Form<Project> form = form(Project.class).bindFromRequest();
		if (form.hasErrors()) {
	        return badRequest(views.html.projects.form.render(form));
	    } else {
			Project project = form.get();
			project.id = id;
			project.update();
			// flash.success("Project successfully updated!");
	    }
		return redirect(routes.Projects.projects());
	}
	
	public static Result delete_confirmation(Long id) {
		return ok(views.html.projects.confirmation.render(id));
	}

	public static Result delete(Long id) {
		Project project = models.Project.find.byId(id);
		project.delete();
		// flash.success("Project successfully deleted!");
		return redirect(routes.Projects.projects());
	}
}
