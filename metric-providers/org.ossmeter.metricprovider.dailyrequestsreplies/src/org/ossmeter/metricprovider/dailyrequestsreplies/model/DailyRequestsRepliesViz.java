 
package org.ossmeter.metricprovider.dailyrequestsreplies.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class DailyRequestsRepliesViz extends PongoViz {

	public DailyRequestsRepliesViz() {
		super();
	}
	
	DailyRequestsReplies db;
	
	public void setProjectDB(DB projectDB) {
//		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.dailyrequestsreplies");

		db = new DailyRequestsReplies(projectDB);
	}

	@Override
	public String getViz(String type) {
			
			if( "json".equals(type)) {
				String table = "";
			
				for (DayArticles day : db.getDayArticles()) {
					table += "{ 'Series' : 'Articles', 'Day' : '" + day.getName() + "', 'Quantity' : " + day.getNumberOfArticles() + " },";
				}
				for (DayRequests day : db.getDayRequests()) {
					table += "{ 'Series' : 'Requests', 'Day' : '" + day.getName() + "', 'Quantity' : " + day.getNumberOfRequests() + " },";
				}
				for (DayReplies day : db.getDayReplies()) {
					table += "{ 'Series' : 'Replies', 'Day' : '" + day.getName() + "', 'Quantity' : " + day.getNumberOfReplies()+ " },";
				}
				table = table.substring(0, table.lastIndexOf(","));
				table += "]";
				
				return ("{ 'id' : 'dailyrequestsreplies', 'name' : 'Daily requests/replies', 'type' : 'bar', " +
						"'description' : 'Foo bar.', " +
						"'xtext' : 'Day', 'ytext':'Quantity', 'orderRule' : ['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday'], " +
						"'datatable' : " + table + "," +
						"'isTimeSeries':false, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
			} else if ("csv".equals(type)) {
				String table = "Series, Day, Quantity\n";
				
				for (DayArticles day : db.getDayArticles()) {
					table += "Articles," + day.getName() + "," + day.getNumberOfArticles() + "\n";
				}
				for (DayRequests day : db.getDayRequests()) {
					table += "Requests," + day.getName() + "," + day.getNumberOfRequests() + "\n";
				}
				for (DayReplies day : db.getDayReplies()) {
					table += "Replies," + day.getName() + "," + day.getNumberOfReplies() + "\n";
				}
				return table;
			}
			return null;
			
	}
	
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Subversion");
		
		DailyRequestsRepliesViz viz = new DailyRequestsRepliesViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}
}
