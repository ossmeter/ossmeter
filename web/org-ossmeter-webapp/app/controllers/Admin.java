package controllers;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import model.*;
import model.Users;
import models.*;
import play.Routes;
import play.data.Form;
import play.mvc.*;
import play.mvc.Http.Response;
import play.mvc.Http.Session;
import play.mvc.Result;
import play.mvc.Http.Request;

import metvis.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import views.html.*;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;

import auth.MongoAuthenticator;
import com.mongodb.Mongo;
import com.mongodb.DB;

import org.apache.commons.mail.*;

import play.libs.ws.*;
import play.libs.F.Function;
import play.libs.F.Promise;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Admin extends Controller {

	public static void logRequest(Request request) {
		final User localUser = Application.getLocalUser(session());

		if (request.uri().startsWith("/assets")) return;
		if (request.uri().startsWith("/api")) return;

		System.out.println(request.cookies());
	    System.out.println(request.host());
	    System.out.println("username: " + request.username());
	    System.out.println(request.uri());
	    System.out.println("---");
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result index() {
		final User localUser = Application.getLocalUser(session());
		return ok(views.html.admin.index.render(localUser));
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result requests() {

		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

		List<InvitationRequest> invites = new ArrayList<>();
        Iterator<InvitationRequest> it = users.getInvites().iterator();
        while (it.hasNext()) {
        	invites.add(it.next());
        }

		return ok(views.html.admin.requests.render(invites));
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result status() {
		return ok(views.html.admin.status.render());
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result offerInvite(String email) {
		System.out.println(email);

		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

        InvitationRequest inv = users.getInvites().findOneByEmail(email);
        if (inv == null) {
        	db.getMongo().close();
			flash(Application.FLASH_ERROR_KEY, "Something went wrong. DB lookup for email failed.");
			return redirect(routes.Admin.requests());        	
        }

        // TODO: Send email
        try {
	        HtmlEmail e = new HtmlEmail();
			e.addTo(email);
			// e.addTo("jamesrobertwilliams@gmail.com");
			e.setFrom("ossmeter@gmail.com", "OSSMETER");
			e.setSubject("OSSMETER Invitation");

			e.setHtmlMsg("<html><h1>Your OSSMETER.com invitation is here</h1>"+
				"<p>Thanks for your interest in OSSMETER. Please click the link below to accept your invitation into the OSSMETER beta phase and start playing :)</p>"+
				"<p><a href=\"http://www.ossmeter.com" + routes.Invitation.acceptInvitation(inv.getToken()) + "\">http://www.ossmeter.com"+routes.Invitation.acceptInvitation(inv.getToken())+"</a></p>" +
				"<p>Thanks!</p>" + 
				"</html>");
			e.setTextMsg("sd");
			
			e.setHostName("smtp.gmail.com");
			e.setSmtpPort(587);
			e.setAuthentication("ossmeter@gmail.com", "ossmeter112358");
			e.setStartTLSEnabled(true);
			
			e.send();
		} catch (Exception e) {
			e.printStackTrace();
		}

        inv.setStatus("SENT");
        users.getInvites().sync();
        db.getMongo().close();

		flash(Application.FLASH_MESSAGE_KEY, "Invite sent: " + routes.Invitation.acceptInvitation(inv.getToken()));
		return redirect(routes.Admin.requests());
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result deleteInvite(String email) {
		
		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

        InvitationRequest inv = users.getInvites().findOneByEmail(email);

        if (inv != null) {
        	users.getInvites().remove(inv);
        }

        users.getInvites().sync();
        db.getMongo().close();

		flash(Application.FLASH_MESSAGE_KEY, "Invite deleted.");
		return redirect(routes.Admin.requests());
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result getUsagePlot(String email) {
		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

        Iterable<Log> logs = users.getLogs().findByUser(email);

        // TODO: map to MetVis
        String result = "{\"status\" : \"unimplemented\"}";
        ObjectMapper mapper = new ObjectMapper();

		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		final DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        ObjectNode vis = mapper.createObjectNode();
        vis.put("id", "user-stats");
        vis.put("name", "User stats for " + email);
        vis.put("description", "The activity of the user " + email);
        vis.put("type", "BarChart");
        vis.put("timeSeries", true);
        vis.put("x", "Date");
        vis.put("y", "Quantity");

        ArrayNode data = mapper.createArrayNode();
        vis.put("datatable", data);

        for (Log log : users.getLogs().findByUser(email)) {
        	ObjectNode entry = mapper.createObjectNode();
        	entry.put("Date",  df.format(log.getDate()));
        	entry.put("Datetime",  dtf.format(log.getDate()));
        	entry.put("Quantity", 1);
        	entry.put("URI", log.getUri());
        	entry.put("User", log.getUser());
        	data.add(entry);
        }	

        //Chart line = new Chart(null);
        //MetricVisualisation vis = new MetricVisualisation(chart, spec, vis);

        db.getMongo().close();
        return ok(vis.toString()).as("application/json");
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result adminApi(String path) {

		if (!path.startsWith("/")){
			path = "/" + path;
		}
		String url = play.Play.application().configuration().getString("ossmeter.adminapi") + path; 

		Promise<Result> promise = WS.url(url).get().map(
		    new Function<WSResponse, Result>() {
		        public Result apply(WSResponse response) {
		        	List<String> contentTypes = response.getAllHeaders().get("Content-Type");
		        	if (contentTypes.size() > 0) {
		        		String type = contentTypes.get(0);
		        		if (type.contains("application/json")) {
		        			return ok(response.asJson());
		        		} else if(type.equals("image/png")) {
		        			return ok(response.getBodyAsStream());
		        		} else {
		        			System.err.println("Unrecognised Content-Type.");
			        		return ok();	
		        		}
		        	} else {
		        		System.err.println("No Content-Type set on response.");
		        		return ok();
		        	}
		        }
		    }
		);

		return promise.get(120000);
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result makeAdmin(String email) {
		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

        User u = users.getUsers().findOneByEmail(email);

        if (u == null) {
        	flash(Application.FLASH_ERROR_KEY, "That doesn't appear to be a valid user!");
        	return redirect(routes.Users.users());
        }

        Role r = new Role();
        r.setName(MongoAuthenticator.ADMIN_ROLE);
        u.getRoles().add(r);
        users.getUsers().sync();

        db.getMongo().close();
        return redirect(routes.Users.users());
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result revokeAdmin(String email) {
		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

        User u = users.getUsers().findOneByEmail(email);

        if (u == null) {
        	flash(Application.FLASH_ERROR_KEY, "That doesn't appear to be a valid user!");
        	List<User> userList = MongoAuthenticator.findAllUsers();
		    return badRequest(views.html.users.users.render(userList));
        }

        int index = -1;
        for (Role r : u.getRoles()) {
        	if (r.getName().equals(MongoAuthenticator.ADMIN_ROLE)) {
				u.getRoles().remove(r);
        		break;
        	}
        }
        // u.getRoles().remove(index);
		users.getUsers().sync();

        db.getMongo().close();	
        return redirect(routes.Users.users());
	}

	public static Result jsRoutes() {
		return ok(
				Routes.javascriptRouter("adminJSRoutes",
					controllers.routes.javascript.Admin.getUsagePlot(),
					controllers.routes.javascript.Admin.adminApi()
					)
				)
				.as("text/javascript");
	}
}