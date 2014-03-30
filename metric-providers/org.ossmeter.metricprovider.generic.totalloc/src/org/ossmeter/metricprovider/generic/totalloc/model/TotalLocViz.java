package org.ossmeter.metricprovider.generic.totalloc.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.googlecode.pongo.runtime.viz.PongoViz.DateFilter;
import com.mongodb.BasicDBList;
import com.mongodb.DB;
import com.mongodb.DBObject;
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
		
			case "json":
				return ("{ 'id' : 'totalloc', 'name' : 'Lines of code', 'type' : 'area', " +
						"'description' : 'Lines of code is a software metric used to measure the size of a computer program by counting the number of lines in the text of the programs source code.', " +
						"'xtext' : 'Date', 'ytext':'Lines', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("repositories", "url", "__date", "totalLines", "Date", "Lines", DateFilter.MONTH) + "," +
						"'series' : 'Series', 'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");				
		}
		return null;
	}
	
	protected int lastValueInt = 0;
	
	@Override
	protected String createD3DataTable(String seriesKind, String seriesLabel,
			String xAxis, String yAxis, String xtext, String ytext,
			DateFilter filter) {
		Iterator<DBObject> it = collection.find().iterator();
		String table = "[";
		
		while (it.hasNext()) {
			DBObject dbobj = it.next();
			String date = String.valueOf(dbobj.get("__date"));
			
			if (!date.endsWith("01")) continue; //FIXME: Hardcoded filter.
		
			String tloc = String.valueOf(dbobj.get("totalLines"));
			String sloc = String.valueOf(dbobj.get("sourceLines"));
			String cloc = String.valueOf(dbobj.get("commentedLines"));
			String eloc = String.valueOf(dbobj.get("emptyLines"));
			
			// FIXME: The data is not ordered when read from the database
			if (Integer.valueOf(tloc) > lastValueInt) {
				this.lastValueInt = Integer.valueOf(tloc); 
				this.lastValue = tloc;
			}
			
//			table += "{ 'Series' : 'Total', 'Lines' : " + tloc + ", 'Date' : '" + date + "' }," ;
			table += "{ 'Series' : 'Source', 'Lines' : " + sloc + ", 'Date' : '" + date + "' }," ;
			table += "{ 'Series' : 'Comments', 'Lines' : " + cloc + ", 'Date' : '" + date + "' }," ;
			table += "{ 'Series' : 'Empty', 'Lines' : " + eloc + ", 'Date' : '" + date + "' }" ;

			if (it.hasNext()) table+=",";
		}
		
		if (table.endsWith(",")) table = table.substring(0, table.length()-1);
		
		table += "]";
		return table;
	}

	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("log4j");
			
		TotalLocViz viz = new TotalLocViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}
}
