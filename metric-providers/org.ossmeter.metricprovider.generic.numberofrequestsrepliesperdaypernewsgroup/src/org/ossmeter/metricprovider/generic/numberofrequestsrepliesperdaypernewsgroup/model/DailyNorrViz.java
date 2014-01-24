 
package org.ossmeter.metricprovider.generic.numberofrequestsrepliesperdaypernewsgroup.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;

public class DailyNorrViz extends PongoViz {

	public DailyNorrViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofrequestsrepliesperdaypernewsgroup");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
	String dataTable = createDataTable("requests", "requests", "__date", "numberOfRequests");
	return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Number of occurences' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		}
		return null;
	}
}
