 
package org.ossmeter.metricprovider.trans.dailyrequestsreplies.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DBCollection;

public class DailyRequestsRepliesViz extends PongoViz {

	public DailyRequestsRepliesViz() {
		super();
	}
	
	public DailyRequestsRepliesViz(DBCollection collection) {
		super(collection);
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
