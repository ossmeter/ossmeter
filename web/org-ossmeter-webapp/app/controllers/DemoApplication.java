package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.util.List;
import java.util.Arrays;

import org.ossmeter.repository.model.Project;

import play.Routes;
import play.data.Form;
import play.mvc.*;
import play.mvc.Http.Response;
import play.mvc.Http.Session;
import play.mvc.Result;
import providers.MyUsernamePasswordAuthProvider;
import providers.MyUsernamePasswordAuthProvider.MyLogin;
import providers.MyUsernamePasswordAuthProvider.MySignup;

import play.libs.ws.*;
import play.libs.F.Function;
import play.libs.F.Promise;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static play.data.Form.*;

import auth.MongoAuthenticator;

public class DemoApplication extends Controller {

	private static String[] demoProjects = new String[]{
		"modeling-mmt-atl","modeling-emf", "modeling-incquery"
	};

	public static Result index() {
		return ok(views.html.demo.index.render());
	}

	public static Result compare() {
		return ok(views.html.demo.compare.render(Application.getInformationSourceModel()));
	}

	public static Result viewProject(String id, boolean summary) {
		if (Arrays.asList(demoProjects).contains(id)) {
			Project project = Projects.getProject(id);
			if (project == null) {
				flash(Application.FLASH_ERROR_KEY, "An unexpected error has occurred. We are looking into it.");
				return ok(views.html.demo.index.render());
			}

			return ok(views.html.demo.view_project.render(project, Application.getInformationSourceModel(), summary));

		} else {
			flash(Application.FLASH_ERROR_KEY, "Invalid project identifier.");
			return redirect(routes.DemoApplication.index());
		}
	}
}