package org.ossmeter.metricprovider.rascal;

import java.util.List;
import java.util.Map;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.ossmeter.platform.AbstractFactoidMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.factoids.Factoid;
import org.ossmeter.repository.model.Project;
import org.rascalmpl.interpreter.result.AbstractFunction;

public class RascalFactoidProvider extends AbstractFactoidMetricProvider {
	private RascalMetricProvider metric;
	
	public RascalFactoidProvider(String metricId, String funcName,
			String friendlyName, String description, AbstractFunction f,
			Map<String, String> uses) {
	
		metric = new RascalMetricProvider(metricId, funcName, friendlyName, description, f, uses);
	}

	@Override
	public String getIdentifier() {
		return metric.getIdentifier();
	}
	
	@Override
	public void setUses(List<IMetricProvider> uses) {
		super.setUses(uses);
		metric.setUses(uses);
	}
	
	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		metric.setMetricProviderContext(context);
	}
	
	@Override
	public String getShortIdentifier() {
		return metric.getShortIdentifier();
	}

	@Override
	public String getFriendlyName() {
		return metric.getFriendlyName();
	}

	@Override
	public String getSummaryInformation() {
		return getSummaryInformation();
	}

	@Override
	public boolean appliesTo(Project project) {
		return metric.appliesTo(project);
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return metric.getIdentifiersOfUses();
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
		IValue result = metric.compute(project, delta);
		
		if (result == null) {
			return; // something else went wrong and we should have seen error messages from that
		}
		
		if (!(result instanceof IConstructor)) {
			throw new IllegalArgumentException("factoids should return Factoid data-types");
		}
		
		IConstructor cons = (IConstructor) result;
			
		if (!cons.getName().equals("factoid")) {
			throw new IllegalArgumentException("factoids should return Factoid data-types");
		}

		factoid.setFactoid(((IString) cons.get("freetext")).getValue());
		factoid.setStars(RascalToPongo.toRating((IConstructor) cons.get("rating")));
	}


}
