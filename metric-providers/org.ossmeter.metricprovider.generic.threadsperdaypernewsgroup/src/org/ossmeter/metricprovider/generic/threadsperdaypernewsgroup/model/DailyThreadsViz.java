 
package org.ossmeter.metricprovider.generic.threadsperdaypernewsgroup.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyThreadsViz extends PongoViz {

	public DailyThreadsViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.threadsperdaypernewsgroup");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "json":
				return ("{ 'id' : 'threadspernewsgroup', 'name' : 'Threads', 'type' : 'line', " +
						"'description' : 'Historical view of the total number of threads in the newsgroup.', " +
						"'xtext' : 'Date', 'ytext':'Threads', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("newsgroups", "_id", "__date", "numberOfThreads", "Date", "Threads", DateFilter.MONTH) + "," +
						"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
			
			case "csv":
				return createCSVDataTable("newsgroups", "_id", "__date", "numberOfThreads", "Date", "Threads", DateFilter.DAY);
		}	
		return null;
	}
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Subversion");
			
		DailyThreadsViz viz = new DailyThreadsViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}
}
