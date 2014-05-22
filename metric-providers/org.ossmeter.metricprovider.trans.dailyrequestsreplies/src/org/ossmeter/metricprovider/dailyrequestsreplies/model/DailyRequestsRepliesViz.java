 
package org.ossmeter.metricprovider.dailyrequestsreplies.model;

import java.math.BigDecimal;

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
				String table = "[";
			
//				for (DayArticles day : db.getDayArticles()) {
//					table += "{ 'Series' : 'Articles', 'Day' : '" + day.getName() + "', 'Quantity' : " + day.getNumberOfArticles() + " },";
//				}
				double totalRequests = 0;
				double totalReplies = 0;
				for (DayRequests day : db.getDayRequests()) totalRequests += day.getNumberOfRequests();
				for (DayReplies day : db.getDayReplies()) totalReplies += day.getNumberOfReplies();
				
				for (DayRequests day : db.getDayRequests()) {
					table += "{ 'Series' : 'Requests', 'Day' : '" + day.getName() + "', 'Proportion' : " + round(((double)day.getNumberOfRequests())/totalRequests,2, BigDecimal.ROUND_HALF_UP) + " },";
				}
				for (DayReplies day : db.getDayReplies()) {
					table += "{ 'Series' : 'Replies', 'Day' : '" + day.getName() + "', 'Proportion' : " + round(((double)day.getNumberOfReplies())/totalReplies,2, BigDecimal.ROUND_HALF_UP) + " },";
				}
				table = table.substring(0, table.lastIndexOf(","));
				table += "]";
				
				return ("{ 'id' : 'dailyrequestsreplies', 'name' : 'Daily requests/replies', 'type' : 'bar', " +
						"'description' : 'The days on which each request, reply, and article take place.', " +
						"'xtext' : 'Day', 'ytext':'Proportion', 'tickFormat':',.2f', 'series' : 'Series', 'orderRule' : ['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday'], " +
						"'datatable' : " + table + "," +
						"'isTimeSeries':false, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
			} else if ("csv".equals(type)) {
				String table = "Series, Day, Quantity\n";
				
//				for (DayArticles day : db.getDayArticles()) {
//					table += "Articles," + day.getName() + "," + day.getNumberOfArticles() + "\n";
//				}
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
	
	public static double round(double unrounded, int precision, int roundingMode)
	{
	    BigDecimal bd = new BigDecimal(unrounded);
	    BigDecimal rounded = bd.setScale(precision, roundingMode);
	    return rounded.doubleValue();
	}
	
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("eclipseplatform");
		
		DailyRequestsRepliesViz viz = new DailyRequestsRepliesViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("json"));
	}
}
