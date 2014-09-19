 
package org.ossmeter.metricprovider.historic.overallsentimentnntp.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DBCollection;

public class OverallSentimentNNTPViz extends PongoViz {

	public OverallSentimentNNTPViz() {
		super();
	}
	
	public OverallSentimentNNTPViz(DBCollection collection) {
		super(collection);
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
	String dataTable = createDataTable("newsgroups", "", "__date", "overallAverageSentiment");
	return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Overall Sentiment of Bugs' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		}
		return null;
	}
}
