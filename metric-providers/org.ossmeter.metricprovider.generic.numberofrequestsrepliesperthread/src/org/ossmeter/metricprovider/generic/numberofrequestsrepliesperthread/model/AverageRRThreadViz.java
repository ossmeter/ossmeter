 
package org.ossmeter.metricprovider.generic.numberofrequestsrepliesperthread.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.googlecode.pongo.runtime.viz.PongoViz.DateFilter;
import com.mongodb.BasicDBList;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class AverageRRThreadViz extends PongoViz {

	public AverageRRThreadViz() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.numberofrequestsrepliesperthread");
	}

	@Override
	public String getViz(String type) {

		if ("json".equals(type)) {
			Iterator<DBObject> it = collection.find().iterator();
			String table = "[";
			
			while (it.hasNext()) {
				DBObject dbobj = it.next();
				String xval = String.valueOf(dbobj.get("__date"));
				if (xval.trim().endsWith("01")) {
					// need to be defensive here, as the metric provider will create empty arrays if there is no data.
					BasicDBList artsList =  ((BasicDBList) dbobj.get("articles"));
					BasicDBList reqsList =  ((BasicDBList) dbobj.get("requests"));
					BasicDBList repsList =  ((BasicDBList) dbobj.get("replies"));
					
					DBObject arts = null;
					if (artsList.size()>0) arts = (DBObject) artsList.get(0);
					
					DBObject reqs = null;
					if (reqsList.size()>0) reqs = (DBObject) reqsList.get(0);
					
					DBObject reps = null;
					if (repsList.size()>0) reps = (DBObject) repsList.get(0);
					
					String quantityArticles = arts != null ? String.valueOf(arts.get("averageArticlesPerThread")) : "0";
					String quantityRequests = reqs != null ? String.valueOf(reqs.get("averageRequestsPerThread")) : "0";
					String quantityReplies = reps != null ? String.valueOf(reps.get("averageRepliesPerThread")) : "0";
					
					if ("NaN".equals(quantityArticles)) quantityArticles = "0";
					if ("NaN".equals(quantityRequests)) quantityRequests = "0";
					if ("NaN".equals(quantityReplies)) quantityReplies = "0";
					
					table += "{ 'Series' : 'Articles', 'Quantity' : " + quantityArticles + ", 'Date' : '" + xval + "' },";
					table += "{ 'Series' : 'Requests', 'Quantity' : " + quantityRequests + ", 'Date' : '" + xval + "' },";
					table += "{ 'Series' : 'Replies', 'Quantity' : " + quantityReplies + ", 'Date' : '" + xval + "' }" ;
					if (it.hasNext()) table+=",";
				}
			}
			if (table.endsWith(",")) table = table.substring(0, table.lastIndexOf(","));
			table += "]";
			
			
			return ("{ 'id' : 'articlesrequestsrepliesperthread', 'name' : 'Average requests and replies/thread', 'type' : 'line', " +
					"'description' : 'A measure of the average number of requests and replies per thread posted on the newsgroup.', " +
					"'xtext' : 'Date', 'ytext':'Quantity', 'series':'Series', 'orderRule' : 'Date', 'datatable' : " + table + "," +
					"'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");
		} else if ("csv".equals(type)) {
			Iterator<DBObject> it = collection.find().iterator();
			String table = "Series,Quantity,Date\n";
			
			while (it.hasNext()) {
				DBObject dbobj = it.next();
				String xval = String.valueOf(dbobj.get("__date"));
			
				// need to be defensive here, as the metric provider will create empty arrays if there is no data.
				BasicDBList artsList =  ((BasicDBList) dbobj.get("articles"));
				BasicDBList reqsList =  ((BasicDBList) dbobj.get("requests"));
				BasicDBList repsList =  ((BasicDBList) dbobj.get("replies"));
				
				DBObject arts = null;
				if (artsList.size()>0) arts = (DBObject) artsList.get(0);
				
				DBObject reqs = null;
				if (reqsList.size()>0) reqs = (DBObject) reqsList.get(0);
				
				DBObject reps = null;
				if (repsList.size()>0) reps = (DBObject) repsList.get(0);
				
				String quantityArticles = arts != null ? String.valueOf(arts.get("averageArticlesPerThread")) : "0";
				String quantityRequests = reqs != null ? String.valueOf(reqs.get("averageRequestsPerThread")) : "0";
				String quantityReplies = reps != null ? String.valueOf(reps.get("averageRepliesPerThread")) : "0";
				
				table += "Articles," + quantityArticles + "," + xval + "\n";
				table += "Requests," + quantityRequests + "," + xval + "\n";
				table += "Replies," + quantityReplies + "," + xval + "\n" ;
			}
			return table;
			
		}
		
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("modelinggmpgraphiti");
			
		AverageRRThreadViz viz = new AverageRRThreadViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("json"));
	}
}
