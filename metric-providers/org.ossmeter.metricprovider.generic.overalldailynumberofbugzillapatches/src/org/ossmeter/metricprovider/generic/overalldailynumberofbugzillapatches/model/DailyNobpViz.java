 
package org.ossmeter.metricprovider.generic.overalldailynumberofbugzillapatches.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyNobpViz extends PongoViz {

	public DailyNobpViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.overalldailynumberofbugzillapatches");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "json":
				return ("{ 'id' : 'dailybugzillapatches', 'name' : 'Bug patches/day', 'type' : 'bar', " +
						"'description' : 'A measure of the number of bug comments submitted by day.', " +
						"'xtext' : 'Date', 'ytext':'Patches', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("bugzillas", "_id", "__date", "numberOfPatches", "Date", "Patches", DateFilter.DAY) + "," +
						"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		}
		return null;
	}
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Subversive");
			
		DailyNobpViz viz = new DailyNobpViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}
}
