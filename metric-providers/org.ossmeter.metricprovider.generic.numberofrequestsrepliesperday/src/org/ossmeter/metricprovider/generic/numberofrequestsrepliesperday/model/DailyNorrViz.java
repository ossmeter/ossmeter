 
package org.ossmeter.metricprovider.generic.numberofrequestsrepliesperday.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.BasicDBList;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class DailyNorrViz extends PongoViz {

	protected int lastNumberRequests = 0;
	protected int lastNumberReplies = 0;
	
	public DailyNorrViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofrequestsrepliesperday");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "gcharts": 
				String dataTable = createDataTable("requests", "requests", "__date", "numberOfRequests");
				return ("{ 'target' : 'gcharts', 'chartType' : 'LineChart', 'options' : { 'hAxis' : {'title':'Date'}, 'vAxis':{'title' : 'Number of occurences' }}, 'datatable' : " + dataTable + " }").replaceAll("'", "\"");		
			case "d3":
				return ("{ 'id' : 'requestsreplies', 'name' : 'Requests and replies/day', 'type' : 'line', " +
						"'description' : 'A measure of the number of requests and replies per day posted on the newsgroup.', " +
						"'xtext' : 'Date', 'ytext':'Quantity', 'series':'Series', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("repositories", "url", "__date", "numberOfRequests", "Date", "Quantity", DateFilter.DAY) + "," +
						"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		}
		
		return null;
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
		
			DBObject reqs = (DBObject) ((BasicDBList) dbobj.get("requests")).get(0);
			DBObject reps = (DBObject) ((BasicDBList) dbobj.get("replies")).get(0);
			
			String quantityRequests = String.valueOf(reqs.get("numberOfRequests"));
			String quantityReplies = String.valueOf(reps.get("numberOfReplies"));;
			
			table += "{ 'Series' : 'Requests', 'Quantity' : " + quantityRequests + ", 'Date' : '" + xval + "' }," ;
			table += "{ 'Series' : 'Replies', 'Quantity' : " + quantityReplies + ", 'Date' : '" + xval + "' }" ;

			if (it.hasNext()) table+=",";
		}
		
		table += "]";
		return table;
	}
	
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Tomcat");
			
		DailyNorrViz viz = new DailyNorrViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}
}
