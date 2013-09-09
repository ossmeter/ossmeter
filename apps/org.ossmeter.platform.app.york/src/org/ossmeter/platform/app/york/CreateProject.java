package org.ossmeter.platform.app.york;

import org.ossmeter.platform.Date;
import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.MetricProvider;
import org.ossmeter.repository.model.MetricProviderType;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectCollection;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class CreateProject {

	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("ossmeter");
		DBCollection projects = db.getCollection("projects");
		
		Platform platform = new Platform(mongo);
		
		Project project = new Project();
		project.setName("Foo Bar");
		project.setShortName("foo-bar");
		project.setDescription("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
		project.setYear(2012);
		project.setActive(true);
		project.setLastExecuted(new Date().toString());
		
		MetricProvider mp = new MetricProvider();
		mp.setName("loc-historic");
		mp.setMetricProviderId("loc-historic");
		mp.setType(MetricProviderType.HISTORIC);
		mp.setLastExecuted(new Date().toString());
		project.getMetricProvider().add(mp); 
		
		mp = new MetricProvider();
		mp.setName("loc-transient");
		mp.setMetricProviderId("loc-transient");
		mp.setType(MetricProviderType.TRANSIENT); 
		mp.setLastExecuted(new Date().toString());
		project.getMetricProvider().add(mp);
		
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
//		ProjectCollection pc = new ProjectCollection(projects);
//		pc.add(project);
//		pc.sync();
	
		
	}
}
