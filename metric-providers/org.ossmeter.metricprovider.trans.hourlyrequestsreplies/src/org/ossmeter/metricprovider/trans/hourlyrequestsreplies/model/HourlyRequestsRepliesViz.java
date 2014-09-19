 
package org.ossmeter.metricprovider.trans.hourlyrequestsreplies.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DBCollection;

public class HourlyRequestsRepliesViz extends PongoViz {

	public HourlyRequestsRepliesViz() {
		super();
	}
	
	public HourlyRequestsRepliesViz(DBCollection collection) {
		super(collection);
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
