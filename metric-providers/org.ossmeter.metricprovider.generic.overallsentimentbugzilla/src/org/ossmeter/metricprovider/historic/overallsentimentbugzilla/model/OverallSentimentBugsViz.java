 
package org.ossmeter.metricprovider.historic.overallsentimentbugzilla.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DBCollection;

public class OverallSentimentBugsViz extends PongoViz {

	public OverallSentimentBugsViz() {
		super();
	}
	
	public OverallSentimentBugsViz(DBCollection collection) {
		super(collection);
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
	String dataTable = createDataTable("bugzillas", "", "__date", "overallAverageSentiment");
	return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Overall Sentiment of Bugs' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		}
		return null;
	}
}
