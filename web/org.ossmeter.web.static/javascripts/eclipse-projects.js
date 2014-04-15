var Projects = {
	"birt" : { "name" : "BIRT", "shortName" : "birt", "url" : "www.eclipse.org/birt", "desc" : "BIRT is an open source Eclipse-based reporting system that integrates with your Java/Java EE application to produce compelling reports." },
	"ecf" : { "name" : "ECF", "shortName" : "ecf", "url" : "www.eclipse.org/ecf", "desc" : "ECF is a set of frameworks for building distributed servers, applications, and tools. It provides a modular implementation of the OSGi 5 Remote Services standard, along with support for REST-based and other types of remote services." },
	"eclipseplatform" : { "name" : "Eclipse Platform", "shortName" : "eclipse-platform", "url" : "www.eclipse.org/eclipse", "desc" : "The Eclipse Platform defines the set of frameworks and common services that collectively make up \"integration-ware\" required to support the use of Eclipse as a component model, as a rich client platform (RCP) and as a comprehensive tool integration platform." },
	"ecoretools" : { "name" : "EcoreTools", "shortName" : "ecoretools", "url" : "www.eclipse.org/ecoretools/", "desc" : "EcoreTools 2 provides a complete and state of the art graphical modeler to create, edit and analyze Ecore models." },
	"egit" : { "name" : "EGit", "shortName" : "egit", "url" : "https://www.eclipse.org/egit/", "desc" : "EGit is an Eclipse Team provider for the Git version control system. Git is a distributed SCM, which means every developer has a full copy of all history of every revision of the code, making queries against the history very fast and versatile." },
	"emf" : { "name" : "EMF", "shortName" : "emf", "url" : "http://eclipse.org/modeling/emf/", "desc" : "The EMF project is a modeling framework and code generation facility for building tools and other applications based on a structured data model. From a model specification described in XMI, EMF provides tools and runtime support to produce a set of Java classes for the model, along with a set of adapter classes that enable viewing and command-based editing of the model, and a basic editor." },
	"emf-diffmerge" : { "name" : "EMF Diff/Merge", "shortName" : "diffmerge", "url" : "http://eclipse.org/diffmerge/", "desc" : "EMF Diff/Merge is a tool for merging models. More precisely, it is a technical component which helps create tools that need to merge models. It consists in a diff/merge engine that is designed to prevent data loss and enforce model consistency during merge, thanks to consistency rules - predefined and, if needed, user-defined. It also includes GUI components." },
	"epsilon" : { "name" : "Epsilon", "shortName" : "epsilon", "url" : "http://www.eclipse.org/epsilon", "desc" : "Epsilon is a family of languages and tools for code generation, model-to-model transformation, model validation, comparison, migration and refactoring that work out-of-the-box with EMF and other types of models."},
	"gmf-runtime" : {"name" : "GMF Runtime", "shortName" : "gmf-runtime", "url" : "https://www.eclipse.org/modeling/gmp/?project=gmf-runtime", "desc" : "The GMF runtime is an industry proven application framework for creating graphical editors using EMF and GEF."},
	"graphiti" : { "name" : "Graphiti", "shortName" : "graphiti", "url" : "https://www.eclipse.org/graphiti/", "desc" : "Graphiti is an Eclipse-based graphics framework that enables rapid development of state-of-the-art diagram editors for domain models. Graphiti can use EMF-based domain models very easily but can deal with any Java-based objects on the domain side as well."},
	"ocl" : { "name" : "OCL", "shortName" : "ocl", "url" : "www.eclipse.org/modeling/mdt/?project=ocl", "desc" : "OCL is an implementation of the Object Constraint Language (OCL) OMG standard for EMF-based models." },
	"pdt" : { "name" : "PHP Development Tools", "shortName" : "pdt", "url" : "www.eclipse.org/pdt", "desc" : "The PHP IDE project delivers a PHP Integrated Development Environment framework for the Eclipse platform. This project encompasses the development components necessary to develop PHP-based Web Applications and facilitates extensibility. It leverages the existing Web Tools Project in providing developers with PHP capabilities." },
	"swt" : { "name" : "SWT", "shortName" : "swt", "url" : "www.eclipse.org/swt", "desc" : "SWT is an open source widget toolkit for Java designed to provide efficient, portable access to the user-interface facilities of the operating systems on which it is implemented." },
	"swtbot" : { "name" : "SWTBot", "shortName" : "swtbot", "url" : "https://www.eclipse.org/swtbot/", "desc" : "SWTBot is an open-source Java based UI/functional testing tool for testing SWT, Eclipse and GEF based applications." },
	"uml2" : { "name" : "UML2","shortName" : "uml2", "url" : "www.eclipse.org/modeling/mdt/?project=uml2", "desc" : "UML2 is an EMF-based implementation of the Unified Modeling Language (UMLTM) 2.x OMG metamodel for the Eclipse platform." },
	"xsd" : { "name" : "XSD", "shortName" : "xsd", "url" : "http://www.eclipse.org/modeling/mdt/?project=xsd", "desc" : "XSD is a library that provides an API for manipulating the components of an XML Schema as described by the W3C XML Schema specifications, as well as an API for manipulating the DOM-accessible representation of XML Schema as a series of XML documents, and for keeping these representations in agreement as schemas are modified." },
	"xtext" : { "name" : "Xtext", "shortName" : "xtext", "url" : "https://www.eclipse.org/Xtext/", "desc" : "Xtext is a framework for development of programming languages and domain specific languages. It covers all aspects of a complete language infrastructure, from parsers, over linker, compiler or interpreter to fully-blown top-notch Eclipse IDE integration. It comes with good defaults for all these aspects and at the same time every single aspect can be tailored to your needs." }
}

var Metrics = [
	// { "id" : "articlesperday", "niceName" : "Articles/day"}, 
	// { "id" : "dailyarticles", "niceName" : "Articles"}, 
	// { "id" : "newthreadspernewsgroup", "niceName" : "New threads"},  
	// { "id" : "activeuserspernewsgroup", "niceName" : "Active users"}, 
	// { "id" : "avgresponsetimepernewsgroup", "niceName" : "Response time"},  

	{ "id" : "usersperday", "niceName" : "Users", 
		"desc" : "A measurement of the number of active daily users of the newsgroup." }, 
	{ "id" : "threadspernewsgroup", "niceName" : "Threads", 
		"desc" : "Historical view of the total number of threads in the newsgroup."},
	{ "id" : "requestsreplies", "niceName" : "Requests/replies", 
		"desc" : "A measure of the number of requests and replies per day posted on the newsgroup." }, 
	{ "id" : "activeusersperday", "niceName" : "Active users", 
		"desc" : "A measurement of the number of active daily users of the newsgroup." }, 
	{ "id" : "articlesrequestsrepliesperthread", "niceName" : "Average requests/replies", 
		"desc" : "A measure of the average number of requests and replies per thread posted on the newsgroup." }, 
	{ "id" : "dailyrequestsreplies", "niceName" : "Daily requests/replies", 
		"desc" : "This metric shows the day of the week that each request and reply was posted." }, 
	{ "id" : "hourlyrequestsreplies", "niceName" : "Hourly requests/replies", 
		"desc" : "This metric shows the hour of the day that each request and reply was posted." }
]