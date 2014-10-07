using System;
using System.Net;
using System.IO;
using Newtonsoft.Json;
using System.Collections.Generic;
using Newtonsoft.Json.Linq;

namespace ossmeterclient
{
	public class SchedulingInformation Object 
	{

		protected IList<string> currentLoad { get; set; }
		protected string workerIdentifier { get; set; }
		protected bool isMaster { get; set; }
	}
}
