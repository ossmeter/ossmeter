 
package org.ossmeter.metricprovider.generic.numberofactiveusersperday.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyActiveUsersViz extends PongoViz {

	public DailyActiveUsersViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofactiveusersperday");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "csv": 
				return createCSVDataTable("newsgroups", "_id", "__date", "numberOfActiveUsers", "Date", "Active Users", DateFilter.DAY);
			case "json":
				return ("{ 'id' : 'activeusersperday', 'name' : 'Active Users', 'type' : 'line', " +
						"'description' : 'A measurement of the number of active daily users of the newsgroup.', " +
						"'xtext' : 'Date', 'ytext':'Active Users', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("newsgroups", "_id", "__date", "numberOfActiveUsers", "Date", "Active Users", DateFilter.DAY) + "," +
						"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
			}
		
		return null;
	}

	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Subversion");
			
		DailyActiveUsersViz viz = new DailyActiveUsersViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}
}
