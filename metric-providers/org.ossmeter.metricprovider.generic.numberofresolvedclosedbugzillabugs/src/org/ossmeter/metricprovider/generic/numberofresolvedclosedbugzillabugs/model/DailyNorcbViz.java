 
package org.ossmeter.metricprovider.generic.numberofresolvedclosedbugzillabugs.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;

public class DailyNorcbViz extends PongoViz {

	public DailyNorcbViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofresolvedclosedbugzillabugs");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
	String dataTable = createDataTable("bugzillas", "", "__date", "numberOfResolvedClosedBugs");
	return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Number of resolved or closed bugs' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		}
		return null;
	}
}
