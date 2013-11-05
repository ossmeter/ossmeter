 
package org.ossmeter.metricprovider.generic.numberofactiveusersperday.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyActiveUsersViz extends PongoViz {

	public DailyActiveUsersViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofactiveusersperday");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
				String dataTable = createDataTable("newsgroups", "", "__date", "numberOfActiveUsers");
				return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Number of new active users' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		
			case "d3":
				return ("{ 'id' : 'activeusersperday', 'name' : 'Active Users', 'type' : 'line', " +
						"'description' : 'A measurement of the number of active daily users of the newsgroup.', " +
						"'xtext' : 'Date', 'ytext':'Active Users', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("newsgroups", "_id", "__date", "numberOfActiveUsers", "Date", "Active Users", DateFilter.DAY) + "," +
						"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		}
		
		
		return null;
	}

	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Subversive");
			
		DailyActiveUsersViz viz = new DailyActiveUsersViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}

}
