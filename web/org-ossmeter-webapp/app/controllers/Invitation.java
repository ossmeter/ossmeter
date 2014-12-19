package controllers;

import java.util.UUID;

import views.html.*;
import model.InvitationRequest;
import model.Users;

import play.mvc.Controller;
import play.mvc.Result;
import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.Constraints;

import com.typesafe.plugin.*;

import auth.MongoAuthenticator;
import com.mongodb.Mongo;
import com.mongodb.DB;

public class Invitation extends Controller{

    private static Form<InvitationRequest> invitationForm = Form.form(InvitationRequest.class);

    public static Result submitInvitationRequest() throws Exception {       

        Form<model.InvitationRequest> boundForm= invitationForm.bindFromRequest();
        
        if(boundForm.hasErrors()){
            flash("error", "Please correct the form below.");
            return ok(views.html.invitation.render(invitationForm));
        }
        else{
            InvitationRequest invitation = boundForm.get();

            DB db = MongoAuthenticator.getUsersDb();
            Users users = new Users(db);

            // Check they're not already a user
            if (users.getUsers().findOneByEmail(invitation.getEmail()) != null) {
                db.getMongo().close();
                flash(Application.FLASH_MESSAGE_KEY, "It looks like you already have an account with this address. Why not try logging in, or requesting a password reset?");
                return redirect(routes.Application.login());
            }

            // Check they've not already requested an invite
            if (users.getInvites().findOneByEmail(invitation.getEmail()) != null) {
                db.getMongo().close();
                flash(Application.FLASH_MESSAGE_KEY, "It looks like you have already requested an invitation. Don't worry, we've not forgotten you! We'll get in touch as soon as we can.");
                return redirect(routes.Application.index());
            }

            // Generate token and store
            invitation.setToken(UUID.randomUUID().toString());
            users.getInvites().add(invitation);

            users.getInvites().sync();
            db.getMongo().close();

            flash(Application.FLASH_MESSAGE_KEY, "Thanks for your interest! We'll get in touch soon!");
            return redirect(routes.Application.index());
        }
    }

    public static Result requestInvitation() {
        return ok(views.html.invitation.render(invitationForm));
    }
}