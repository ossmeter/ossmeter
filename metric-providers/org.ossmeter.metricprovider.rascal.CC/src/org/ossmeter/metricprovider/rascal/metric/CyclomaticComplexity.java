package org.ossmeter.metricprovider.rascal.metric;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IValue;
import org.ossmeter.metricprovider.rascal.RascalManager;
import org.ossmeter.metricprovider.rascal.RascalMetrics;
import org.ossmeter.metricprovider.rascal.metric.trans.model.CC;
import org.ossmeter.metricprovider.rascal.metric.trans.model.CCData;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.repository.model.Project;
import com.mongodb.DB;

public class CyclomaticComplexity extends RascalMetrics implements ITransientMetricProvider<CC> {

	public CyclomaticComplexity() {
		this.module = "WMC";
		this.function = "getCC";
		RascalManager.importModule(this.module);
	}

	@Override
	public String getIdentifier() {
		return CyclomaticComplexity.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "CC";
	}

	@Override
	public String getFriendlyName() {
		return "McCabe's Cyclomatic Complexity";
	}

	@Override
	public String getSummaryInformation() {
		return "Calculates McCabe's Cyclomatic Complexity";
	}

	@Override
	public boolean appliesTo(Project project) {
		return project.getVcsRepositories().size() > 0;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		
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
	public CC adapt(DB db) {
		return new CC(db);
	}

	@Override
	public void measure(Project project, ProjectDelta delta, CC db) {
		manager = RascalManager.getInstance(project.getName());
		TreeMap<String, HashMap<String, IValue>> revisions = getM3s(project, delta);
		for (String rev: revisions.keySet()) {
			HashMap<String, IValue> fileM3s = revisions.get(rev);
			for (String fileURL: fileM3s.keySet()) {
				IMap result = (IMap) manager.callRascal(module, function, new IValue[] {fileM3s.get(fileURL)});
				for (Iterator<Entry<IValue, IValue>> it = result.entryIterator(); it.hasNext(); ) {
					Entry<IValue, IValue> currentEntry = (Entry<IValue, IValue>)it.next();
					String key = ((IString)currentEntry.getKey()).getValue();
					if (!key.isEmpty()) {
						CCData noaData = null;
						// for cc need to delete all methods for this file
						for (Iterator<CCData> ccIt = db.getMethodCC().findByFileURL(fileURL).iterator(); ccIt.hasNext(); ) {
							noaData = (CCData) ccIt.next();
							db.getMethodCC().remove(noaData);
						}
						noaData = new CCData();
						noaData.setDate(delta.getDate().toString());
						noaData.setFileURL(fileURL);
						noaData.setRevisionNumber(rev);
						noaData.setMethodName(key);
						noaData.setCcValue(((IInteger)currentEntry.getValue()).longValue());
						db.getMethodCC().add(noaData);
					}
				}
			}
			db.sync();
		}
	}
}
