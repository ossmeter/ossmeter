package org.ossmeter.platform.osgi.services;

import java.util.List;

import org.ossmeter.platform.osgi.executors.SchedulerStatus;

public interface IWorkerService {

	public abstract boolean queueProjects(List<String> projects);

	public abstract SchedulerStatus getStatus();

}