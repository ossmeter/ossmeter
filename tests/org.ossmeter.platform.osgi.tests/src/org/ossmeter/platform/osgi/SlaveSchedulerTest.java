package org.ossmeter.platform.osgi;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.ossmeter.platform.osgi.executors.SlaveScheduler;

public class SlaveSchedulerTest {

	@Test
	public void test() {
		
		SlaveScheduler slave = new SlaveScheduler();
		
		List<Object> projects = new ArrayList<Object>(); 
		
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
