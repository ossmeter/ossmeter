package org.ossmeter.platform;

import java.util.List;

import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.factoids.Factoid;
import org.ossmeter.platform.factoids.Factoids;
import org.ossmeter.repository.model.Project;

import com.mongodb.DB;

public abstract class AbstractFactoidMetricProvider implements ITransientMetricProvider<Factoids>{
	
	protected MetricProviderContext context = null;
	protected List<IMetricProvider> uses;
	
	@Override
	public String getIdentifier() {
		return this.getClass().getName();
	}

	@Override
	public abstract boolean appliesTo(Project project);

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public abstract List<String> getIdentifiersOfUses();

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	final public Factoids adapt(DB db){
		return new Factoids(db); 
	}

	@Override
	final public void measure(Project project, ProjectDelta delta, Factoids db){
		
		Factoid factoid = db.getFactoids().findOneByMetricId(getIdentifier());
		if (factoid == null) {
			factoid = new Factoid();
			factoid.setMetricId(getIdentifier());
			List<String> uses = getIdentifiersOfUses();
			if (uses != null) {
				for (String dep : uses) {
					factoid.getMetricDependencies().add(dep);
				}
			}
			db.getFactoids().add(factoid);
		}
		measureImpl(project, delta, factoid);
		db.sync();
	}

	abstract public void measureImpl(Project project, ProjectDelta delta, Factoid factoid);
}

