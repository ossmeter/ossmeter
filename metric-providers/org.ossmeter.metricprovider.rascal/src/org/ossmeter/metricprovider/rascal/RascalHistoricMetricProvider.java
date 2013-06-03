package org.ossmeter.metricprovider.rascal;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ossmeter.metricprovider.rascal.history.model.HistoricRascalMetrics;
import org.ossmeter.metricprovider.rascal.trans.model.IntegerMeasurement;
import org.ossmeter.metricprovider.rascal.trans.model.Measurement;
import org.ossmeter.metricprovider.rascal.trans.model.RascalMetrics;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class RascalHistoricMetricProvider implements IHistoricalMetricProvider{

	protected final URI metric;
	private RascalTransientMetricProvider rascalMetricProvider;
	private MetricProviderContext context;

	public RascalHistoricMetricProvider(URI metric) {
		this.metric = metric;
	}
	
	@Override
	public boolean appliesTo(Project project) {
		return true; //FIXME
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.rascalMetricProvider = (RascalTransientMetricProvider)uses.get(0);
	}


	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public Pongo measure(Project project) {
		RascalMetrics rascalMetrics = rascalMetricProvider.adapt(context.getProjectDB(project));

		Iterable<Measurement> measurements = rascalMetrics.getMeasurements().findByMetric(getIdentifier());
		
		HistoricRascalMetrics hrm = new HistoricRascalMetrics();
		Map<String, Long> map = new HashMap<String, Long>();
		
		for (Measurement m : measurements) {
			if (m instanceof IntegerMeasurement) {
				
				String key = m.getUri().split("://")[0];
				if (map.containsKey(key)) {
					map.put(key, map.get(key) + ((IntegerMeasurement) m).getValue()); 
				} else {
					map.put(key, ((IntegerMeasurement) m).getValue());
				}
			}
		}

		for (Entry<String, Long> entry : map.entrySet()){
			IntegerMeasurement im = new IntegerMeasurement();
			im.setMetric(getIdentifier());
			im.setValue(entry.getValue());
			im.setUri(entry.getKey());
			hrm.getMeasurements().add(im);
		}
		
		return hrm;
	}

	@Override
	public String getIdentifier() {
		return metric.toASCIIString() + "/historic";
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Collections.emptyList();
	}
	
}
