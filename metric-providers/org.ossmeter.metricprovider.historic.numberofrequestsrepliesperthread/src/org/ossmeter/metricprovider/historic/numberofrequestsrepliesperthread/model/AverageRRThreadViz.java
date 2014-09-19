 
package org.ossmeter.metricprovider.historic.numberofrequestsrepliesperthread.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DBCollection;

public class AverageRRThreadViz extends PongoViz {

	public AverageRRThreadViz() {
		super();
	}
	
	public AverageRRThreadViz(DBCollection collection) {
		super(collection);
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
	String dataTable = createDataTable("articles", "articles", "__date", "averageArticlesPerThread");
	return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'articles per thread' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		}
		return null;
	}
}
