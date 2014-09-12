package controllers;

import java.util.List;

import play.*;
import play.mvc.*;
import models.Project;
import models.User;

public class Users extends Controller {

	public static Result users() {
	    List<User> userList = User.find.all();
	    return ok(views.html.users.users.render(userList));
	}
	
	public static Result view(Long id) {
	    User user = User.findById(id);
	    return ok(views.html.users.view_item.render(user));
	}
	
	public static Result delete_confirmation(Long id) {
		return ok(views.html.users.confirmation.render(id));
	}
	
	public static Result delete(Long id) {
		User user = User.findById(id);
		// user.roles.delete();
		user.delete();
		return redirect(routes.Users.users());
	}
}
