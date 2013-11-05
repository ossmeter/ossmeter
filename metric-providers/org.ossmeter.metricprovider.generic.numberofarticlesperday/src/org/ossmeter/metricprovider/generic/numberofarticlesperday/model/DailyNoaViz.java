 
package org.ossmeter.metricprovider.generic.numberofarticlesperday.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyNoaViz extends PongoViz {

	public DailyNoaViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofarticlesperday");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
				String dataTable = createDataTable("newsgroups", "", "__date", "numberOfArticles");
				return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Number of new bugs' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");
			case "d3":
				return ("{ 'id' : 'articles', 'name' : 'Articles/day', 'type' : 'line', " +
						"'description' : 'This metric shows the number of articles posted in the newgroup(s) per day.', " +
						"'xtext' : 'Date', 'ytext':'Articles', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("newsgroups", "_id", "__date", "numberOfArticles", "Date", "Articles", DateFilter.DAY) + "," +
						"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
			}
		return null;
	}

	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Subversive");
			
		DailyNoaViz viz = new DailyNoaViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}
}
