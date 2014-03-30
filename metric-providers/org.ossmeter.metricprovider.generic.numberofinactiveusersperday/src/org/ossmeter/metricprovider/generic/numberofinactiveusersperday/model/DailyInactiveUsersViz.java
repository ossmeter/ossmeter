 
package org.ossmeter.metricprovider.generic.numberofinactiveusersperday.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.googlecode.pongo.runtime.viz.PongoViz.DateFilter;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyInactiveUsersViz extends PongoViz {

	public DailyInactiveUsersViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofinactiveusersperday");
	}


	@Override
	public String getViz(String type) {
		switch (type) {
			case "csv": 
				return createCSVDataTable("newsgroups", "_id", "__date", "numberOfInactiveUsers", "Date", "Inactive Users", DateFilter.DAY);
			case "json":
				return ("{ 'id' : 'inactiveusersperday', 'name' : 'Inactive Users', 'type' : 'line', " +
						"'description' : 'A measurement of the number of inactive daily users of the newsgroup.', " +
						"'xtext' : 'Date', 'ytext':'Inactive Users', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("newsgroups", "_id", "__date", "numberOfInactiveUsers", "Date", "Inactive Users", DateFilter.DAY) + "," +
						"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Subversion");
			
		DailyInactiveUsersViz viz = new DailyInactiveUsersViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}
}
