 
package org.ossmeter.metricprovider.generic.overalldailynumberofbugzillacomments.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyNobcViz extends PongoViz {

	public DailyNobcViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.overalldailynumberofbugzillacomments");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
				String dataTable = createDataTable("bugzillas", "", "__date", "numberOfBugs");
				return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Number of new bugs' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");	
			case "d3":
				return ("{ 'id' : 'dailybugzillacomments', 'name' : 'Bug comments/day', 'type' : 'bar', " +
						"'description' : 'A measure of the number of bug comments submitted by day.', " +
						"'xtext' : 'Date', 'ytext':'Comments', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("bugzillas", "_id", "__date", "numberOfComments", "Date", "Comments", DateFilter.DAY) + "," +
						"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		}
		return null;
	}
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Tomcat");
			
		DailyNobcViz viz = new DailyNobcViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}
}
