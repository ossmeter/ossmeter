using System;
using System.Net;
using System.IO;
using Newtonsoft.Json;
using System.Collections.Generic;
using Newtonsoft.Json.Linq;

namespace ossmeterclient
{
	public abstract class VcsRepository : NamedElement 
	{

		protected IList<Person> persons { get; set; }
		protected string created_at { get; set; }
		protected string updated_at { get; set; }
		protected string url { get; set; }
	}
}
