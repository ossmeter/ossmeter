package org.ossmeter.metricprovider.rascal.metric;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.ossmeter.metricprovider.rascal.RascalMetrics;
import org.ossmeter.metricprovider.rascal.RascalManager;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.ossmeter.metricprovider.rascal.metric.trans.model.LinesOfCodeData;
import org.ossmeter.metricprovider.rascal.metric.trans.model.Loc;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.repository.model.Project;
import com.mongodb.DB;

public class LinesOfCode extends RascalMetrics implements ITransientMetricProvider<Loc> {
	
	public LinesOfCode() {
		this.module = "LOC";
		this.function = "countLoc";
		RascalManager.importModule(this.module);
	}
	
	public Loc adapt(DB db) {
		return new Loc(db);
	}

	@Override
	public String getIdentifier() {
		return LinesOfCode.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "LOC";
	}

	@Override
	public String getFriendlyName() {
		return "Lines Of Code";
	}

	@Override
	public String getSummaryInformation() {
		return "Counts physical, commented, empty and source lines of code per file";
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
		return Collections.emptyList();
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public void measure(Project project, ProjectDelta delta, Loc db) {
		manager = RascalManager.getInstance(project.getName());
		
		TreeMap<String, HashMap<String, IValue>> revisions = super.getM3s(project, delta);
		for (String rev : revisions.keySet()) {
			HashMap<String, IValue> fileM3 = revisions.get(rev);
			for (String fileURL : fileM3.keySet()) {
				ITuple result = (ITuple) manager.callRascal(module, function, new IValue[] {fileM3.get(fileURL)}); 
				LinesOfCodeData locData = db.getLinesOfCode().findOneByFile(fileURL);//new LinesOfCodeData();
				if (locData == null) {
					locData = new LinesOfCodeData();
				}
				locData.setFile(fileURL);
				locData.setDate(delta.getDate().toString());
				locData.setRevisionNumber(rev);
				locData.setTotalLines(((IInteger)result.get(0)).longValue());
				locData.setCommentedLines(((IInteger)result.get(1)).longValue());
				locData.setEmptyLines(((IInteger)result.get(2)).longValue());
				locData.setSourceLines(((IInteger)result.get(3)).longValue());
				db.getLinesOfCode().add(locData);
			}
			db.sync();
		}
	}
}
