 
package org.ossmeter.metricprovider.generic.numberofnonresolvedclosedbugzillabugs.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyNonrcbViz extends PongoViz {

	public DailyNonrcbViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofnonresolvedclosedbugzillabugs");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
		case "csv": 
			return createCSVDataTable("bugzillas", "_id", "__date", "numberOfNonResolvedClosedBugs", "Date", "Non-resolved or Closed Bugs", DateFilter.DAY);
		case "json":
			return ("{ 'id' : 'nonresolvedclosedbugs', 'name' : 'Resolved or Closed Bugs/day', 'type' : 'line', " +
					"'description' : 'This metric shows the number of non-resolved or closed bugs in the Bugzilla(s) per day.', " +
					"'xtext' : 'Date', 'ytext':'Bugs', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("bugzillas", "_id", "__date", "numberOfNonResolvedClosedBugs", "Date", "Bugs", DateFilter.DAY) + "," +
					"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("modelingtmfxtext");
			
		DailyNonrcbViz viz = new DailyNonrcbViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("json"));
	}
}
