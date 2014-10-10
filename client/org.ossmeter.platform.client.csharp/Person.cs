using System;
using System.Net;
using System.IO;
using Newtonsoft.Json;
using System.Collections.Generic;
using Newtonsoft.Json.Linq;

namespace ossmeterclient
{
	public class Person : NamedElement 
	{

		protected IList<Role> roles { get; set; }
		protected string homePage { get; set; }
	}
}
