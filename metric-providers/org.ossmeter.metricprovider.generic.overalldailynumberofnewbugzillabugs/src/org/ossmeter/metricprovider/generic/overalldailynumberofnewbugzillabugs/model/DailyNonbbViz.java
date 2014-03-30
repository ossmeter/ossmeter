 
package org.ossmeter.metricprovider.generic.overalldailynumberofnewbugzillabugs.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyNonbbViz extends PongoViz {

	public DailyNonbbViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.overalldailynumberofnewbugzillabugs");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
				String dataTable = createDataTable("bugzillas", "", "__date", "numberOfBugs");
				return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Number of new bugs' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");	
			case "json":
				return ("{ 'id' : 'dailynewbugzillabugs', 'name' : 'New bugs/day', 'type' : 'bar', " +
						"'description' : 'The number of new bug patches committed per day.', " +
						"'xtext' : 'Date', 'ytext':'Bugs', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("bugzillas", "_id", "__date", "numberOfBugs", "Date", "Bugs", DateFilter.DAY) + "," +
						"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		}
		return null;
	}
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Tomcat");
			
		DailyNonbbViz viz = new DailyNonbbViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}
}
