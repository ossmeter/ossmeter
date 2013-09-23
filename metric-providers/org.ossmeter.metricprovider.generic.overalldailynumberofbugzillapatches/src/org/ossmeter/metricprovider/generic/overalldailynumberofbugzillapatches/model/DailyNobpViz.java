 
package org.ossmeter.metricprovider.generic.overalldailynumberofbugzillapatches.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DBCollection;

public class DailyNobpViz extends PongoViz {

	public DailyNobpViz() {
		super();
	}
	
	public DailyNobpViz(DBCollection collection) {
		super(collection);
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
	String dataTable = createDataTable("bugzillas", "", "__date", "numberOfPatches");
	return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Number of new patches' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		}
		return null;
	}
}
