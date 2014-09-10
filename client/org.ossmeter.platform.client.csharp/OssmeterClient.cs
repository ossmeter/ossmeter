using System;
using System.Net;
using System.IO;
using Newtonsoft.Json;
using System.Collections.Generic;
using Newtonsoft.Json.Linq;


namespace ossmeterclient
{
	class OssmeterClient 
	{
		protected readonly string connectionUrl;

		public OssmeterClient(string connectionUrl) 
		{
			this.connectionUrl = connectionUrl;
			
			// TODO: Test connection
		}
		public IList<Project> getProjectList()
		{
			string result = makeRequest(connectionUrl + "/p");
			return JsonConvert.DeserializeObject<IList<Project>> (result, new JsonEclipseProjectConverter());
		}
		public Project getProject(string projectId)
		{
			string result = makeRequest(connectionUrl + "/p/" + projectId + "");
			return JsonConvert.DeserializeObject<Project> (result, new JsonEclipseProjectConverter());
		}
		public Metric getMetric(string projectId, string metricId)
		{
			string result = makeRequest(connectionUrl + "/p/" + projectId + "/m/" + metricId + "");
			return JsonConvert.DeserializeObject<Metric> (result);
		}
		public MetricVisualisation getMetricVisualisation(string projectId, string metricId)
		{
			string result = makeRequest(connectionUrl + "/p/" + projectId + "/v/" + metricId + "");
			return JsonConvert.DeserializeObject<MetricVisualisation> (result);
		}
		
		protected string makeRequest(string urlString) 
		{
			HttpWebRequest req = (HttpWebRequest)WebRequest.Create (urlString);
			HttpWebResponse res = (HttpWebResponse)req.GetResponse ();

			Stream stream = res.GetResponseStream ();
			StreamReader reader = new StreamReader (stream);

			return reader.ReadToEnd ();
		}
	}
	
	
	/**
	 *	This is a test method to ensure that the generated client works ok.
	 */
	public static void Main (string[] args)
		OssmeterClient c = new OssmeterClient("http://localhost:1234");
		
		System.out.println(c.getProjectList());
		
		Project p = c.getProject("epsilon");
		Metric m2 = c.getMetric(p.getShortName(), "activeusersperday");
		
	}
}