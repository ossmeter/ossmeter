package org.ossmeter.platform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

public abstract class AbstractHistoricalMetricProvider implements IHistoricalMetricProvider {
	
	public List<Pongo> getHistoricalMeasurements(MetricProviderContext context, Project project, Date start, Date end) {
		
		DB db = context.getProjectDB(project);
		DBCollection collection = db.getCollection(getIdentifier());
		
		QueryBuilder builder = QueryBuilder.start();
		if (start != null) {
			builder.and("__datetime").greaterThanEquals(start.toJavaDate());
		}
		if (end != null) {
			builder.and("__datetime").lessThanEquals(end.toJavaDate());
		}
		 
		BasicDBObject query = (BasicDBObject) builder.get(); 

		Iterator<DBObject> it = collection.find(query).iterator();
		
		List<Pongo> pongoList = new ArrayList<Pongo>();
		
		while (it.hasNext()) {
			DBObject dbObject = it.next();
			pongoList.add(PongoFactory.getInstance().createPongo(dbObject));
		}
		
		return pongoList;
		
	}
	
}
