package org.ossmeter.metricprovider.historic.numberofcommitters.model;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.trans.committers.CommittersMetricProvider;
import org.ossmeter.metricprovider.trans.committers.model.ProjectCommitters;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class CommittersOverTime implements IHistoricalMetricProvider {

	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	@Override
	public String getIdentifier() {
		return CommittersOverTime.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "committersovertime";
	}

	@Override
	public String getFriendlyName() {
		return "Number of committers over time";
	}

	@Override
	public String getSummaryInformation() {
		return "The number of committers on the project over time.";
	}

	@Override
	public boolean appliesTo(Project project) {
		return project.getVcsRepositories().size() > 0;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(CommittersMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public Pongo measure(Project project) {
		
		CommittersMetricProvider cmp = (CommittersMetricProvider)uses.get(0);
		ProjectCommitters pc = cmp.adapt(context.getProjectDB(project));
		
		Committers committers = new Committers();
		committers.setNumberOfCommitters((int)pc.getCommitters().size());
		
		return committers;
	}
}
