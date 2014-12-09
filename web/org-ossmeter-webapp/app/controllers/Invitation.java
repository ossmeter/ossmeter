package controllers;

import views.html.*;
import model.*;

import play.mvc.Controller;
import play.mvc.Result;
import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.Constraints;

import com.typesafe.plugin.*;


public class Invitation extends Controller{

    private static Form<Invitation> invitationForm = Form.form(Invitation.class);

    public static Result submitInvitationRequest() throws Exception {       

        Form<Invitation> boundForm= invitationForm.bindFromRequest();
         Invitation invitation = boundForm.get();
        if(boundForm.hasErrors()){
            flash("error", "Please correct the form below.");
            return badRequest(views.html.invitation.render());//invitationForm));
        }
        else{
          
           flash("success",String.format("Success."));
            // generateEmailBody(invitation.email);
            return redirect("/invitationsent");
        }
   }


    public static Result invitationSent() {
        return ok(views.html.invitationsent.render());
    }


    public static Result requestInvitation() {
        return  ok(views.html.invitation.render());//invitationForm));
    }
    

    private static void generateEmailBody(String email) {
        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject("OSSMETER invitation request.");
        //TODO Fix this. Use config file.
        mail.setRecipient("ossmeter@gmail.com");
        mail.setFrom(email);
        mail.send( "User with email address "+ email+ " requests invitation." );
    }
}