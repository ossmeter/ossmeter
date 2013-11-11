package org.ossmeter.metricprovider.rascal;

import java.io.File;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.ossmeter.metricprovider.rascal.trans.model.RascalMetric;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.vcs.PlatformVcsManager;
import org.ossmeter.platform.delta.vcs.VcsChangeType;
import org.ossmeter.platform.delta.vcs.VcsCommit;
import org.ossmeter.platform.delta.vcs.VcsCommitItem;
import org.ossmeter.platform.delta.vcs.VcsProjectDelta;
import org.ossmeter.platform.delta.vcs.VcsRepositoryDelta;
import org.ossmeter.platform.util.ExtensionPointHelper;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.VcsRepository;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.NullRascalMonitor;
import org.rascalmpl.interpreter.env.GlobalEnvironment;
import org.rascalmpl.interpreter.env.ModuleEnvironment;
import org.rascalmpl.interpreter.load.StandardLibraryContributor;
import org.rascalmpl.interpreter.load.URIContributor;
import org.rascalmpl.uri.ClassResourceInput;
import org.rascalmpl.uri.IURIInputStreamResolver;
import org.rascalmpl.uri.URIUtil;
import org.rascalmpl.values.ValueFactoryFactory;

import com.mongodb.DB;

public class RascalTransientMetricProvider implements ITransientMetricProvider<RascalMetric> {
	protected final static String extensionId = "org.ossmeter.metricprovider.rascal.metric";
	
	private final static GlobalEnvironment heap = new GlobalEnvironment();
	private final static ModuleEnvironment root = new ModuleEnvironment("******metrics******", heap);
	private final static IValueFactory VF = ValueFactoryFactory.getValueFactory();
	private final static OSSMeterURIResolver ossmStore = new OSSMeterURIResolver();
	private final static Evaluator eval = initEvaluator();
	private final List<RascalMetrics> rascalMetrics;

	private static Evaluator initEvaluator() {
		Evaluator eval = new Evaluator(VF, new PrintWriter(System.err), new PrintWriter(System.out), root, heap);
		IURIInputStreamResolver metrics = new ClassResourceInput(eval.getResolverRegistry(), "metrics", RascalTransientMetricProvider.class, "");
		eval.getResolverRegistry().registerInput(metrics);
		URIContributor moduleContributor = new URIContributor(URIUtil.rootScheme("metrics"));
		eval.addRascalSearchPathContributor(moduleContributor);
		eval.addRascalSearchPathContributor(StandardLibraryContributor.getInstance());
		eval.addRascalSearchPath(URIUtil.assumeCorrect("file:///Users/shahi/Documents/CWI/l1_workspace/org.ossmeter.metricprovider.rascal/modules"));
		return eval;
	}
	
	private final String module = "Manager";
	protected MetricProviderContext context;

	public RascalTransientMetricProvider() {
		eval.doImport(new NullRascalMonitor(), module);
		rascalMetrics = loadMetrics();
	}

	private List<RascalMetrics> loadMetrics() {
		List<RascalMetrics> metrics = new ArrayList<>();
		for(IConfigurationElement configurationElement : ExtensionPointHelper.getConfigurationElementsForExtensionPoint(extensionId)){
			try {
				RascalMetrics metric = (RascalMetrics) configurationElement.createExecutableExtension("provider");
				metrics.add(metric);
				eval.doImport(new NullRascalMonitor(), ((RascalMetrics) metric).getModule());
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return metrics;
	}

	private PlatformVcsManager vcsManager;

	@Override
	public String getIdentifier() {
		return RascalTransientMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "rascal";
	}

	@Override
	public String getFriendlyName() {
		return "Rascal Metrics";
	}

	@Override
	public String getSummaryInformation() {
		return "Rascal Metrics calculator.";
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
		this.vcsManager = context.getPlatformVcsManager();
	}

	@Override
	public RascalMetric adapt(DB db) {
		//return new RascalMetrics(db);
		for (RascalMetrics metric : rascalMetrics) {
			metric.adapt(db);
		}
		
		return null;
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, RascalMetric db) {
		try {
			File localStorage = new File(project.getStorage().getPath());
			VcsProjectDelta vcsDelta = projectDelta.getVcsDelta();

			for (VcsRepositoryDelta vcsRepositoryDelta : vcsDelta.getRepoDeltas()) {
				VcsRepository vcsRepository = vcsRepositoryDelta.getRepository();
				List<VcsCommit> commits = vcsRepositoryDelta.getCommits();
				for (VcsCommit commit : commits) {
					eval.call(new NullRascalMonitor(), module, "checkOutRepository", VF.string(vcsRepository.getUrl()), VF.integer(commit.getRevision()), VF.sourceLocation(localStorage.getAbsolutePath()));
					for (VcsCommitItem item : commit.getItems()) {
						if (item.getChangeType() == VcsChangeType.DELETED || item.getChangeType() == VcsChangeType.UNKNOWN) {
							// not handling deleted files or unknown
							continue;
						}
	 					ISourceLocation fileLoc = VF.sourceLocation(localStorage.getAbsolutePath().concat(item.getPath()));
						IValue fileM3 = null;
						try {
							fileM3 = eval.call(new NullRascalMonitor(), module, "createFileM3", fileLoc);
						} catch (Exception e) {
							System.err.println("Model could not be created for file " + fileLoc);
							System.err.println("Continuing with other files...");
							continue;
						}
						IValue argument = ((IBool)eval.call(new NullRascalMonitor(), module, "isValid", fileM3)).getValue() ? fileM3 : fileLoc;
						for (RascalMetrics metric : rascalMetrics) {
							metric.measure(eval, argument, commit.getRevision(), vcsRepository.getUrl() + item.getPath(), projectDelta.getDate());
						}
					}
					// syncing needs to be done for all metrics after each commit
					syncAllDBs();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void syncAllDBs() {
		for (RascalMetrics m : rascalMetrics)
			((RascalMetrics)m).sync();
	}
	
}
