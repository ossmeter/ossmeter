 
package org.ossmeter.metricprovider.generic.numberofresolvedclosedbugzillabugs.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.googlecode.pongo.runtime.viz.PongoViz.DateFilter;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyNorcbViz extends PongoViz {

	public DailyNorcbViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofresolvedclosedbugzillabugs");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
		case "csv": 
			return createCSVDataTable("bugzillas", "_id", "__date", "numberOfResolvedClosedBugs", "Date", "Resolved or Closed Bugs", DateFilter.DAY);
		case "json":
			return ("{ 'id' : 'resolvedclosedbugs', 'name' : 'Resolved or Closed Bugs/day', 'type' : 'line', " +
					"'description' : 'This metric shows the number of resolved or closed bugs in the Bugzilla(s) per day.', " +
					"'xtext' : 'Date', 'ytext':'Bugs', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("bugzillas", "_id", "__date", "numberOfResolvedClosedBugs", "Date", "Bugs", DateFilter.DAY) + "," +
					"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("modelingtmfxtext");
			
		DailyNorcbViz viz = new DailyNorcbViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("json"));
	}
}
