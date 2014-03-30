 
package org.ossmeter.metricprovider.generic.numberofworksformebugzillabugs.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyNowfmbViz extends PongoViz {

	public DailyNowfmbViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofworksformebugzillabugs");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
		case "csv": 
			return createCSVDataTable("bugzillas", "_id", "__date", "numberOfWorksForMeBugs", "Date", "Bugs", DateFilter.DAY);
		case "json":
			return ("{ 'id' : 'resolvedclosedbugs', 'name' : 'Works-for-me bugs/day', 'type' : 'line', " +
					"'description' : 'This metric shows the number of works-for-me bugs in the Bugzilla(s) per day.', " +
					"'xtext' : 'Date', 'ytext':'Bugs', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("bugzillas", "_id", "__date", "numberOfWorksForMeBugs", "Date", "Bugs", DateFilter.DAY) + "," +
					"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("modelingtmfxtext");
			
		DailyNowfmbViz viz = new DailyNowfmbViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("json"));
	}
}
