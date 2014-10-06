using System;
using System.Net;
using System.IO;
using Newtonsoft.Json;
using System.Collections.Generic;
using Newtonsoft.Json.Linq;

namespace ossmeterclient
{
	public class Project : NamedElement 
	{

		protected IList<VcsRepository> vcsRepositories { get; set; }
		protected IList<CommunicationChannel> communicationChannels { get; set; }
		protected IList<BugTrackingSystem> bugTrackingSystems { get; set; }
		protected IList<Person> persons { get; set; }
		protected IList<License> licenses { get; set; }
		protected IList<MetricProvider> metricProviderData { get; set; }
		protected string shortName { get; set; }
		protected string description { get; set; }
		protected int year { get; set; }
		protected bool active { get; set; }
		protected string lastExecuted { get; set; }
		protected Project parent { get; set; }
	}
}
