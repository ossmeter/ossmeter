package org.ossmeter.metricprovider.rascal;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IReal;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.visitors.NullVisitor;
import org.ossmeter.metricprovider.rascal.trans.model.IntegerMeasurement;
import org.ossmeter.metricprovider.rascal.trans.model.ListMeasurement;
import org.ossmeter.metricprovider.rascal.trans.model.Measurement;
import org.ossmeter.metricprovider.rascal.trans.model.MeasurementCollection;
import org.ossmeter.metricprovider.rascal.trans.model.RascalMetrics;
import org.ossmeter.metricprovider.rascal.trans.model.RealMeasurement;
import org.ossmeter.metricprovider.rascal.trans.model.SetMeasurement;
import org.ossmeter.metricprovider.rascal.trans.model.StringMeasurement;
import org.ossmeter.metricprovider.rascal.trans.model.URIMeasurement;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.vcs.VcsCommit;
import org.ossmeter.platform.delta.vcs.VcsRepositoryDelta;
import org.ossmeter.platform.vcs.workingcopy.manager.Churn;
import org.ossmeter.platform.vcs.workingcopy.manager.WorkingCopyCheckoutException;
import org.ossmeter.platform.vcs.workingcopy.manager.WorkingCopyFactory;
import org.ossmeter.platform.vcs.workingcopy.manager.WorkingCopyManagerUnavailable;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.VcsRepository;
import org.rascalmpl.interpreter.env.KeywordParameter;
import org.rascalmpl.interpreter.result.ICallableValue;
import org.rascalmpl.interpreter.result.OverloadedFunction;
import org.rascalmpl.interpreter.result.RascalFunction;
import org.rascalmpl.interpreter.result.Result;

import com.googlecode.pongo.runtime.Pongo;
import com.mongodb.DB;

public class RascalMetricProvider implements ITransientMetricProvider<RascalMetrics> {
	private static final String SCRATCH_FOLDERS_PARAM = "scratchFolders";
	private static final String WORKING_COPIES_PARAM = "workingCopies";
	private static final String PREVIOUS_PARAM = "previous";
	private static final String HISTORY_PARAM = "history";
	private static final String DELTA_PARAM = "delta";
	private static final String ASTS_PARAM = "asts";
	private static final String M3S_PARAM = "m3s";

	private final boolean needsM3;
	private final boolean needsAsts;
	private final boolean needsDelta;
	private final boolean needsHistory;
	private final boolean needsPrevious;
	private final boolean needsWc;
	private final boolean needsScratch;

	private final String description;
	private final String friendlyName;
	private final String shortMetricId;
	private final String metricId;
	private final ICallableValue function;

	private static String lastRevision = null;
	private static IValue cachedM3 = null;
	private static IValue cachedAsts = null;

	private static Map<String, File> workingCopyFolders = new HashMap<>();
	private static Map<String, File> scratchFolders = new HashMap<>();
	private static IConstructor rascalDelta;

	public RascalMetricProvider(String metricId, String shortMetricId, String friendlyName, String description, ICallableValue function) {
		this.metricId = metricId;
		this.shortMetricId =  shortMetricId;
		this.friendlyName = friendlyName;
		this.description = description;
		this.function = function;

		this.needsM3 = hasParameter(M3S_PARAM);
		this.needsAsts = hasParameter(ASTS_PARAM);
		this.needsDelta = hasParameter(DELTA_PARAM);
		this.needsHistory = hasParameter(HISTORY_PARAM);
		this.needsPrevious = hasParameter(PREVIOUS_PARAM);
		this.needsWc = hasParameter(WORKING_COPIES_PARAM);
		this.needsScratch = hasParameter(SCRATCH_FOLDERS_PARAM);
		
		assert function instanceof RascalFunction;
	}

	private boolean hasParameter(String param) {
		ICallableValue f = function;
		
		if (f instanceof OverloadedFunction) {
			f = ((OverloadedFunction) f).getFunctions().get(0);
		}
		
		assert f instanceof RascalFunction;
		
		RascalFunction rf = (RascalFunction) f;
		
		for (KeywordParameter p : rf.getKeywordParameterDefaults()) {
			if (p.getName().equals(param)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public String toString() {
		return getIdentifier();
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
		return Collections.emptyList();
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {	}

	@Override
	public RascalMetrics adapt(DB db) {
		RascalMetrics rm = new RascalMetrics(db, this.metricId);
		return rm;
	}

	@Override
	public void measure(Project project, ProjectDelta delta, RascalMetrics db) {
		try {
			RascalManager manager = RascalManager.getInstance();
			List<VcsRepositoryDelta> repoDeltas = delta.getVcsDelta().getRepoDeltas();
			Map<String, IValue> params = new HashMap<>();

			synchronized (RascalMetricProvider.class) {
				if (lastRevision == null) {
					// the very first time, this will still be null, so we need to check for null below as well
					lastRevision = repoDeltas.get(repoDeltas.size()-1).getLatestRevision();
				}

				if (needCacheClearance(delta)) {
					cachedM3 = null;
					cachedAsts = null;
					workingCopyFolders.clear();
					scratchFolders.clear();
					rascalDelta = null;
				}

				if (needsScratch || needsWc || needsM3 || needsAsts || needsDelta) {
					if (workingCopyFolders.isEmpty() || scratchFolders.isEmpty()) {
						computeFolders(project, delta, manager, workingCopyFolders, scratchFolders);
					}

					if (needsWc) {
						params.put(WORKING_COPIES_PARAM, manager.makeMap(workingCopyFolders));
					}

					if (needsScratch) {
						params.put(SCRATCH_FOLDERS_PARAM, manager.makeMap(scratchFolders));
					}
				}

				if (needsDelta) {
					params.put(DELTA_PARAM, computeDelta(project, delta, manager));
				}

				if (needsAsts) {
					params.put(ASTS_PARAM, computeAsts(project, delta, manager));
				}

				if (needsM3) {
					params.put(M3S_PARAM, computeM3(project, delta, manager));
				}

				if (needsHistory) {
					params.put(HISTORY_PARAM, computeHistory(project, delta, manager));
				}

				if (needsPrevious) {
					params.put(PREVIOUS_PARAM, computePrevious(project, db, delta, manager));
				}
			}

			Result<IValue> result = function.call(new Type[] { }, new IValue[] { }, params);

			lastRevision = getLastRevision(delta);

			storeResult(delta, db, result.getValue(), manager);
		} catch (WorkingCopyManagerUnavailable | WorkingCopyCheckoutException  e) {
			Rasctivator.logException("unexpected exception while measuring " + getIdentifier(), e);
		}
	}

	private IValue computePrevious(Project project, RascalMetrics db, ProjectDelta delta, RascalManager man) {
		return convertBack(db, man);
	}

	private IValue computeHistory(Project project, ProjectDelta delta,
			RascalManager _instance) {
		// TODO
		throw new UnsupportedOperationException();
	}

	private void computeFolders(Project project, ProjectDelta delta, RascalManager _instance, Map<String, File> wc, Map<String, File> scratch) throws WorkingCopyManagerUnavailable, WorkingCopyCheckoutException {
		WorkingCopyFactory.getInstance().checkout(project, getLastRevision(delta), wc, scratch);
	}

	private String getLastRevision(ProjectDelta delta) {
		List<VcsRepositoryDelta> repoDeltas = delta.getVcsDelta().getRepoDeltas();
		VcsRepositoryDelta deltas = repoDeltas.get(repoDeltas.size() - 1);
		List<VcsCommit> commits = deltas.getCommits();
		String revision = commits.get(commits.size() - 1).getRevision();
		return revision;
	}

	private IValue computeAsts(Project project, ProjectDelta delta,
			RascalManager _instance) {
		assert !workingCopyFolders.isEmpty();

		if (cachedAsts == null) {
			cachedAsts = function.getEval().call("extractAST", _instance.makeProjectLoc(project), _instance.makeLocSet(workingCopyFolders));
		}

		return cachedAsts;
	}

	private void storeResult(ProjectDelta delta, RascalMetrics db, IValue result, RascalManager _instance) {
		// TODO: instead save the current state and do a diff later for optimal communication with the db.
		MeasurementCollection ms = db.getMeasurements();
		for (Measurement m : ms) {
			ms.remove(m);
		}
		convert(ms, _instance.makeProjectLoc(delta.getProject()), result);
		db.sync();
	}
	
	private IValue convertBack(RascalMetrics m, RascalManager man) {
		return man.toValue(m);
	}
	
	private void convert(final MeasurementCollection measurements, final ISourceLocation loc, IValue result) {
		result.accept(new NullVisitor<Void,RuntimeException>() {
			@Override
			public Void visitInteger(IInteger o) throws RuntimeException {
				IntegerMeasurement m = new IntegerMeasurement();
				m.setUri(loc.getURI().toString());
				m.setValue(o.longValue());
				measurements.add(m);
				return null;
			}
			
			@Override
			public Void visitString(IString o) throws RuntimeException {
				StringMeasurement m = new StringMeasurement();
				m.setUri(loc.getURI().toString());
				m.setValue(o.getValue());
				measurements.add(m);
				return null;
			}
			
			@Override
			public Void visitReal(IReal o) throws RuntimeException {
				RealMeasurement m = new RealMeasurement();
				m.setUri(loc.getURI().toString());
				m.setValue((float) o.doubleValue());
				measurements.add(m);
				return null;
			}
			
			@Override
			public Void visitSourceLocation(ISourceLocation o) {
				URIMeasurement m = new URIMeasurement();
				m.setUri(loc.getURI().toString());
				m.setValue(o.getURI().toString());
				measurements.add(m);
				return null;
			}
			
			@Override
			public Void visitMap(IMap o) throws RuntimeException {
				for (Iterator<Entry<IValue, IValue>> it = o.entryIterator(); it.hasNext(); ) {
					Entry<IValue, IValue> currentEntry = (Entry<IValue, IValue>) it.next();
					ISourceLocation key = (ISourceLocation) currentEntry.getKey();
					convert(measurements, key, currentEntry.getValue());
				}
				return null;
			}

			@Override
			public Void visitList(IList o) throws RuntimeException {
				ListMeasurement m = new ListMeasurement();
				final MeasurementCollection col = m.getValue();
				
				for (IValue val : o) {
					convert(col, loc, val);
				}
				return null;
			}
			
			@Override
			public Void visitSet(ISet o) throws RuntimeException {
				SetMeasurement m = new SetMeasurement();
				final MeasurementCollection col = m.getValue();
				
				for (IValue val : o) {
					convert(col, loc, val);
				}
				return null;
			}
		});
	}

	private IConstructor computeDelta(Project project, ProjectDelta delta,
			RascalManager _instance) {
		RascalProjectDeltas rpd = new RascalProjectDeltas(_instance.getEvaluator());
		List<VcsRepositoryDelta> repoDeltas = delta.getVcsDelta().getRepoDeltas();

		if (repoDeltas.isEmpty()) { 
			return rpd.emptyDelta();
		}

		List<VcsCommit> deltaCommits = repoDeltas.get(repoDeltas.size()-1).getCommits();

		// check if we can reuse the previously cached delta, if not lets compute a new one
		if (rascalDelta == null) {
			Map<VcsCommit, List<Churn>> churnPerCommit = new HashMap<>();

			for (VcsCommit commit: deltaCommits) {
				assert !workingCopyFolders.isEmpty();
				VcsRepository repo = commit.getDelta().getRepository();

				try {
					List<Churn> currentChurn = WorkingCopyFactory.getInstance().getDiff(repo, workingCopyFolders.get(repo.getUrl()), RascalMetricProvider.lastRevision);
					churnPerCommit.put(commit, currentChurn);
				} catch (WorkingCopyManagerUnavailable e) {
					Rasctivator.logException("exception while diffing " + repo.getUrl(), e);
				}
			}

			rascalDelta = rpd.convert(delta, churnPerCommit);
		}

		return rascalDelta;
	}

	private boolean needCacheClearance(ProjectDelta delta) {
		if (lastRevision == null) {
			return true;
		}

		List<VcsRepositoryDelta> repoDeltas = delta.getVcsDelta().getRepoDeltas();
		if (repoDeltas.size() > 0) {
			List<VcsCommit> deltaCommits = repoDeltas.get(repoDeltas.size()-1).getCommits();
			
			if (deltaCommits.size() > 0) {
				return !deltaCommits.get(deltaCommits.size()-1).getRevision().equals(RascalMetricProvider.lastRevision);
			}
		}
		
		// no delta?
		return false;
	}


	private IValue computeM3(Project project, ProjectDelta delta, RascalManager man) {
		assert !workingCopyFolders.isEmpty();

		if (cachedM3 == null) {
			cachedM3 = function.getEval().call("extractM3", man.makeProjectLoc(project), man.makeMap(workingCopyFolders));
		}

		return cachedM3;
	}
}
