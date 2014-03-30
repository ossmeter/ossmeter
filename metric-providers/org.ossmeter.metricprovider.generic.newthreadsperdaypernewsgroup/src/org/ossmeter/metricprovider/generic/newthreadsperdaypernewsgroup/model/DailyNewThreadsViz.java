 
package org.ossmeter.metricprovider.generic.newthreadsperdaypernewsgroup.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.googlecode.pongo.runtime.viz.PongoViz.DateFilter;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyNewThreadsViz extends PongoViz {

	public DailyNewThreadsViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.newthreadsperdaypernewsgroup");
	}

	@Override
	public String getViz(String type) {
		if("json".equals(type)) { 
				String dataTable = createD3DataTable("newsgroups", "url_name", "__date", "numberOfNewThreads", "Date","New Threads", DateFilter.DAY);

				return ("{ 'id' : 'newthreadsperdaypernewsgroup', 'name' : 'New Threads', 'type' : 'line', " +
						"'description' : 'Foo bar.', " +
						"'xtext' : 'Date', 'ytext':'New Threads', 'orderRule' : 'Date', 'datatable' : " + dataTable + "," +
						"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		} else if("csv".equals(type)) {
			return createCSVDataTable("newsgroups", "url_name", "__date", "numberOfNewThreads", "Date", "New Threads", DateFilter.DAY);
		}
		return null;
	}
	

	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Subversion");
			
		DailyNewThreadsViz viz = new DailyNewThreadsViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}
}
