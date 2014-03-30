 
package org.ossmeter.metricprovider.generic.numberofactiveusersperdaypernewsgroup.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyActiveUsersViz extends PongoViz {

	public DailyActiveUsersViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofactiveusersperdaypernewsgroup");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
		case "csv": 
			return createCSVDataTable("newsgroups", "url_name", "__date", "numberOfActiveUsers", "Date", "Users", DateFilter.DAY);
		case "json":
			return ("{ 'id' : 'activeuserspernewsgroup', 'name' : 'New Users/day', 'type' : 'line', " +
					"'description' : 'This metric shows the number of active users of the newgroup(s) per day.', " +
					"'xtext' : 'Date', 'ytext':'Users', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("newsgroups", "url_name", "__date", "numberOfActiveUsers", "Date", "Users", DateFilter.DAY) + "," +
					"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		}
	return null;
}

public static void main(String[] args) throws Exception {
	Mongo mongo = new Mongo();
	DB db = mongo.getDB("modelingtmfxtext");
		
	DailyActiveUsersViz viz = new DailyActiveUsersViz();
	viz.setProjectDB(db);
	System.err.println(viz.getViz("json"));
}
}
