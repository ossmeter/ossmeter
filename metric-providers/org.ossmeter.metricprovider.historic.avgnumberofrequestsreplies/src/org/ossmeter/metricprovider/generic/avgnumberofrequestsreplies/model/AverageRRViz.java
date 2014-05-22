 
package org.ossmeter.metricprovider.generic.avgnumberofrequestsreplies.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;

public class AverageRRViz extends PongoViz {

	public AverageRRViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.avgnumberofrequestsreplies");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
	String dataTable = createDataTable("articles", "articles", "__date", "averageArticles");
	return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Average number of articles' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		}
		return null;
	}
}
