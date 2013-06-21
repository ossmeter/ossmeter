// @SOURCE:/Users/nikos/Desktop/eclipseOss/workspace/org.ossmeter.webapp/conf/routes
// @HASH:e3a421f716c1d07de107a8a71d53a6d1084d4efd
// @DATE:Fri Jun 21 11:49:04 BST 2013

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.j._
import java.net.URLEncoder

import play.api.mvc._
import play.libs.F

import Router.queryString


// @LINE:18
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers {

// @LINE:13
// @LINE:12
// @LINE:11
class ReverseClientAPIController {
    

// @LINE:13
def getMetric(name:String, id:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "api/project/" + implicitly[PathBindable[String]].unbind("name", URLEncoder.encode(name, "utf-8")) + "/metric/" + implicitly[PathBindable[String]].unbind("id", URLEncoder.encode(id, "utf-8")))
}
                                                

// @LINE:12
def getProject(name:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "api/project/" + implicitly[PathBindable[String]].unbind("name", URLEncoder.encode(name, "utf-8")))
}
                                                

// @LINE:11
def projects(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "api/projects")
}
                                                
    
}
                          

// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {
    

// @LINE:9
def search(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "search")
}
                                                

// @LINE:8
def getProject(name:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "project/" + implicitly[PathBindable[String]].unbind("name", URLEncoder.encode(name, "utf-8")))
}
                                                

// @LINE:6
def index(): Call = {
   Call("GET", _prefix)
}
                                                

// @LINE:7
def projects(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "projects")
}
                                                
    
}
                          

// @LINE:18
class ReverseAssets {
    

// @LINE:18
def at(file:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                
    
}
                          
}
                  


// @LINE:18
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers.javascript {

// @LINE:13
// @LINE:12
// @LINE:11
class ReverseClientAPIController {
    

// @LINE:13
def getMetric : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ClientAPIController.getMetric",
   """
      function(name,id) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "api/project/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("name", encodeURIComponent(name)) + "/metric/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("id", encodeURIComponent(id))})
      }
   """
)
                        

// @LINE:12
def getProject : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ClientAPIController.getProject",
   """
      function(name) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "api/project/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("name", encodeURIComponent(name))})
      }
   """
)
                        

// @LINE:11
def projects : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ClientAPIController.projects",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "api/projects"})
      }
   """
)
                        
    
}
              

// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {
    

// @LINE:9
def search : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.search",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "search"})
      }
   """
)
                        

// @LINE:8
def getProject : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.getProject",
   """
      function(name) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "project/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("name", encodeURIComponent(name))})
      }
   """
)
                        

// @LINE:6
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + """"})
      }
   """
)
                        

// @LINE:7
def projects : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.projects",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "projects"})
      }
   """
)
                        
    
}
              

// @LINE:18
class ReverseAssets {
    

// @LINE:18
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        
    
}
              
}
        


// @LINE:18
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers.ref {

// @LINE:13
// @LINE:12
// @LINE:11
class ReverseClientAPIController {
    

// @LINE:13
def getMetric(name:String, id:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ClientAPIController.getMetric(name, id), HandlerDef(this, "controllers.ClientAPIController", "getMetric", Seq(classOf[String], classOf[String]), "GET", """""", _prefix + """api/project/$name<[^/]+>/metric/$id<[^/]+>""")
)
                      

// @LINE:12
def getProject(name:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ClientAPIController.getProject(name), HandlerDef(this, "controllers.ClientAPIController", "getProject", Seq(classOf[String]), "GET", """""", _prefix + """api/project/$name<[^/]+>""")
)
                      

// @LINE:11
def projects(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ClientAPIController.projects(), HandlerDef(this, "controllers.ClientAPIController", "projects", Seq(), "GET", """""", _prefix + """api/projects""")
)
                      
    
}
                          

// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {
    

// @LINE:9
def search(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.search(), HandlerDef(this, "controllers.Application", "search", Seq(), "GET", """""", _prefix + """search""")
)
                      

// @LINE:8
def getProject(name:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.getProject(name), HandlerDef(this, "controllers.Application", "getProject", Seq(classOf[String]), "GET", """""", _prefix + """project/$name<[^/]+>""")
)
                      

// @LINE:6
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Seq(), "GET", """ Home page""", _prefix + """""")
)
                      

// @LINE:7
def projects(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.projects(), HandlerDef(this, "controllers.Application", "projects", Seq(), "GET", """""", _prefix + """projects""")
)
                      
    
}
                          

// @LINE:18
class ReverseAssets {
    

// @LINE:18
def at(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      
    
}
                          
}
                  
      