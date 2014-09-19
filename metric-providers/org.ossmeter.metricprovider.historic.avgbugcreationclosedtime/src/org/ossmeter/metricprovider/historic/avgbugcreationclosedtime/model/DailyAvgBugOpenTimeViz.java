 
package org.ossmeter.metricprovider.historic.avgbugcreationclosedtime.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DBCollection;

public class DailyAvgBugOpenTimeViz extends PongoViz {

	public DailyAvgBugOpenTimeViz() {
		super();
	}
	
	public DailyAvgBugOpenTimeViz(DBCollection collection) {
		super(collection);
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
	String dataTable = createDataTable("bugzillas", "", "__date", "avgBugOpenTime");
	return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Average time between creating and closing bugzilla bugs' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		}
		return null;
	}
}
