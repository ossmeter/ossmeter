 
package org.ossmeter.metricprovider.generic.overalldailynumberofnewbugzillabugs.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DBCollection;

public class DailyNonbbViz extends PongoViz {

	public DailyNonbbViz() {
		super();
	}
	
	public DailyNonbbViz(DBCollection collection) {
		super(collection);
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
	String dataTable = createDataTable("bugzillas", "", "__date", "numberOfBugs");
	return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Number of new bugs' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		}
		return null;
	}
}
