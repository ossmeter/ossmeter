package org.ossmeter.metricprovider.rascal.metric.trans.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class LocViz extends PongoViz{

	@Override
	public void setProjectDB(DB db) {
		this.collection = db.getCollection("Loc.linesOfCode");
		System.out.println(this.collection.find().count());
	}

	@Override
	public String getViz(String type) {
		switch (type) {
			case "d3":
				return ("{ 'id' : 'LOC', 'name' : 'Lines of Code', 'type' : 'bar', " +
						"'description' : 'Lines of code is a software metric used to measure the size of a computer program by counting the number of lines in the text of the programs source code.', " +
						"'xtext' : 'Lines', 'ytext':'', 'orderRule' : ['sourceLines','commentedLines','emptyLines'], " +
						"'series' : 'Field', 'vertical' : true, 'datatable' : " + createD3DataTable() + "," +
						"'isTimeSeries':false, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");				
		}
		return null;
	}

	protected String createD3DataTable() {
		
		BasicDBObject groupFields = new BasicDBObject("_id","null");
		groupFields.put("totalLines", new BasicDBObject("$sum", "$totalLines"));
		groupFields.put("commentedLines", new BasicDBObject("$sum", "$commentedLines"));
		groupFields.put("emptyLines", new BasicDBObject("$sum", "$emptyLines"));
		groupFields.put("sourceLines", new BasicDBObject("$sum", "$sourceLines"));
		BasicDBObject group = new BasicDBObject("$group", groupFields);

		AggregationOutput output = collection.aggregate(group);

		Iterator<DBObject> it = output.results().iterator();
		
		String table = "[";
		if (it.hasNext()) { // Should only be one result
			DBObject obj = it.next();
//			table += "{ 'Project':'', 'Field':'totalLines', 'Lines': " + Integer.valueOf((String)obj.get("totalLines")) + " },";
			table += "{ 'Project':'Ant', 'Field':'commentedLines', 'Lines': " + obj.get("commentedLines") + " },";
			table += "{ 'Project':'Ant', 'Field':'emptyLines', 'Lines': " + obj.get("emptyLines") + " },";
			table += "{ 'Project':'Ant', 'Field':'sourceLines', 'Lines': " + obj.get("sourceLines") + " }";
			this.lastValue = String.valueOf(obj.get("totalLines"));
		}
		table += "]";
		return table;
	}
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Ant");
			
		LocViz viz = new LocViz();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}
}
