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
import org.ossmeter.metricprovider.rascal.metric.trans.model.Nom;
import org.ossmeter.metricprovider.rascal.metric.trans.model.NumberOfMethodsData;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.Project;
import org.ossmeter.platform.delta.ProjectDelta;
import com.mongodb.DB;

public class NumberOfMethods extends RascalMetrics implements ITransientMetricProvider<Nom> {
	
	public NumberOfMethods() {
		this.module = "NOM";
		this.function = "getNOM";
		RascalManager.importModule(this.module);
	}

	@Override
	public Nom adapt(DB db) {
		return new Nom(db);
	}

	@Override
	public String getIdentifier() {
		return NumberOfMethods.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "NOM";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Methods";
	}

	@Override
	public String getSummaryInformation() {
		return "Counts number of methods per class";
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
	public void measure(Project project, ProjectDelta delta, Nom db) {
		manager = RascalManager.getInstance(project.getName());
		TreeMap<String, HashMap<String, IValue>> revisions = super.getM3s(project, delta);
		for (String rev : revisions.keySet()) {
			List<IValue> fileM3s = new ArrayList<>();
			// NOM doesn't care about file url's since the keys are class names
			for (String fileURL: revisions.get(rev).keySet()) {
				fileM3s.add(revisions.get(rev).get(fileURL));
			}
			for (IValue file : fileM3s) {
				IMap result = (IMap) manager.callRascal(module, function, new IValue[] {file});
				for (Iterator<Entry<IValue, IValue>> it = result.entryIterator(); it.hasNext(); ) {
					Entry<IValue, IValue> currentEntry = (Entry<IValue, IValue>)it.next();
					String key = ((IString)currentEntry.getKey()).getValue();
					if (!key.isEmpty()) {
						NumberOfMethodsData nomData = db.getTotalNumberOfMethods().findOneByClassName(key);
						if (nomData == null) {
							nomData = new NumberOfMethodsData();
						}
						nomData.setDate(delta.getDate().toString());
						nomData.setRevisionNumber(rev);
						nomData.setClassName(key);
						nomData.setNOM(((IInteger)currentEntry.getValue()).longValue());
						db.getTotalNumberOfMethods().add(nomData);
					}
				}
			}
			db.sync();
		}
	}
}
