package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import models.ProjectImport;
import org.ossmeter.repository.model.Project;
import play.*;
import play.mvc.*;
import play.data.*;
import play.data.validation.ValidationError;
import play.libs.ws.*;
import play.libs.F.Function;
import play.libs.F.Promise;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.net.ConnectException;

import static play.data.Form.*;

public class Projects extends Controller {
	
	private static final String jsonUrl = "http://localhost:8182/";
	
	public static List<Project> getProjects() throws Exception{
		final Promise<List<Project>> resultPromise = WS.url(jsonUrl+"projects").get().map(
	            new Function<WSResponse, List<Project>>() {
	                public List<Project> apply(WSResponse response) {
	                	JsonNode json = response.asJson();
	                	ObjectMapper mapper = new ObjectMapper();

	                	try {
		                	return mapper.readValue(json.toString(), TypeFactory.defaultInstance().constructCollectionType(List.class, Project.class));
		                } catch (Exception e) {
		                	e.printStackTrace();
		                	return null;
		                }
	                }
	            }
	    ).recover(
		    new Function<Throwable, List<Project>>() {
		    	@Override
		    	public List<Project> apply(Throwable throwable) throws Throwable {
		    		throw throwable;
		    	}
		    }
	    );
		return resultPromise.get(120000);
	}
	
	public static Project getProject(String shortName){
		System.out.println("requesting: " + jsonUrl+"projects/p/"+shortName);
		final Promise<Project> resultPromise = WS.url(jsonUrl+"projects/p/"+shortName).get().map(
	            new Function<WSResponse, Project>() {
	                public Project apply(WSResponse response) {
	                	JsonNode json = response.asJson();

	                	// TODO : Check the result is valid

	                	// We're good! Let's show them the project.
	                    ObjectMapper mapper = new ObjectMapper();
	                	try {
	                    	Project project = mapper.readValue(json.toString(), Project.class);
	                    	return project;
	                    } catch (Exception e) {
	                    	e.printStackTrace();
							return null; // FIXME
	                    }
	                }
	            }
	    );
		return resultPromise.get(120000);
	}
	
	public static Result projects() {
		try {
		    List<Project> projectList = getProjects();
		    return ok(views.html.projects.projects.render(projectList));
		} catch (ConnectException e) {
			e.printStackTrace();
			flash(Application.FLASH_ERROR_KEY, "Unable to connect to the OSSMETER API."); //TODO move to Messages.
			return ok(views.html.index.render());
		} catch (Exception e) {
			e.printStackTrace();
			flash(Application.FLASH_ERROR_KEY, "An unexpected error has occurred. We are looking into it.");//TODO move to Messages.
			return ok(views.html.index.render());
		}
	}
	
	public static Result view(String shortName) {
		Project project  = getProject(shortName);
		if (project == null) {
			flash(Application.FLASH_ERROR_KEY, "An unexpected error has occurred. We are looking into it.");//TODO move to Messages.
			return ok(views.html.index.render());
		}
		return ok(views.html.projects.view_item.render(project));
	}
	
	public static Result create() {
		return ok(views.html.projects.form.render(form(Project.class), form(ProjectImport.class)));
		
	}

	public static Result importProject() {
	    final Form<ProjectImport> form = form(ProjectImport.class).bindFromRequest();
	    
	    if (form.hasErrors()) {
	    	flash(Application.FLASH_ERROR_KEY, "Invalid URL.");
	    	return badRequest(views.html.projects.form.render(form(Project.class), form));
	    }

		ProjectImport imp = form.get();
	    System.out.println(imp.url);

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("url", imp.url);


		final Promise<Result> resultPromise = WS.url("http://localhost:8182/projects/import").post(node)
				.map(new Function<WSResponse, Result>() {
					public Result apply(WSResponse response) {
						
						System.err.println("response body: " + response.getBody());

						JsonNode node = response.asJson();

						if (node.has("status") && "error".equals(node.get("status").asText())) {
							flash(Application.FLASH_ERROR_KEY, "Invalid URL.");
							return badRequest(views.html.projects.form.render(form(Project.class), form));
						}

						// TODO check for errors, etc.
						ObjectMapper mapper = new ObjectMapper();

						try {
							Project project = mapper.readValue(node.toString(), Project.class);
	                        // project.id = node.get("shortName").asLong();
	                        // project.name = node.get("name").asText();
	                        // project.shortName = node.get("shortName").asText();
	                        // project.url = node.get("url").asText();
	                        // project.desc = node.get("desc").asText();

							return redirect(routes.Projects.view(project.getShortName()));//views.html.projects.view_item.render(project));
							// return view(project.shortName);
						} catch (Exception e) {
							e.printStackTrace(); //FIXME: handle better
							return internalServerError(e.getMessage());
						}
					}
				});
		return resultPromise.get(120000);	
	}
	
	public static Result save() {
		Form<Project> form = form(Project.class).bindFromRequest();
		if (form.hasErrors()) {
			flash("error", "Invalid details.");
	        return badRequest(views.html.projects.form.render(form, form(ProjectImport.class)));
	    } else {
			Project project = form.get();

			// TODO

			// project.save();
			// flash.success("Project successfully created!");
	    }
	    return redirect(routes.Projects.projects());
	}
	
	public static Result edit(Long id) {
		// TODO FIXME

		// Form<Project> form = form(Project.class).fill(models.Project.find.byId(id));
		// return ok(views.html.projects.form_edit.render(form));
		return redirect(routes.Projects.projects());
	}
	
	public static Result update(Long id) {
		// TODO FIXME
		// Form<Project> form = form(Project.class).bindFromRequest();
		// if (form.hasErrors()) {
	 //        return badRequest(views.html.projects.form.render(form, form(ProjectImport.class)));
	 //    } else {
		// 	Project project = form.get();
		// 	project.id = id;
		// 	project.update();
		// 	// flash.success("Project successfully updated!");
	 //    }
		return redirect(routes.Projects.projects());
	}
	
	public static Result delete_confirmation(Long id) {
		return ok(views.html.projects.confirmation.render(id));
	}

	public static Result delete(Long id) {
		// TODO FIXME
		// Project project = models.Project.find.byId(id);
		// project.delete();
		// flash.success("Project successfully deleted!");
		return redirect(routes.Projects.projects());
	}
}
