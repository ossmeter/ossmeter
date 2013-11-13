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
import org.ossmeter.metricprovider.rascal.metric.trans.model.NOA;
import org.ossmeter.metricprovider.rascal.metric.trans.model.NOAData;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.repository.model.Project;
import com.mongodb.DB;

public class NumberOfAttributes extends RascalMetrics implements ITransientMetricProvider<NOA>{
	
	public NumberOfAttributes() {
		this.module = "NOA";
		this.function = "getNOA";
		RascalManager.importModule(this.module);
	}

	@Override
	public String getIdentifier() {
		return NumberOfAttributes.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "NOA";
	}

	@Override
	public String getFriendlyName() {
		return "Number of Attributes";
	}

	@Override
	public String getSummaryInformation() {
		return "Counts number of attributes per class";
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
		return null;
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public NOA adapt(DB db) {
		return new NOA(db); 
	}

	@Override
	public void measure(Project project, ProjectDelta delta, NOA db) {
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
						NOAData noaData = db.getNumberOfAttributes().findOneByClassName(key);
						if (noaData == null) {
							noaData = new NOAData();
						}
						noaData.setDate(delta.getDate().toString());
						noaData.setRevisionNumber(rev);
						noaData.setClassName(key);
						noaData.setAttributes(((IInteger)currentEntry.getValue()).longValue());
						db.getNumberOfAttributes().add(noaData);
					}
				}
			}
			db.sync();
		}
	}

}
