package org.ossmeter.metricprovider.generic.avgresponsetimeperdaypernewsgroup.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyAverageThreadResponseTimeViz extends PongoViz {

	public DailyAverageThreadResponseTimeViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.avgresponsetimeperdaypernewsgroup");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
		case "csv": 
			return createCSVDataTable("newsgroups", "_id", "__date", "numberOfArticles", "Date", "Articles", DateFilter.DAY);
		case "json":
			return ("{ 'id' : 'avgresponsetimepernewsgroup', 'name' : 'Articles/day', 'type' : 'line', " +
					"'description' : 'Average Thread Response Time Per Day Per Newsgroup.', " +
					"'xtext' : 'Date', 'ytext':'Average Response Time', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("newsgroups", "url_name", "__date", "avgResponseTime", "Date", "Time", DateFilter.DAY) + "," +
					"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("modelingtmfxtext");
			
		DailyAverageThreadResponseTimeViz viz = new DailyAverageThreadResponseTimeViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("json"));
	}
}
