package org.ossmeter.metricprovider.generic.avgresponsetimeperdaypernewsgroup.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.googlecode.pongo.runtime.viz.PongoViz.DateFilter;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class DailyAverageThreadResponseTimeViz extends PongoViz {

	public DailyAverageThreadResponseTimeViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.avgresponsetimeperdaypernewsgroup");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
				String dataTable = createDataTable("newsgroups", "", "__date", "avgResponseTime");
				return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Average response time' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		
		}
		return null;
	}
}
