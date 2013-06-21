// @SOURCE:/Users/nikos/Desktop/eclipseOss/workspace/org.ossmeter.webapp/conf/routes
// @HASH:e3a421f716c1d07de107a8a71d53a6d1084d4efd
// @DATE:Fri Jun 21 11:49:04 BST 2013


import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString

object Routes extends Router.Routes {

private var _prefix = "/"

def setPrefix(prefix: String) {
  _prefix = prefix  
  List[(String,Routes)]().foreach {
    case (p, router) => router.setPrefix(prefix + (if(prefix.endsWith("/")) "" else "/") + p)
  }
}

def prefix = _prefix

lazy val defaultPrefix = { if(Routes.prefix.endsWith("/")) "" else "/" } 


// @LINE:6
private[this] lazy val controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart(Routes.prefix))))
        

// @LINE:7
private[this] lazy val controllers_Application_projects1 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("projects"))))
        

// @LINE:8
private[this] lazy val controllers_Application_getProject2 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("project/"),DynamicPart("name", """[^/]+""",true))))
        

// @LINE:9
private[this] lazy val controllers_Application_search3 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("search"))))
        

// @LINE:11
private[this] lazy val controllers_ClientAPIController_projects4 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/projects"))))
        

// @LINE:12
private[this] lazy val controllers_ClientAPIController_getProject5 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/project/"),DynamicPart("name", """[^/]+""",true))))
        

// @LINE:13
private[this] lazy val controllers_ClientAPIController_getMetric6 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/project/"),DynamicPart("name", """[^/]+""",true),StaticPart("/metric/"),DynamicPart("id", """[^/]+""",true))))
        

// @LINE:18
private[this] lazy val controllers_Assets_at7 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
        
def documentation = List(("""GET""", prefix,"""controllers.Application.index()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """projects""","""controllers.Application.projects()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """project/$name<[^/]+>""","""controllers.Application.getProject(name:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """search""","""controllers.Application.search()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/projects""","""controllers.ClientAPIController.projects()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/project/$name<[^/]+>""","""controllers.ClientAPIController.getProject(name:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/project/$name<[^/]+>/metric/$id<[^/]+>""","""controllers.ClientAPIController.getMetric(name:String, id:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]] 
}}
       
    
def routes:PartialFunction[RequestHeader,Handler] = {        

// @LINE:6
case controllers_Application_index0(params) => {
   call { 
        invokeHandler(controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Nil,"GET", """ Home page""", Routes.prefix + """"""))
   }
}
        

// @LINE:7
case controllers_Application_projects1(params) => {
   call { 
        invokeHandler(controllers.Application.projects(), HandlerDef(this, "controllers.Application", "projects", Nil,"GET", """""", Routes.prefix + """projects"""))
   }
}
        

// @LINE:8
case controllers_Application_getProject2(params) => {
   call(params.fromPath[String]("name", None)) { (name) =>
        invokeHandler(controllers.Application.getProject(name), HandlerDef(this, "controllers.Application", "getProject", Seq(classOf[String]),"GET", """""", Routes.prefix + """project/$name<[^/]+>"""))
   }
}
        

// @LINE:9
case controllers_Application_search3(params) => {
   call { 
        invokeHandler(controllers.Application.search(), HandlerDef(this, "controllers.Application", "search", Nil,"GET", """""", Routes.prefix + """search"""))
   }
}
        

// @LINE:11
case controllers_ClientAPIController_projects4(params) => {
   call { 
        invokeHandler(controllers.ClientAPIController.projects(), HandlerDef(this, "controllers.ClientAPIController", "projects", Nil,"GET", """""", Routes.prefix + """api/projects"""))
   }
}
        

// @LINE:12
case controllers_ClientAPIController_getProject5(params) => {
   call(params.fromPath[String]("name", None)) { (name) =>
        invokeHandler(controllers.ClientAPIController.getProject(name), HandlerDef(this, "controllers.ClientAPIController", "getProject", Seq(classOf[String]),"GET", """""", Routes.prefix + """api/project/$name<[^/]+>"""))
   }
}
        

// @LINE:13
case controllers_ClientAPIController_getMetric6(params) => {
   call(params.fromPath[String]("name", None), params.fromPath[String]("id", None)) { (name, id) =>
        invokeHandler(controllers.ClientAPIController.getMetric(name, id), HandlerDef(this, "controllers.ClientAPIController", "getMetric", Seq(classOf[String], classOf[String]),"GET", """""", Routes.prefix + """api/project/$name<[^/]+>/metric/$id<[^/]+>"""))
   }
}
        

// @LINE:18
case controllers_Assets_at7(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
   }
}
        
}
    
}
        