package controllers;

import play.api.libs.iteratee.Enumerator;
import play.api.libs.iteratee.Iteratee;
import play.api.mvc.PlainResult;
import play.api.mvc.SimpleResult;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import scala.Function1;
import views.html.index;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result projects() {
    	Result projectList = ClientAPIController.projects();
 		
 		Enumerator body = ((SimpleResult)projectList.getWrappedResult()).body();
 		

 		return projectList;
    }
 
 	public static Result getProject(String name) {
 		return TODO;
 	} 
 	
 	public static Result getMetrics(String projectName) {
 		return TODO;
 	}
 	
 	public static Result search() {
 		return TODO;
 	}
}
