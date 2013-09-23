 
package org.ossmeter.metricprovider.generic.numberofactiveusersperday.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DBCollection;

public class DailyActiveUsersViz extends PongoViz {

	public DailyActiveUsersViz() {
		super();
	}
	
	public DailyActiveUsersViz(DBCollection collection) {
		super(collection);
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
	String dataTable = createDataTable("newsgroups", "", "__date", "numberOfActiveUsers");
	return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Number of new active users' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		}
		return null;
	}
}
