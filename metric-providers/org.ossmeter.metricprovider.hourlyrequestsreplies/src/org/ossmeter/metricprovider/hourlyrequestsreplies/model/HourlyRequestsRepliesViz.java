 
package org.ossmeter.metricprovider.hourlyrequestsreplies.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class HourlyRequestsRepliesViz extends PongoViz {

	HourlyRequestsReplies db;
	
	public HourlyRequestsRepliesViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		db = new HourlyRequestsReplies(projectDB);
	}

	@Override
	public String getViz(String type) {
			if("json".equals(type)) { 
	
				String table = "[";
				
//				for (HourArticles hour : db.getHourArticles()) {
//					table += "{ 'Series' : 'Articles', 'Hour' : '" + hour.getHour() + "', 'Quantity' : " + hour.getNumberOfArticles() + " },";
//				}
				for (HourRequests hour : db.getHourRequests()) {
					table += "{ 'Series' : 'Requests', 'Hour' : '" + hour.getHour() + "', 'Quantity' : " + hour.getNumberOfRequests() + " },";
				}
				for (HourReplies hour : db.getHourReplies()) {
					table += "{ 'Series' : 'Replies', 'Hour' : '" + hour.getHour() + "', 'Quantity' : " + hour.getNumberOfReplies()+ " },";
				}
				table = table.substring(0, table.lastIndexOf(","));
				table += "]";
				
				return ("{ 'id' : 'hourlyrequestsreplies', 'name' : 'Hourly requests/replies', 'type' : 'bar', " +
						"'description' : 'Foo bar.', " +
						"'xtext' : 'Hour', 'ytext':'Quantity','series' : 'Series', 'orderRule' : ['00:00','01:00','02:00','03:00','04:00','05:00','06:00','07:00','08:00','09:00','10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00','19:00','20:00','21:00','22:00','23:00'], " +
						"'datatable' : " + table + "," +
						"'isTimeSeries':false, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		} else if("csv".equals(type)) {
			String table = "Series, Hour, Quantity\n";
			
			for (HourArticles day : db.getHourArticles()) {
				table += "Articles," + day.getHour() + "," + day.getNumberOfArticles() + "\n";
			}
			for (HourRequests day : db.getHourRequests()) {
				table += "Requests," + day.getHour() + "," + day.getNumberOfRequests() + "\n";
			}
			for (HourReplies day : db.getHourReplies()) {
				table += "Replies," + day.getHour() + "," + day.getNumberOfReplies() + "\n";
			}
			return table;
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("modelingtmfxtext");
		
		HourlyRequestsRepliesViz viz = new HourlyRequestsRepliesViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("json"));
	}
}
