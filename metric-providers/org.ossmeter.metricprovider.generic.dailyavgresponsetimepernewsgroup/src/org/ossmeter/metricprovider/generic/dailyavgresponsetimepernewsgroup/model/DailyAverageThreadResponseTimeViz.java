package org.ossmeter.metricprovider.generic.dailyavgresponsetimepernewsgroup.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DailyAverageThreadResponseTimeViz extends PongoViz {

	public DailyAverageThreadResponseTimeViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.dailyavgresponsetimepernewsgroup");
	}

	@Override
	public String getViz(String type) {
		if("json".equals(type)) { 
				String dataTable = createD3DataTable("newsgroups", "url_name", "__date", "avgResponseTime", "Date","Average Response Time", DateFilter.DAY);

				return ("{ 'id' : 'dailyavgresponsetimepernewsgroup', 'name' : 'Average Response Time', 'type' : 'line', " +
						"'description' : 'Foo bar.', " +
						"'xtext' : 'Date', 'ytext':'Average Response Time', 'orderRule' : 'Date', 'datatable' : " + dataTable + "," +
						"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		} else if("csv".equals(type)) {
			return createCSVDataTable("newsgroups", "url_name", "__date", "avgResponseTime", "Date", "Average Response Time", DateFilter.DAY);
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Subversion");
			
		DailyAverageThreadResponseTimeViz viz = new DailyAverageThreadResponseTimeViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}

}
