package org.ossmeter.metricprovider.generic.totalloc.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DBCollection;

public class TotalLocViz extends PongoViz {

	public TotalLocViz() {
		super();
	}
	
	public TotalLocViz(DBCollection collection) {
		super(collection);
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
				String dataTable = createDataTable("repositories", "url", "__date", "totalLines");
				return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { " +
						"'hAxis' : {'title':'Date', 'textStyle' : {'color':'#aaa'}, 'gridlines':{'color':'#eee'}, 'baselineColor':'#ddd'}, " +
						"'fontName' : 'Helvetica', " +
						"'vAxis':{'title':'Total lines of code','textStyle' : {'color':'#aaa'}, 'gridlines':{'color':'#eee'}, 'baselineColor':'#ddd'}, " +
						"'width':'90%','height':200, " +
						"'legend': {'position': 'in'}}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");
		}
		return null;
	}
}
