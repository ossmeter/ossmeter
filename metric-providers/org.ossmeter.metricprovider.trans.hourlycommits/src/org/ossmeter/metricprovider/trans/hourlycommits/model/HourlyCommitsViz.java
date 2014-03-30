package org.ossmeter.metricprovider.trans.hourlycommits.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class HourlyCommitsViz extends PongoViz {

	public HourlyCommitsViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("HourlyCommits.hours");
	}
	
	@Override
	public String getViz(String type) {
		switch (type) {
			case "json":
				return ("{ 'id' : 'hourlycommits', 'name' : 'Hourly commits', 'type' : 'bar', " +
						"'description' : 'Source code commits grouped by the hour they occurred.', " +
						"'xtext' : 'Hour', 'ytext':'Commits', 'orderRule' : ['00:00','01:00','02:00','03:00','04:00','05:00','06:00','07:00','08:00','09:00','10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00','19:00','20:00','21:00','22:00','23:00'], " +
						"'datatable' : " + createD3DataTable(null, null, "hour", "numberOfCommits","Hour", "Commits", DateFilter.NONE) + "," +
						"'isTimeSeries':false, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");				
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Log4J");
			
		HourlyCommitsViz viz = new HourlyCommitsViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}
}
