package org.ossmeter.platform.osgi.executors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProjectExecutor implements Runnable {

	protected Object project;
	protected int numberOfCores;
	
	public ProjectExecutor() {
		numberOfCores = Runtime.getRuntime().availableProcessors();
	}
	
	public void setProject(Object project) {
		this.project = project;
	}
	
	@Override
	public void run() {
		System.out.println(project + " executing...");
		
		List<Object> metrics1 = new ArrayList<Object>();
		metrics1.add("Metric 1");
		metrics1.add("Metric 2");
		metrics1.add("Metric 3");
		metrics1.add("Metric 4");
		
		List<Object> metrics2 = new ArrayList<Object>();
		metrics2.add("Metric 5");
		metrics2.add("Metric 6");
		
		List<Object> metrics3 = new ArrayList<Object>();
		metrics3.add("Metric 7");
		
		List<Object> metrics4 = new ArrayList<Object>();
		metrics4.add("Metric 8");
		
		List<Object> metrics5 = new ArrayList<Object>();
		metrics5.add("Metric 9");
		
		List<Object> metrics6 = new ArrayList<Object>();
		metrics6.add("Metric 10");
		metrics6.add("Metric 11");
		
		List<List<Object>> metricDependencyBranches = Arrays.asList(metrics1,metrics2,metrics3,metrics4,metrics5,metrics6);

		// TODO: An alternative would be to have a single thread pool for the node. I briefly tried this
		// and it didn't work. Reverted to this implement (temporarily at least).
		ExecutorService executorService = Executors.newFixedThreadPool(numberOfCores);
		
		for (List<Object> branch : metricDependencyBranches) {
			MetricListExecutor mExe = new MetricListExecutor();
			mExe.setMetricList(branch);
			
			executorService.execute(mExe);
		}

		executorService.shutdown();
		try {
			executorService.awaitTermination(30,TimeUnit.HOURS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		};
	}

}
