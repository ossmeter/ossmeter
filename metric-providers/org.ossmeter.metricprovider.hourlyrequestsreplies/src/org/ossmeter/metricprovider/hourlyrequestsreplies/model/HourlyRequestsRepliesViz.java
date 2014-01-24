 
package org.ossmeter.metricprovider.hourlyrequestsreplies.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;

public class HourlyRequestsRepliesViz extends PongoViz {

	public HourlyRequestsRepliesViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.hourlyrequestsreplies");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
	String dataTable = createDataTable("hourArticles", "articles", "__date", "numberOfArticles");
	return ("{ 'target' : 'gcharts', 'chartType' : 'BarChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Number of articles' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		}
		return null;
	}
}
