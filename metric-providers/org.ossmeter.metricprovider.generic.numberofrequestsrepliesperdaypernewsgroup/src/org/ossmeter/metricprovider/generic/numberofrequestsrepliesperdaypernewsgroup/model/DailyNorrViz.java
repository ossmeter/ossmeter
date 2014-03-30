 
package org.ossmeter.metricprovider.generic.numberofrequestsrepliesperdaypernewsgroup.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.googlecode.pongo.runtime.viz.PongoViz.DateFilter;
import com.mongodb.BasicDBList;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class DailyNorrViz extends PongoViz {

	public DailyNorrViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofrequestsrepliesperdaypernewsgroup");
	}

	@Override
	public String getViz(String type) {
		switch (type) {
		case "csv": 
			return createCSVDataTable("newsgroups", "url_name", "__date", "numberOfNewUsers", "Date", "Users", DateFilter.DAY);
		case "json":
			return ("{ 'id' : 'articles', 'name' : 'New Users/day', 'type' : 'line', " +
					"'description' : 'This metric shows the new users of the newgroup(s) per day.', " +
					"'xtext' : 'Date', 'ytext':'Users', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable("newsgroups", "url_name", "__date", "numberOfNewUsers", "Date", "Users", DateFilter.DAY) + "," +
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
		
			// need to be defensive here, as the metric provider will create empty arrays if there is no data.
			BasicDBList reqsList =  ((BasicDBList) dbobj.get("requests"));
			BasicDBList repsList =  ((BasicDBList) dbobj.get("replies"));
			
			DBObject reqs = null;
			if (reqsList.size()>0) reqs = (DBObject) reqsList.get(0);
			
			DBObject reps = null;
			if (repsList.size()>0) reps = (DBObject) repsList.get(0);
			
			String quantityRequests = reqs != null ? String.valueOf(reqs.get("numberOfRequests")) : "0";
			String quantityReplies = reps != null ? String.valueOf(reps.get("numberOfReplies")) : "0";
			
			table += "{ 'Series' : 'Requests', 'Quantity' : " + quantityRequests + ", 'Date' : '" + xval + "' }," ;
			table += "{ 'Series' : 'Replies', 'Quantity' : " + quantityReplies + ", 'Date' : '" + xval + "' }" ;

			if (it.hasNext()) table+=",";
		}
		
		table += "]";
		return table;
	}

public static void main(String[] args) throws Exception {
	Mongo mongo = new Mongo();
	DB db = mongo.getDB("modelingtmfxtext");
		
	DailyNorrViz viz = new DailyNorrViz();
	viz.setProjectDB(db);
	System.err.println(viz.getViz("json"));
}
}
