package org.ossmeter.metricprovider.rascal;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.rascal.trans.model.ListMeasurement;
import org.ossmeter.metricprovider.rascal.trans.model.Measurement;
import org.ossmeter.metricprovider.rascal.trans.model.RascalMetrics;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;
import com.mongodb.DB;

/**
 * Wraps a transient metric provider to be an historic one
 */
public class RascalMetricHistoryWrapper implements IHistoricalMetricProvider {
	private final ITransientMetricProvider<RascalMetrics> transientId;
	private MetricProviderContext context;

	public RascalMetricHistoryWrapper(ITransientMetricProvider<RascalMetrics> transientProvider) {
		this.transientId = transientProvider;
	}
	
	@Override
	public String getIdentifier() {
		return transientId.getIdentifier() + ".historic";
	}

	@Override
	public String getShortIdentifier() {
		return transientId.getShortIdentifier() + ".historic";
	}

	@Override
	public String getFriendlyName() {
		return "Historic " + transientId.getShortIdentifier();
	}

	@Override
	public String getSummaryInformation() {
		return "Historic version of:\n" + transientId.getSummaryInformation();
	}

	@Override
	public boolean appliesTo(Project project) {
		return transientId.appliesTo(project);
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		uses.add(transientId);

	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(transientId.getIdentifier());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public Pongo measure(Project project) {
		DB db = context.getProjectDB(project);
		RascalMetrics result = transientId.adapt(db);
		
		ListMeasurement list = new ListMeasurement();
		List<Measurement> collection = list.getValue();
		
		for (Measurement m : result.getMeasurements()) {
			collection.add(m);
		}
  
		return list;
	}
	
}
