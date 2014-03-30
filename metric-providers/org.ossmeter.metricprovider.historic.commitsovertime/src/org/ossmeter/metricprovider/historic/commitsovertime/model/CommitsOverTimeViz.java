package org.ossmeter.metricprovider.historic.commitsovertime.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class CommitsOverTimeViz extends PongoViz {

	public CommitsOverTimeViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.historic.commitsovertime.CommitsOverTimeHistoricMetricProvider");
	}
	
	@Override
	public String getViz(String type) {
		switch (type) {
			case "json":
				return ("{ 'id' : 'commitsovertime', 'name' : 'Commits', 'type' : 'line', " +
						"'description' : 'The total number of commits over time.', " +
						"'xtext' : 'Date', 'ytext':'Commits', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("repositories", "url", "__date", "numberOfCommits", "Date", "Commits", DateFilter.MONTH) + "," +
						"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");				
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Log4J");
			
		CommitsOverTimeViz viz = new CommitsOverTimeViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}
}
