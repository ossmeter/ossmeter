package org.ossmeter.metricprovider.generic.totalloc.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.viz.PongoViz;
import com.googlecode.pongo.runtime.viz.PongoViz.DateFilter;
import com.mongodb.BasicDBList;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class TotalLocVizBasic extends PongoViz {

	public TotalLocVizBasic() {
		super();
	}
	
	public void setProjectDB(DB projectDB) {
		this.collection = projectDB.getCollection("org.ossmeter.metricprovider.generic.totalloc");
	}
	
	@Override
	public String getViz(String type) {
		switch (type) {
			case "d3":
				return ("{ 'id' : 'totalloc2', 'name' : 'Lines of code', 'type' : 'line', " +
						"'description' : 'Lines of code is a software metric used to measure the size of a computer program by counting the number of lines in the text of the programs source code.', " +
						"'xtext' : 'Date', 'ytext':'Lines', 'orderRule' : 'Date', 'datatable' : " + createD3DataTable() + "," +
						"'series' : '', 'isTimeSeries':true, 'lastValue': '"+getLastValue()+"' }").replaceAll("'", "\"");				
		}
		return null;
	}
	
	protected int lastValueInt = 0;
	protected String createD3DataTable() {
		Iterator<DBObject> it = collection.find().iterator();
		String table = "[";
		
		while (it.hasNext()) {
			DBObject dbobj = it.next();
			String date = String.valueOf(dbobj.get("__date"));
			
			if (!date.endsWith("01")) continue; //FIXME: Hardcoded filter.
		
			String tloc = String.valueOf(dbobj.get("totalLines"));
			
			// FIXME: The data is not ordered when read from the database
			if (Integer.valueOf(tloc) > lastValueInt) {
				this.lastValueInt = Integer.valueOf(tloc);
				this.lastValue = tloc;
			}
			
			table += "{ 'Series' : 'Total', 'Lines' : " + tloc + ", 'Date' : '" + date + "' }" ;

			if (it.hasNext()) table+=",";
		}
		
		if (table.endsWith(",")) table = table.substring(0, table.length()-1);
		
		table += "]";
		return table;
	}

	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("Log4J");
			
		TotalLocVizBasic viz = new TotalLocVizBasic();
		viz.setProjectDB(db);
		System.err.println(viz.getViz("d3"));
	}
}
