 
package org.ossmeter.metricprovider.generic.numberofrequestsrepliesperuser.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;

public class AverageRRUserViz extends PongoViz {

	public AverageRRUserViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofrequestsrepliesperuser");
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
