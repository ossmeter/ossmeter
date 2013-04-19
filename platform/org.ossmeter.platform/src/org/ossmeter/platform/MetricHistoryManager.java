package org.ossmeter.platform;

import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class MetricHistoryManager {
	
	protected Platform platform;
	
	public MetricHistoryManager(Platform platform) {
		this.platform = platform;
	}
	
	public void store(Project project, Date date, IHistoricalMetricProvider provider) {
		DB db = platform.getMetricsRepository(project).getDb();
		DBCollection collection = db.getCollection(provider.getIdentifier());

		provider.setMetricProviderContext(platform.getMetricProviderContext());
		Pongo metric = provider.measure(project);
		DBObject dbObject = metric.getDbObject();
		
		dbObject.put("__date", date.toString());
		collection.save(dbObject);
	}
}
