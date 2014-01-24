 
package org.ossmeter.metricprovider.generic.numberofrequestsrepliesperthread.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;

public class AverageRRThreadViz extends PongoViz {

	public AverageRRThreadViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofrequestsrepliesperthread");
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
