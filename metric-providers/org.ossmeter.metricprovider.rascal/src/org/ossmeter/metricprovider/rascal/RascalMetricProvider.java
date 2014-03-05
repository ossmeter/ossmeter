package org.ossmeter.metricprovider.rascal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class RascalMetricProvider implements ITransientMetricProvider<PongoDB> {

  private final String description;
  private final String friendlyName;
  private final String shortMetricId;
  private final String metricId;
  private final String module;
  private final String function;
  private MetricProviderContext context;

  public RascalMetricProvider(String metricId, String shortMetricId, String friendlyName, String description, String module, String function) {
    this.metricId = metricId;
    this.shortMetricId =  shortMetricId;
    this.friendlyName = friendlyName;
    this.description = description;
    this.module = module;
    this.function = function;
    RascalManager.importModule(this.module);
  }
  
	@Override
	public String getIdentifier() {
		return metricId;
	}

	@Override
	public String getShortIdentifier() {
		return shortMetricId;
	}

	@Override
	public String getFriendlyName() {
		return friendlyName;
	}

	@Override
	public String getSummaryInformation() {
		return description;
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
	public PongoDB adapt(DB db) {
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
