 
package org.ossmeter.metricprovider.generic.numberofarticlesperdaypernewsgroup.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyNoaViz extends PongoViz {

	public DailyNoaViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofarticlesperdaypernewsgroup");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
		case "csv": 
			return createCSVDataTable("newsgroups", "_id", "__date", "numberOfArticles", "Date", "Articles", DateFilter.DAY);
		case "json":
			return ("{ 'id' : 'articlespernewsgroup', 'name' : 'Articles/day/newsgroup', 'type' : 'line', " +
					"'description' : 'This metric shows the number of articles posted in each newgroup(s) per day.', " +
					"'xtext' : 'Date', 'ytext':'Articles', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("newsgroups", "_id", "__date", "numberOfArticles", "Date", "Articles", DateFilter.DAY) + "," +
					"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		}
	return null;
}

public static void main(String[] args) throws Exception {
	Mongo mongo = new Mongo();
	DB db = mongo.getDB("modelingtmfxtext");
		
	DailyNoaViz viz = new DailyNoaViz();
	viz.setProjectDB(db);
	System.err.println(viz.getViz("json"));
}
}