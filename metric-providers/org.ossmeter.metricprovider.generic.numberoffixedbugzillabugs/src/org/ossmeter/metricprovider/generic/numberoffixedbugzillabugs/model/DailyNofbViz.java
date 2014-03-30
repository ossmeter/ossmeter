 
package org.ossmeter.metricprovider.generic.numberoffixedbugzillabugs.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyNofbViz extends PongoViz {

	public DailyNofbViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberoffixedbugzillabugs");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
		case "csv": 
			return createCSVDataTable("bugzillas", "_id", "__date", "numberOfFixedBugs", "Date", "Fixed Bugs", DateFilter.DAY);
		case "json":
			return ("{ 'id' : 'fixedbugs', 'name' : 'Fixed Bugs/day', 'type' : 'line', " +
					"'description' : 'This metric shows the number of fixed bugs in the Bugzilla(s) per day.', " +
					"'xtext' : 'Date', 'ytext':'Bugs', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("bugzillas", "_id", "__date", "numberOfFixedBugs", "Date", "Bugs", DateFilter.DAY) + "," +
					"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("modelingtmfxtext");
			
		DailyNofbViz viz = new DailyNofbViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("json"));
	}
}
