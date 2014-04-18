 
package org.ossmeter.metricprovider.historic.numberofcommitters.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.googlecode.pongo.runtime.viz.PongoViz.DateFilter;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class CommittersOverTimeViz extends PongoViz {

	protected int lastNumberRequests = 0;
	protected int lastNumberReplies = 0;
	
	public CommittersOverTimeViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.historic.numberofcommitters.model.CommittersOverTime");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "csv": 
				return createCSVDataTable("repositories", "url", "__date", "numberOfRequests", "Date", "Quantity", DateFilter.DAY);
			case "json":
				return ("{ 'id' : 'committersovertime', 'name' : 'Committers over time', 'type' : 'line', " +
						"'description' : 'The number of committers.', " +
						"'xtext' : 'Date', 'ytext':'Quantity', 'series':'Series', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("", "", "__date", "", "", "", DateFilter.MONTH) + "," +
						"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		}
		
		return null;
	}
	
	@Override
	protected String createCSVDataTable(String seriesKind, String seriesLabel, String xAxis, String yAxis, String xtext, String ytext, DateFilter filter) {
		throw new RuntimeException();
	}
	
	@Override
	protected String createD3DataTable(String seriesKind, String seriesLabel,
			String xAxis, String yAxis, String xtext, String ytext,
			DateFilter filter) {
		Iterator<DBObject> it = collection.find().iterator();
		String table = "[";
		while (it.hasNext()) {
			DBObject dbobj = it.next();
			String xval = String.valueOf(dbobj.get(xAxis));
			if (
					(xAxis.equals("__date") && filter.equals(DateFilter.MONTH) && xval.trim().endsWith("01")) ||
					(xAxis.equals("__date") && filter.equals(DateFilter.DAY))
				) {
				
				table += "{ 'Series' : 'Total', 'Quantity' : " + dbobj.get("totalNumberOfCommitters") + ", 'Date' : '" + xval + "' }," ;
				table += "{ 'Series' : 'Last One Month', 'Quantity' : " + dbobj.get("numberOfCommittersLast1month") + ", 'Date' : '" + xval + "' }," ;
				table += "{ 'Series' : 'Last Three Months', 'Quantity' : " + dbobj.get("numberOfCommittersLast3months") + ", 'Date' : '" + xval + "' }," ;
				table += "{ 'Series' : 'Last Six Months', 'Quantity' : " + dbobj.get("numberOfCommittersLast6months") + ", 'Date' : '" + xval + "' }," ;
				table += "{ 'Series' : 'Last Twelve Months', 'Quantity' : " + dbobj.get("numberOfCommittersLast12months") + ", 'Date' : '" + xval + "' }" ;
	
				if (it.hasNext()) table+=",";
			}
		}
		if (table.endsWith(",")) table = table.substring(0, table.lastIndexOf(","));
		table += "]";
		return table;
	}
	
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("modelingmmtatl");
			
		CommittersOverTimeViz viz = new CommittersOverTimeViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("json"));
	}
}
