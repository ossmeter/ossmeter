package org.ossmeter.metricprovider.rascal.metric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.ossmeter.metricprovider.rascal.RascalManager;
import org.ossmeter.metricprovider.rascal.RascalMetrics;
import org.ossmeter.metricprovider.rascal.metric.trans.model.WMC;
import org.ossmeter.metricprovider.rascal.metric.trans.model.WMCData;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.repository.model.Project;
import com.mongodb.DB;

public class WeightedMethodsCount extends RascalMetrics implements ITransientMetricProvider<WMC>{

	public WeightedMethodsCount() {
		this.module = "WMC";
		this.function = "getWMC";
		RascalManager.importModule(this.module);
	}

	@Override
	public String getIdentifier() {
		return WeightedMethodsCount.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "WMC";
	}

	@Override
	public String getFriendlyName() {
		return "Weighted Method Count";
	}

	@Override
	public String getSummaryInformation() {
		return "Counts weighted method counts per class";
	}

	@Override
	public boolean appliesTo(Project project) {
		return project.getVcsRepositories().size() > 0;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public WMC adapt(DB db) {
		return new WMC(db);
	}

	@Override
	public void measure(Project project, ProjectDelta delta, WMC db) {
		manager = RascalManager.getInstance(project.getName());
		TreeMap<String, HashMap<String, IValue>> revisions = getM3s(project, delta);
		for (String rev: revisions.keySet()) {
			HashMap<String, IValue> files = revisions.get(rev);
			List<IValue> fileM3s = new ArrayList<>();
			//Again don't care about fileURLs
			for (String fileURL: files.keySet()) {
				fileM3s.add(files.get(fileURL));
			}
			for (IValue fileM3: fileM3s) {
				IMap result = (IMap) manager.callRascal(module, function, new IValue[] {fileM3});
				for (Iterator<Entry<IValue, IValue>> it = result.entryIterator(); it.hasNext(); ) {
					Entry<IValue, IValue> currentEntry = (Entry<IValue, IValue>)it.next();
					String key = ((IString)currentEntry.getKey()).getValue();
					if (!key.isEmpty()) {
						WMCData noaData = db.getWeightedMethodCount().findOneByClassName(key);
						if (noaData == null) {
							noaData = new WMCData();
						}
						noaData.setDate(delta.getDate().toString());
						noaData.setRevisionNumber(rev);
						noaData.setClassName(key);
						noaData.setWeightedCount(((IInteger)currentEntry.getValue()).longValue());
						db.getWeightedMethodCount().add(noaData);
					}
				}
			}
			db.sync();
		}
	}

}
