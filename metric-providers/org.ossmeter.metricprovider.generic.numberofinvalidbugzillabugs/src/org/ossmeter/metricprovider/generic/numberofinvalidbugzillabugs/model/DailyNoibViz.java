 
package org.ossmeter.metricprovider.generic.numberofinvalidbugzillabugs.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyNoibViz extends PongoViz {

	public DailyNoibViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofinvalidbugzillabugs");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
		case "csv": 
			return createCSVDataTable("bugzillas", "_id", "__date", "numberOfInvalidBugs", "Date", "Invalid Bugs", DateFilter.DAY);
		case "json":
			return ("{ 'id' : 'invalidbugs', 'name' : 'Invalid Bugs/day', 'type' : 'line', " +
					"'description' : 'This metric shows the number of invalid bugs in the Bugzilla(s) per day.', " +
					"'xtext' : 'Date', 'ytext':'Bugs', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("bugzillas", "_id", "__date", "numberOfInvalidBugs", "Date", "Bugs", DateFilter.DAY) + "," +
					"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("modelingtmfxtext");
			
		DailyNoibViz viz = new DailyNoibViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("json"));
	}
}
