 
package org.ossmeter.metricprovider.generic.overalldailynumberofarticles.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyNoaViz extends PongoViz {

	public DailyNoaViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.overalldailynumberofarticles");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
				String dataTable = createDataTable("newsgroups", "", "__date", "numberOfArticles");
				return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Number of articles' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		
			case "json":
				return ("{ 'id' : 'dailyarticles', 'name' : 'Articles', 'type' : 'line', " +
						"'description' : 'This metric keeps track of the number of newsgroup articles.', " +
						"'xtext' : 'Date', 'ytext':'Articles', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("newsgroups", "_id", "__date", "numberOfArticles", "Date", "Articles", DateFilter.DAY) + "," +
						"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");				
	}
		return null;
	}
	

	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Tomcat");
			
		DailyNoaViz viz = new DailyNoaViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}
}
