package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.util.List;

import model.*;
import models.*;
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

import views.html.*;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider;
import com.feth.play.module.pa.user.AuthUser;

import static play.data.Form.*;

import auth.MongoAuthenticator;

public class Application extends Controller {

	public static final String FLASH_MESSAGE_KEY = "message";
	public static final String FLASH_ERROR_KEY = "danger";
	
	public static final String INFO_SOURCE_MODEL = "channels";
	public static final String QUALITY_MODEL = "quality";

	public static Result index() {
		return ok(index.render());
	}

	public static Result autocomplete(String query) {
		List<model.Project> projects = MongoAuthenticator.autocomplete(query);

		ObjectMapper mapper = new ObjectMapper();

		ArrayNode arr = mapper.createArrayNode();

		for (model.Project p : projects) {
			ObjectNode o = mapper.createObjectNode();
			o.put("id", p.getId());
			o.put("name", p.getName());
			arr.add(o);
		}

		return ok(arr);
	}

	public static Result search(String query) {
		return null;
	}

	public static Result compare() {
		return ok(compare.render(getInformationSourceModel()));
	}

	public static Result jsRoutes() {
		return ok(
				Routes.javascriptRouter("jsRoutes",
						controllers.routes.javascript.Signup.forgotPassword(),
						controllers.routes.javascript.Account.watchSpark(),
						controllers.routes.javascript.Application.autocomplete(),
						controllers.routes.javascript.Application.profileNotification(),
						controllers.routes.javascript.Account.createNotification(),
						controllers.routes.javascript.Account.updateGridLocations(),
						controllers.routes.javascript.Account.loadEventGroupForm()
						)
				)
				.as("text/javascript");
	}

	public static QualityModel getInformationSourceModel() {
		return getQualityModel("conf/quality/infosourcemodel.json");
	}

	public static QualityModel getPlatformQualityModel() {
		return getQualityModel("conf/quality/qualitymodel.json");
	}

	// FIXME: Consider caching
	public static QualityModel getQualityModelById(String id) {
		if (id.equals(INFO_SOURCE_MODEL)) return getInformationSourceModel();
		else if (id.equals(QUALITY_MODEL)) return getPlatformQualityModel();
		else return null;
	}

	protected static QualityModel getQualityModel(String modelPath) {
		File qm = play.Play.application().getFile(modelPath);

		ObjectMapper mapper = new ObjectMapper();
		
		QualityModel model = null;

		try {
			model = mapper.readValue(qm, QualityModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	public static Result api(String path) {
		String url = play.Play.application().configuration().getString("ossmeter.api") + path; //FIXME: Put the API path in the config file

		Promise<Result> promise = WS.url(url).get().map(
		    new Function<WSResponse, Result>() {
		        public Result apply(WSResponse response) {
		            JsonNode json = response.asJson();
		            return ok(json);
		        }
		    }
		);

		return promise.get(120000);
	}

	public static User getLocalUser(final Session session) {
		final AuthUser currentAuthUser = PlayAuthenticate.getUser(session);
		final User localUser = MongoAuthenticator.findUser(currentAuthUser);
		return localUser;
	}

	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result admin() {
		final User localUser = getLocalUser(session());
		return ok(admin.render(localUser));
	}

	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result profile() {
		final User localUser = getLocalUser(session());
		return ok(profile.render(localUser));
	}

	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result profileNotification(String projectid, String projectName, String metricid, String metricName) {
		final User localUser = getLocalUser(session());

		Form<Notification> form = form(Notification.class);

		Notification noti = MongoAuthenticator.findNotification(localUser, projectid, metricid);
		if (noti == null) {
			noti = new Notification();
			Project p = new Project();
			p.setId(projectid);
			p.setName(projectName);
			Metric m = new Metric();
			m.setId(metricid);
			m.setName(metricName);

			noti.setProject(p);
			noti.setMetric(m);
		} else {

			System.out.println("notification already exists!");
			System.out.println(noti.getDbObject());

			form.fill(noti);
		}

		return ok(views.html.projects._notificationForm.render(form));
	}

	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result profileEventGroup() {
		final User localUser = getLocalUser(session());
		return ok(setupeventgroup.render(localUser, form(EventGroup.class)));
	}

	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result profileEvent() {
		final User localUser = getLocalUser(session());
		return ok(setupevent.render(localUser, form(Event.class)));
	}


	public static Result login() {
		return ok(login.render(MyUsernamePasswordAuthProvider.LOGIN_FORM));
	}

	public static Result doLogin() {
		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		final Form<MyLogin> filledForm = MyUsernamePasswordAuthProvider.LOGIN_FORM
				.bindFromRequest();
		if (filledForm.hasErrors()) {
			// User did not fill everything properly
			return badRequest(login.render(filledForm));
		} else {
			// Everything was filled
			return UsernamePasswordAuthProvider.handleLogin(ctx());
		}
	}

	public static Result signup() {
		return ok(signup.render(MyUsernamePasswordAuthProvider.SIGNUP_FORM));
	}

	public static Result doSignup() {
		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		final Form<MySignup> filledForm = MyUsernamePasswordAuthProvider.SIGNUP_FORM
				.bindFromRequest();
		if (filledForm.hasErrors()) {
			// User did not fill everything properly
			return badRequest(signup.render(filledForm));
		} else {
			// Everything was filled
			// do something with your part of the form before handling the user
			// signup
			return UsernamePasswordAuthProvider.handleSignup(ctx());
		}
	}

	public static String formatTimestamp(final long t) {
		return new SimpleDateFormat("yyyy-dd-MM HH:mm:ss").format(new Date(t));
	}

}