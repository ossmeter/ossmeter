package org.ossmeter.platform;

import java.util.Collections;
import java.util.List;

import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public abstract class AbstractTransientMetricProvider<T extends PongoDB> implements ITransientMetricProvider<T>{
	
	protected MetricProviderContext context = null;
	
	@Override
	public String getIdentifier() {
		return this.getClass().getName();
	}

	@Override
	public abstract boolean appliesTo(Project project);

	@Override
	public void setUses(List<IMetricProvider> uses) {
		
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Collections.emptyList();
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public abstract T adapt(DB db);

	@Override
	public abstract void measure(Project project, ProjectDelta delta, T db);

}
