 
package org.ossmeter.metricprovider.generic.numberofwontfixbugzillabugs.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyNowfbViz extends PongoViz {

	public DailyNowfbViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofwontfixbugzillabugs");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
		case "csv": 
			return createCSVDataTable("bugzillas", "_id", "__date", "numberOfWontFixBugs", "Date", "Won't fix bugs", DateFilter.DAY);
		case "json":
			return ("{ 'id' : 'wontfixbugs', 'name' : 'Won't fix bugs/day', 'type' : 'line', " +
					"'description' : 'This metric shows the number of won't fix bugs in the Bugzilla(s) per day.', " +
					"'xtext' : 'Date', 'ytext':'Bugs', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("bugzillas", "_id", "__date", "numberOfWontFixBugs", "Date", "Bugs", DateFilter.DAY) + "," +
					"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("modelingtmfxtext");
			
		DailyNowfbViz viz = new DailyNowfbViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("json"));
	}
}
