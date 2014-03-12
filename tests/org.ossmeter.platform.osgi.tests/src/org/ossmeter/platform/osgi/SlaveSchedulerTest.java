package org.ossmeter.platform.osgi;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.ossmeter.platform.osgi.executors.SlaveScheduler;

import com.mongodb.Mongo;

public class SlaveSchedulerTest {

	@Test
	public void test() throws Exception {
		
		Mongo mongo = new Mongo();
		
		SlaveScheduler slave = new SlaveScheduler(mongo);
		
		List<String> projects = new ArrayList<String>(); 
		
		projects.add("Project1");
		projects.add("Project2");
		projects.add("Project3");
		projects.add("Project4");
		projects.add("Project5");
		
		slave.queueProjects(projects);

		slave.run();
		slave.run(); // Should print error
		slave.finish();
	}
}
