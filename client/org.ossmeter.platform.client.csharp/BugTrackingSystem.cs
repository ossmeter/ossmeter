using System;
using System.Net;
using System.IO;
using Newtonsoft.Json;
using System.Collections.Generic;
using Newtonsoft.Json.Linq;

namespace ossmeterclient
{
	abstract class BugTrackingSystem  
	{

		protected IList<Person> persons { get; set; }
		protected string url { get; set; }
	}
}
