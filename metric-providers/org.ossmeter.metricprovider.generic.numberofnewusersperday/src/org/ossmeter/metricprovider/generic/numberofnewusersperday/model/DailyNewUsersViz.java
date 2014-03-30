 
package org.ossmeter.metricprovider.generic.numberofnewusersperday.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.googlecode.pongo.runtime.viz.PongoViz.DateFilter;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyNewUsersViz extends PongoViz {

	public DailyNewUsersViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofnewusersperday");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "csv": 
				return createCSVDataTable("newsgroups", "_id", "__date", "numberOfNewUsers", "Date", "New Users", DateFilter.DAY);
			case "json":
				return ("{ 'id' : 'newusersperday', 'name' : 'New Users', 'type' : 'line', " +
						"'description' : 'A measurement of the number of new users of the newsgroup.', " +
						"'xtext' : 'Date', 'ytext':'New Users', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("newsgroups", "_id", "__date", "numberOfNewUsers", "Date", "New Users", DateFilter.DAY) + "," +
						"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
			}	
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Subversion");
			
		DailyNewUsersViz viz = new DailyNewUsersViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}
}
