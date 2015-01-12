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

import views.html.*;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;

import auth.MongoAuthenticator;
import com.mongodb.Mongo;
import com.mongodb.DB;

import org.apache.commons.mail.*;

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
}