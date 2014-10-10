using System;
using System.Net;
using System.IO;
using Newtonsoft.Json;
using System.Collections.Generic;
using Newtonsoft.Json.Linq;

namespace ossmeterclient
{
	public class MetricProvider : NamedElement 
	{

		protected string metricProviderId { get; set; }
		protected MetricProviderType type { get; set; }
		protected MetricProviderCategory category { get; set; }
	}
}
