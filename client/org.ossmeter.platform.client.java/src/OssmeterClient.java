
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class OssmeterClient {
	final String connectionUrl;
	final ObjectMapper mapper;
	
	public OssmeterClient(String connectionUrl) throws Exception {
		this.connectionUrl = connectionUrl;
		mapper = new ObjectMapper();
		
		// Test connection
		URL url = new URL(connectionUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() != 200) {
			conn.disconnect();
			throw new Exception("Unable to connect to OSSMETER REST API. Check URL.");
		}
		conn.disconnect();
	}
	public List<Project> getProjectList() throws Exception {
		String result = makeRequest(connectionUrl + "/p");
		return mapper.readValue(result, TypeFactory.defaultInstance().constructCollectionType(List.class, Project.class));
	}
	public Project getProject(String projectId) throws Exception {
		String result = makeRequest(connectionUrl + "/p/" + projectId + "");
		return mapper.readValue(result, Project.class);
	}
	public Metric getMetric(String projectId, String metricId) throws Exception {
		String result = makeRequest(connectionUrl + "/p/" + projectId + "/m/" + metricId + "");
		return mapper.readValue(result, Metric.class);
	}
	public MetricVisualisation getMetricVisualisation(String projectId, String metricId) throws Exception {
		String result = makeRequest(connectionUrl + "/p/" + projectId + "/v/" + metricId + "");
		return mapper.readValue(result, MetricVisualisation.class);
	}
	
	public String makeRequest(String urlString) throws Exception {
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		
		conn.connect();
		
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		StringBuffer sb = new StringBuffer();
		
		String output;
		while ((output = br.readLine()) != null) {
			sb.append(output);
		}
		conn.disconnect();
		
		return sb.toString();
	}
	
	/**
	 *	This is a test method to ensure that the generated client works ok.
	 */
	public static void main(String[] args) throws Exception{
		OssmeterClient c = new OssmeterClient("http://localhost:1234");
		
		System.out.println(c.getProjectList());
		
		Project p = c.getProject("epsilon");
		Metric m2 = c.getMetric(p.getShortName(), "activeusersperday");
		
	}
}
