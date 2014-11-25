package controllers;

import views.html.*;
import model.*;

import java.util.*;

import play.mvc.Controller;
import play.mvc.Result;
import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.ValidationError;
import play.data.validation.Constraints;

import com.typesafe.plugin.*;
import java.util.UUID;

import com.mongodb.Mongo;
import com.mongodb.DBCollection;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.BasicDBObject;



public class InvitationController extends Controller {

    public static Result submitInvitationRequest() throws Exception {       
        DynamicForm boundForm= Form.form().bindFromRequest();;
        UUID id = UUID.randomUUID();
        Date requestDate = new Date();

       
        String email = boundForm.get("email");  
        //Add request to the database
        Mongo mongo = new Mongo();  
        DB db = mongo.getDB("invitation_requests");
        DBCollection invitations = db.getCollection("invitationsCollection");
        BasicDBObject request = new BasicDBObject();
        request.put("email", email);
        request.put("id", id.toString());
        request.put("requestDate", requestDate.toString());
        invitations.insert(request);

        generateEmailBody(email, id.toString());
        return redirect("/invitationsent");     
   }


    public static Result invitationSent() {
        return ok(views.html.invitationsent.render());
    }


    public static Result requestInvitation() {
        return  ok(views.html.invitation.render());
    }
    

    private static void generateEmailBody(String email, String id) {
        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();

        mail.setSubject("OSSMETER invitation.");
        //TODO Fix this. Use config file.
        mail.setRecipient(email);
        mail.setFrom("ossmeter@gmail.com");
        mail.send( "Thank you for your interest in OSSMETER. Please click the following link to complete the signup process:\n\n"+ "http://localhost:9000/signup"+"/"+id+"\n\n The OSSMETER Team");
    }

}