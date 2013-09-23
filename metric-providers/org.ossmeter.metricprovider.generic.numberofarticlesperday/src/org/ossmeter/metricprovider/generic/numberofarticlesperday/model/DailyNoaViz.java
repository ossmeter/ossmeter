 
package org.ossmeter.metricprovider.generic.numberofarticlesperday.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DBCollection;

public class DailyNoaViz extends PongoViz {

	public DailyNoaViz() {
		super();
	}
	
	public DailyNoaViz(DBCollection collection) {
		super(collection);
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
	String dataTable = createDataTable("newsgroups", "", "__date", "numberOfArticles");
	return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Number of new bugs' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		}
		return null;
	}
}
