package org.ossmeter.platform;

import org.ossmeter.platform.logging.OssmeterLoggerFactory;
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
		
		String id = provider.getIdentifier().replace("org.ossmeter.metricprovider.", "");
		DBCollection collection = db.getCollection(id);

		MetricProviderContext context = new MetricProviderContext(platform, new OssmeterLoggerFactory().makeNewLoggerInstance(provider.getIdentifier()));
		context.setDate(date);
		provider.setMetricProviderContext(context);
		Pongo metric = provider.measure(project);
		DBObject dbObject = metric.getDbObject();
		
		dbObject.put("__date", date.toString());
		dbObject.put("__datetime", date.toJavaDate());
		collection.save(dbObject);
	}
}
