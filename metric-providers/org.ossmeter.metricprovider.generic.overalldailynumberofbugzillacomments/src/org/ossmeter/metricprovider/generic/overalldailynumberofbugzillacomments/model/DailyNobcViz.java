 
package org.ossmeter.metricprovider.generic.overalldailynumberofbugzillacomments.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DBCollection;

public class DailyNobcViz extends PongoViz {

	public DailyNobcViz() {
		super();
	}
	
	public DailyNobcViz(DBCollection collection) {
		super(collection);
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
	String dataTable = createDataTable("bugzillas", "", "__date", "numberOfComments");
	return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Number of bug comments' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		}
		return null;
	}
}
