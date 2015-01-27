package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

import models.QualityAspect;
import models.QualityModel;
import models.ProjectImport;

import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.VcsRepository;
import org.ossmeter.repository.model.vcs.svn.SvnRepository;
import org.ossmeter.repository.model.vcs.git.GitRepository;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;


import play.*;
import play.mvc.*;
import play.data.*;
import play.data.validation.ValidationError;
import play.libs.Json;
import play.libs.ws.*;
import play.libs.F.Function;
import play.libs.F.Promise;
import views.html.index;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.core.JsonProcessingException;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;

import java.net.ConnectException;

import auth.MongoAuthenticator;
import com.mongodb.DB;

import static play.data.Form.*;

@With(LogAction.class)
public class Projects extends Controller {
	
	private static final String jsonUrl = play.Play.application().configuration().getString("ossmeter.api");
	
	public static List<Project> getProjects() throws Exception{
		final Promise<List<Project>> resultPromise = WS.url(jsonUrl+"/projects").get().map(
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
		System.out.println("requesting: " + jsonUrl+"/projects/p/"+shortName);
		final Promise<Project> resultPromise = WS.url(jsonUrl+"/projects/p/"+shortName).get().map(
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
	
	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result projects() {
		try {
			// TODO: This should be limited
		    List<model.Project> projectList = new ArrayList<>();
		    DB db = MongoAuthenticator.getUsersDb();
            model.Users users = new model.Users(db);
           	for (model.Project p : users.getProjects().findAnalysed()) {
           		projectList.add(p);
           	}
			db.getMongo().close();

		    return ok(views.html.projects.projects.render(projectList, MongoAuthenticator.getStatistics()));
		} catch (Exception e) {
			e.printStackTrace();
			flash(Application.FLASH_ERROR_KEY, "An unexpected error has occurred. We are looking into it.");//TODO move to Messages.
			return redirect(routes.Application.index());
		}
	}
	
	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result viewTmp(String id, String qm, boolean summary) {

		System.err.println("selected qm:" + qm);

		Project project = getProject(id);
		if (project == null) {
			flash(Application.FLASH_ERROR_KEY, "An unexpected error has occurred. We are looking into it.");//TODO move to Messages.
			return redirect(routes.Application.index());
		}

		QualityModel qualityModel = Application.getQualityModelById(qm);
		if (qualityModel == null) qualityModel = Application.getInformationSourceModel();

		return ok(views.html.projects.view_project.render(project, qualityModel, summary));
	}

	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result view(String id) {
		
		Logger.debug("Trying to view " + id);
		return viewAspect(id, Application.INFO_SOURCE_MODEL, Application.INFO_SOURCE_MODEL, true);
	}
	
	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result viewAspect(String projectId, String qmId, String aspectId, boolean summary) {
		if (aspectId.equals("null")) aspectId = qmId; // Workaround for routing issue

		Project project = getProject(projectId);

		if (project == null) {
			flash(Application.FLASH_ERROR_KEY, "An unexpected error has occurred. We are looking into it.");//TODO move to Messages.
			return redirect(routes.Application.index());
		}

		QualityModel qm = Application.getQualityModelById(qmId);
		if (qm == null) qm = Application.getInformationSourceModel();

		// Lookup aspect
		QualityAspect aspect = findAspect(qm, aspectId);

		return ok(views.html.projects.view_project.render(project, qm, summary));
	}

	protected static QualityAspect findAspect(QualityAspect aspect, String aspectId) {

		if (aspect.id.equals(aspectId)) {
			return aspect;
		}

		for (QualityAspect a : aspect.aspects) {
			QualityAspect as = findAspect(a, aspectId);
			if (as != null) return as;
		}

		return null;
	}
	
	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result create() {
		return ok(views.html.projects.form.render(form(Project.class), form(ProjectImport.class)));
	}
	
	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result createProject() {
		// Project project = new Project();
		// project.setName("hello");

		// project.getVcsRepositories().add(new SvnRepository());
		// project.getVcsRepositories().add(new GitRepository());

		// Form<Project> form = form(Project.class);
		// form.fill(project);

		// FIXME: Cannot get the form working with POJOs. Just translate
		// Jimi's JSON into a Project object and send it.
		// return ok(views.html.projects.createProject.render(form));
		return ok(views.html.projects.addProject.render());
	}

	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result postCreateProject() {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode json = request().body().asJson();


		System.out.println(json);

	
		final Promise<Result> resultPromise = WS.url(play.Play.application().configuration().getString("ossmeter.api") + "/projects/create").post(json.toString())
			.map(new Function<WSResponse, Result>() {
				public Result apply(WSResponse response) {
					// JsonNode node = response.asJson();


					return ok();
					// if (node.has("status") && "error".equals(node.get("status").asText())) {
					// 	flash(Application.FLASH_ERROR_KEY, "Invalid URL.");
					// 	return badRequest(views.html.projects.form.render(form(Project.class), form));
					// }

					// // TODO check for errors, etc.
					// ObjectMapper mapper = new ObjectMapper();

					// try {
					// 	Project project = mapper.readValue(node.toString(), Project.class);
     //                    // project.id = node.get("shortName").asLong();
     //                    // project.name = node.get("name").asText();
     //                    // project.shortName = node.get("shortName").asText();
     //                    // project.url = node.get("url").asText();
     //                    // project.desc = node.get("desc").asText();

					// 	return redirect(routes.Projects.view(project.getShortName()));//views.html.projects.view_item.render(project));
					// 	// return view(project.shortName);
					// } catch (Exception e) {
					// 	e.printStackTrace(); //FIXME: handle better
					// 	return internalServerError(e.getMessage());
					// }
				}
			});
		return resultPromise.get(120000);	
	}	
	
	public static SvnRepository createSvnRepository(DynamicForm postData) {
		SvnRepository svnRepos = new SvnRepository();
		svnRepos.setUrl(postData.get("vcs_url"));
		return svnRepos;
	}
	

	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
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


		final Promise<Result> resultPromise = WS.url(play.Play.application().configuration().getString("ossmeter.api") + "/projects/import").post(node)
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
}
