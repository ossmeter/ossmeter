 
package org.ossmeter.metricprovider.dailyrequestsreplies.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;

public class DailyRequestsRepliesViz extends PongoViz {

	public DailyRequestsRepliesViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.dailyrequestsreplies");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
	String dataTable = createDataTable("dayArticles", "articles", "__date", "numberOfArticles");
	return ("{ 'target' : 'gcharts', 'chartType' : 'BarChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Number of articles' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		}
		return null;
	}
}
