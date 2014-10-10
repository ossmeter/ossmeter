package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import model.*;
import play.Routes;
import play.data.Form;
import play.mvc.*;
import play.mvc.Http.Response;
import play.mvc.Http.Session;
import play.mvc.Result;
import providers.MyUsernamePasswordAuthProvider;
import providers.MyUsernamePasswordAuthProvider.MyLogin;
import providers.MyUsernamePasswordAuthProvider.MySignup;

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
	
	
	public static Result index() {
		return ok(index.render());
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
	public static Result profileNotification() {
		final User localUser = getLocalUser(session());
		return ok(setupnotification.render(localUser, form(Notification.class)));
	}

	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result profileEventGroup() {
		final User localUser = getLocalUser(session());
		return ok();//setupeventgroup.render(localUser, form(EventGroup.class)));
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

	public static Result jsRoutes() {
		return ok(
				Routes.javascriptRouter("jsRoutes",
						controllers.routes.javascript.Signup.forgotPassword()))
				.as("text/javascript");
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