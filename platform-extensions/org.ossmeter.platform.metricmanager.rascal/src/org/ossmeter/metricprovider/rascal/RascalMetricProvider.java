package org.ossmeter.metricprovider.rascal;

import static org.ossmeter.metricprovider.rascal.PongoToRascal.makeMap;
import static org.ossmeter.metricprovider.rascal.PongoToRascal.makeProjectLoc;
import static org.ossmeter.metricprovider.rascal.RascalToPongo.toPongo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.io.StandardTextWriter;
import org.eclipse.imp.pdb.facts.type.Type;
import org.ossmeter.metricprovider.rascal.trans.model.Measurement;
import org.ossmeter.metricprovider.rascal.trans.model.MeasurementCollection;
import org.ossmeter.metricprovider.rascal.trans.model.RascalMetrics;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.vcs.VcsCommit;
import org.ossmeter.platform.delta.vcs.VcsRepositoryDelta;
import org.ossmeter.platform.logging.OssmeterLogger;
import org.ossmeter.platform.vcs.workingcopy.manager.Churn;
import org.ossmeter.platform.vcs.workingcopy.manager.WorkingCopyCheckoutException;
import org.ossmeter.platform.vcs.workingcopy.manager.WorkingCopyFactory;
import org.ossmeter.platform.vcs.workingcopy.manager.WorkingCopyManagerUnavailable;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.VcsRepository;
import org.rascalmpl.interpreter.control_exceptions.MatchFailed;
import org.rascalmpl.interpreter.control_exceptions.Throw;
import org.rascalmpl.interpreter.env.KeywordParameter;
import org.rascalmpl.interpreter.result.AbstractFunction;
import org.rascalmpl.interpreter.result.RascalFunction;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.interpreter.staticErrors.StaticError;
import org.rascalmpl.interpreter.utils.LimitedResultWriter;
import org.rascalmpl.interpreter.utils.LimitedResultWriter.IOLimitReachedException;

import com.mongodb.DB;

public class RascalMetricProvider implements ITransientMetricProvider<RascalMetrics> {
	private static final String SCRATCH_FOLDERS_PARAM = "scratchFolders";
	private static final String WORKING_COPIES_PARAM = "workingCopies";
	private static final String PREVIOUS_PARAM = "prev";
	private static final String DELTA_PARAM = "delta";
	private static final String ASTS_PARAM = "asts";
	private static final String M3S_PARAM = "m3s";

	private final boolean needsM3;
	private final boolean needsAsts;
	private final boolean needsDelta;
	private final boolean needsWc;
	private final boolean needsScratch;
	
	private final Map<String,String> uses;
	private final Map<String, IMetricProvider> providers;

	private final String description;
	private final String friendlyName;
	private final String shortMetricId;
	private final String metricId;
	private final AbstractFunction function;
	private final OssmeterLogger logger;
	private MetricProviderContext context;
	private boolean needsPrev;
	private static final RascalManager manager = RascalManager.getInstance();

	private static String lastRevision = null;
	
	private static Map<String, File> workingCopyFolders = new HashMap<>();
	private static Map<String, File> scratchFolders = new HashMap<>();
	private static IConstructor rascalDelta;
	
	
	public RascalMetricProvider(String metricId, String shortMetricId, String friendlyName, String description, AbstractFunction function, Map<String,String> uses) {
		this.metricId = metricId;
		this.shortMetricId =  shortMetricId;
		this.friendlyName = friendlyName;
		this.description = description;
		this.function = function;
		this.uses = uses;
		this.providers = new HashMap<String,IMetricProvider>();
		
		this.needsM3 = hasParameter(M3S_PARAM);
		this.needsAsts = hasParameter(ASTS_PARAM);
		this.needsDelta = hasParameter(DELTA_PARAM);
		this.needsWc = hasParameter(WORKING_COPIES_PARAM);
		this.needsScratch = hasParameter(SCRATCH_FOLDERS_PARAM);
		this.needsPrev = hasParameter(PREVIOUS_PARAM);
		
		this.logger = (OssmeterLogger) OssmeterLogger.getLogger("RascalMetricProvider (" + friendlyName + ")");
		this.logger.addConsoleAppender(OssmeterLogger.DEFAULT_PATTERN);
		
		assert function instanceof RascalFunction;
	}

	private boolean hasParameter(String param) {
		for (KeywordParameter p : function.getKeywordParameterDefaults()) {
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
		for (IMetricProvider provider : uses) {
			providers.put(provider.getIdentifier(), provider);
		}
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		List<String> names = new ArrayList<String>(uses.size());
		names.addAll(uses.keySet());
		return names;
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {	
		this.context = context;
	}

	@Override
	public RascalMetrics adapt(DB db) {
		return new RascalMetrics(db, this.metricId);
	}

	@Override
	public void measure(Project project, ProjectDelta delta, RascalMetrics db) {
		IValue result = compute(project, delta);
		
		if (result != null) {
			logger.info("storing metric result");
			storeResult(delta, db, result);
		}
	}
	
	public IValue compute(Project project, ProjectDelta delta) {
		try {
			List<VcsRepositoryDelta> repoDeltas = delta.getVcsDelta().getRepoDeltas();
			Map<String, IValue> params = new HashMap<>();

			synchronized (RascalMetricProvider.class) {
				if (lastRevision == null) {
					// the very first time, this will still be null, so we need to check for null below as well
					lastRevision = repoDeltas.get(repoDeltas.size()-1).getLatestRevision();
				}

				if (needCacheClearance(delta)) {
					logger.info("\tclearing caches");
					workingCopyFolders.clear();
					scratchFolders.clear();
					rascalDelta = null;
				}

				if (needsScratch || needsWc || needsM3 || needsAsts || needsDelta) {
					if (workingCopyFolders.isEmpty() || scratchFolders.isEmpty()) {
						logger.info("creating working copies");
						computeFolders(project, delta, workingCopyFolders, scratchFolders);
					}

					if (needsWc) {
						params.put(WORKING_COPIES_PARAM, makeMap(workingCopyFolders));
					}

					if (needsScratch) {
						params.put(SCRATCH_FOLDERS_PARAM, makeMap(scratchFolders));
					}
				}

				if (needsDelta) {
					logger.info("computing delta model");
					params.put(DELTA_PARAM, computeDelta(project, delta, manager));
				}

				if (needsAsts) {
					logger.info("parsing to asts");
					params.put(ASTS_PARAM, computeAsts(project, delta, manager));
				}

				if (needsPrev) {
					logger.info("retrieving current result");
					params.put(PREVIOUS_PARAM, getMetricResult(project, this, manager));
				}
				
				if (needsM3) {
					logger.info("extracting M3 models");
					params.put(M3S_PARAM, computeM3(project, delta, manager));
				}

				for (String use : uses.keySet()) {
					IMetricProvider provider = providers.get(use);
					
					if (provider != null) {
						String label = uses.get(use);
						IValue val = getMetricResult(project, provider, manager);
						params.put(label, val);
					} else {
						logger.error("Used metric provider " + use + " was not found! " + use);
							// name mismatch!
						logger.info("Select from:");
						for (IMetricProvider imp : manager.getMetricProviders()) {
							logger.info("\t" + imp.getIdentifier());
						}
						return null;
					}
					
				}
				
				filterNullParameters(params);
				
				// measurement is included in the sync block to avoid sharing evaluators between metrics
				logger.info("calling measurement function");
				//logger.info("with parameters: " + params);
				Result<IValue> result = function.call(new Type[] { }, new IValue[] { }, params);

				logResult(result);
				
				lastRevision = getLastRevision(delta);
				return result.getValue();
			}
		} catch (Throw e) {
		    if (!(e.getException() instanceof IConstructor && ((IConstructor) e.getException()).getName().equals("undefined"))) {
		    	logger.error("metric threw an exception: " + e.getMessage() + " at " + e.getLocation(), e);
		    	throw e;
		    }
		    else {
		    	logger.info("metric was undefined for the input of today, ignoring.");
		    	return null;
		    }
		} catch (WorkingCopyManagerUnavailable | WorkingCopyCheckoutException  e) {
			logger.error("unexpected exception while measuring", e);
			throw new RuntimeException("Metric failed due to missing working copy", e);
		} catch (StaticError e) {
			logger.error("a static error in a Rascal function was detected", e);
			throw e;
		} catch (MatchFailed e) {
			logger.error("a Rascal function was called with illegal arguments", e);
			throw e;
		} catch (IOException e) {
			throw new RuntimeException("Metric failed for unknown reasons", e);
		}
	}

	private void filterNullParameters(Map<String, IValue> params) {
		Set<Entry<String, IValue>> entrySet = params.entrySet();
		Iterator<Entry<String, IValue>> it = entrySet.iterator();
		while (it.hasNext()) {
			Entry<String,IValue> entry = it.next(); 
			if (entry.getValue() == null) {
				it.remove();
			}
		}
	}

	private void logResult(Result<IValue> result) throws IOException {
		LimitedResultWriter str = new LimitedResultWriter(100);
		try {
			new StandardTextWriter().write(result.getValue(), str);
		}
		catch (IOLimitReachedException e) { }
		finally {
			logger.info("measurement result: " + str.toString());
		}
	}

	// TODO: add support for other kinds of metric providers
	private IValue getMetricResult(Project project, IMetricProvider provider, RascalManager man) {
		Type type = provider instanceof RascalMetricProvider ? ((RascalMetricProvider) provider).getReturnType() : ((RascalMetricHistoryWrapper) provider).getValueType();
		DB db = context.getProjectDB(project);
		RascalMetrics rascalMetrics = new RascalMetrics(db, provider.getIdentifier());
		
		return PongoToRascal.toValue(rascalMetrics, type, provider instanceof RascalMetricHistoryWrapper);
	}

	public Type getReturnType() {
		return function.getReturnType();
	}

	private void computeFolders(Project project, ProjectDelta delta, Map<String, File> wc, Map<String, File> scratch) throws WorkingCopyManagerUnavailable, WorkingCopyCheckoutException {
		WorkingCopyFactory.getInstance().checkout(project, getLastRevision(delta), wc, scratch);
	}

	private String getLastRevision(ProjectDelta delta) {
		List<VcsRepositoryDelta> repoDeltas = delta.getVcsDelta().getRepoDeltas();
		if (repoDeltas.isEmpty()) {
			return lastRevision;
		}
		VcsRepositoryDelta deltas = repoDeltas.get(repoDeltas.size() - 1);
		List<VcsCommit> commits = deltas.getCommits();
		String revision = commits.get(commits.size() - 1).getRevision();
		return revision;
	}

	private IValue computeAsts(Project project, ProjectDelta delta,	RascalManager _instance) {
		assert !workingCopyFolders.isEmpty() && delta != null;
		return callExtractors(project, delta, _instance, _instance.getASTExtractors());
	}

	protected void storeResult(ProjectDelta delta, RascalMetrics db, IValue result) {
		// TODO: instead save the current state and do a diff later for optimal communication with the db.
		MeasurementCollection ms = db.getMeasurements();
		for (Measurement m : ms) {
			ms.remove(m);
		}
		db.sync();
		toPongo(ms, result);
		db.sync();
	}
	
	private IConstructor computeDelta(Project project, ProjectDelta delta,
			RascalManager _instance) {
		logger.info("\tretrieving from VcsProvider");
		RascalProjectDeltas rpd = new RascalProjectDeltas(_instance.getEvaluator());
		List<VcsRepositoryDelta> repoDeltas = delta.getVcsDelta().getRepoDeltas();

		if (repoDeltas.isEmpty()) { 
			return rpd.emptyDelta(delta);
		}

		List<VcsCommit> deltaCommits = repoDeltas.get(repoDeltas.size()-1).getCommits();

		// check if we can reuse the previously cached delta, if not lets compute a new one
		if (rascalDelta == null) {
			Map<VcsCommit, List<Churn>> churnPerCommit = new HashMap<>();

			logger.info("\tcomputing actual source code differences");
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

			logger.info("\tconverting delta model to Rascal values");
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
		assert !workingCopyFolders.isEmpty() && delta != null;
		return callExtractors(project, delta, man, man.getM3Extractors());
	}
	
	private IValue callExtractors(Project project, ProjectDelta delta, RascalManager man, Set<RascalManager.Extractor> extractors) {
		ISetWriter allResults = man.getEvaluator().getValueFactory().setWriter();;
		
		ISourceLocation projectLoc = makeProjectLoc(project);
		IMap wcf = makeMap(workingCopyFolders);
		IMap scratch = makeMap(scratchFolders);
		IConstructor rascalDelta = computeDelta(project, delta, man);
		
		for (RascalManager.Extractor e : extractors) {
			// generally extractors are assume to use @memo
			ISet result = (ISet) e.call(projectLoc, rascalDelta, wcf, scratch);
			allResults.insertAll(result);
		}
		
		return allResults.done(); // TODO what if null?
	}
	
	
}
