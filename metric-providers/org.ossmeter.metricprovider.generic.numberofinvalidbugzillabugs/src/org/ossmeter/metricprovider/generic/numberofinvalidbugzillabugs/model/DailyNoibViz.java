 
package org.ossmeter.metricprovider.generic.numberofinvalidbugzillabugs.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;

public class DailyNoibViz extends PongoViz {

	public DailyNoibViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofinvalidbugzillabugs");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
	String dataTable = createDataTable("bugzillas", "", "__date", "numberOfInvalidBugs");
	return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Number of invalid bugs' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		}
		return null;
	}
}
