package org.ossmeter.metricprovider.generic.totalloc.model;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class TotalLocViz extends PongoViz {

	public TotalLocViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.totalloc");
	}
	
	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
				return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { " +
						"'hAxis' : {'title':'Date', 'textStyle' : {'color':'#aaa'}, 'gridlines':{'color':'#eee'}, 'baselineColor':'#ddd'}, " +
						"'fontName' : 'Helvetica', " +
						"'vAxis':{'title':'Total lines of code','textStyle' : {'color':'#aaa'}, 'gridlines':{'color':'#eee'}, 'baselineColor':'#ddd'}, " +
						"'width':'90%','height':200, " +
						"'legend': {'position': 'in'}}, 'datatable' : " + createDataTable("repositories", "url", "__date", "totalLines") + " }").replaceAll("'", "\"");
		
			case "d3":
				return ("{ 'id' : 'totalloc', 'name' : 'Lines of code', 'type' : 'line', " +
						"'description' : 'Lines of code is a software metric used to measure the size of a computer program by counting the number of lines in the text of the programs source code.', " +
						"'xtext' : 'Date', 'ytext':'Lines', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("repositories", "url", "__date", "totalLines", "Date", "Lines", DateFilter.MONTH) + "," +
						"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");				
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Log4J");
			
		TotalLocViz viz = new TotalLocViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}
}
