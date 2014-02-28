package org.ossmeter.platform.osgi.old;

import java.util.List;

public interface IWorker {
	
	public void setMaster(IMaster master);
	
	public void queueProjects(List<String> projectList);

}
