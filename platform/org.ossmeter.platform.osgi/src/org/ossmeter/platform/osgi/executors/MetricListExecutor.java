package org.ossmeter.platform.osgi.executors;

import java.util.List;

public class MetricListExecutor implements Runnable {

	List<Object> metrics;
	public MetricListExecutor() {
		
	}
	
	public void setMetricList(List<Object> metrics) {
		this.metrics = metrics;
	}
	
	@Override
	public void run() {
		
		for (Object m : metrics) {
			System.out.println("\t" + m + " executed");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
