package org.ossmeter.metricprovider.trans.dailycommits.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyCommitsViz extends PongoViz {

	public DailyCommitsViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("DailyCommits.days");
	}
	
	@Override
	public String getViz(String type) {
		switch (type) {
			case "json":
				return ("{ 'id' : 'dailycommits', 'name' : 'Daily commits', 'type' : 'bar', " +
						"'description' : 'Source code commits grouped by the day they occurred.', " +
						"'xtext' : 'Day', 'ytext':'Commits', 'orderRule' : ['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday'], " +
						"'datatable' : " + createD3DataTable(null, null, "name", "numberOfCommits","Day", "Commits", DateFilter.NONE) + "," +
						"'isTimeSeries':false, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");				
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Log4J");
			
		DailyCommitsViz viz = new DailyCommitsViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}
}
