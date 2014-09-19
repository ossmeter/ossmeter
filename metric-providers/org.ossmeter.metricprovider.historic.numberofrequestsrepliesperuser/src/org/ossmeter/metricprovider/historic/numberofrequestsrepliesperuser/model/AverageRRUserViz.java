 
package org.ossmeter.metricprovider.historic.numberofrequestsrepliesperuser.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DBCollection;

public class AverageRRUserViz extends PongoViz {

	public AverageRRUserViz() {
		super();
	}
	
	public AverageRRUserViz(DBCollection collection) {
		super(collection);
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
	String dataTable = createDataTable("articles", "articles", "__date", "averageArticlesPerUser");
	return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'articles per user' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		}
		return null;
	}
}
