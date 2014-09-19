 
package org.ossmeter.metricprovider.historic.dailyavgresponsetimepernewsgroup.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DBCollection;

public class DailyAverageThreadResponseTimeViz extends PongoViz {

	public DailyAverageThreadResponseTimeViz() {
		super();
	}
	
	public DailyAverageThreadResponseTimeViz(DBCollection collection) {
		super(collection);
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
	String dataTable = createDataTable("newsgroups", "", "__date", "avgResponseTime");
	return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Average response time' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		}
		return null;
	}
}
