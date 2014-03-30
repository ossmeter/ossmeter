 
package org.ossmeter.metricprovider.generic.numberofduplicatebugzillabugs.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyNodbViz extends PongoViz {

	public DailyNodbViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofduplicatebugzillabugs");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
		case "csv": 
			return createCSVDataTable("bugzillas", "_id", "__date", "numberOfDuplicateBugs", "Date", "Duplicate Bugs", DateFilter.DAY);
		case "json":
			return ("{ 'id' : 'duplicatebugs', 'name' : 'Duplicate Bugs/day', 'type' : 'line', " +
					"'description' : 'This metric shows the number of duplicate bugs in the Bugzilla(s) per day.', " +
					"'xtext' : 'Date', 'ytext':'Bugs', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("bugzillas", "_id", "__date", "numberOfDuplicateBugs", "Date", "Bugs", DateFilter.DAY) + "," +
					"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("modelingtmfxtext");
			
		DailyNodbViz viz = new DailyNodbViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("json"));
	}
}
