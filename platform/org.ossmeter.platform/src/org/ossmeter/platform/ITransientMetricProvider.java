package org.ossmeter.platform;

import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public interface ITransientMetricProvider<T extends PongoDB> extends IMetricProvider {
	
	/**
	 * 
	 * @param db
	 * @return
	 */
	public T adapt(DB db);
	
	public void measure(Project project, ProjectDelta delta, T db);
	
}
