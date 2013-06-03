package org.ossmeter.metricprovider.rascal;

import java.io.PrintWriter;
import java.net.URI;
import java.util.Collections;
import java.util.List;

import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IReal;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.ossmeter.metricprovider.rascal.trans.model.BooleanMeasurement;
import org.ossmeter.metricprovider.rascal.trans.model.IntegerMeasurement;
import org.ossmeter.metricprovider.rascal.trans.model.Measurement;
import org.ossmeter.metricprovider.rascal.trans.model.RascalMetrics;
import org.ossmeter.metricprovider.rascal.trans.model.RealMeasurement;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.vcs.VcsCommitItem;
import org.ossmeter.platform.delta.vcs.VcsProjectDelta;
import org.ossmeter.platform.delta.vcs.VcsRepositoryDelta;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.VcsRepository;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.NullRascalMonitor;
import org.rascalmpl.interpreter.env.GlobalEnvironment;
import org.rascalmpl.interpreter.env.ModuleEnvironment;
import org.rascalmpl.interpreter.load.URIContributor;
import org.rascalmpl.uri.ClassResourceInputOutput;
import org.rascalmpl.uri.IURIInputStreamResolver;
import org.rascalmpl.uri.URIUtil;
import org.rascalmpl.values.ValueFactoryFactory;

import com.mongodb.DB;

public class RascalTransientMetricProvider implements ITransientMetricProvider<RascalMetrics> {
	private final static GlobalEnvironment heap = new GlobalEnvironment();
	private final static ModuleEnvironment root = new ModuleEnvironment("***metrics***", heap);
	private final static IValueFactory VF = ValueFactoryFactory.getValueFactory();
	private final static Evaluator eval = initEvaluator();

	private static Evaluator initEvaluator() {
		Evaluator eval = new Evaluator(VF, new PrintWriter(System.err), new PrintWriter(System.out), root, heap);
		IURIInputStreamResolver metrics = new ClassResourceInputOutput(eval.getResolverRegistry(), "metrics", RascalTransientMetricProvider.class, "");
		eval.getResolverRegistry().registerInput(metrics);
		eval.addRascalSearchPathContributor(new URIContributor(URIUtil.rootScheme("metrics")));
		return eval;
	}

	private final String function;
	private final String module;
	private final URI metric;
	protected MetricProviderContext context;

	public RascalTransientMetricProvider(URI metric) {
		this.metric = metric;
		String module = metric.getAuthority();
		this.module = module;
		eval.doImport(new NullRascalMonitor(), module);
		this.function = metric.getPath().substring(1); // drop the slash
	}

	@Override
	public boolean appliesTo(Project project) {
		return project.getVcsRepositories().size() > 0;
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public RascalMetrics adapt(DB db) {
		return new RascalMetrics(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta,
			RascalMetrics db) {
		try {
			VcsProjectDelta vcsDelta = projectDelta.getVcsDelta();

			for (VcsRepositoryDelta vcsRepositoryDelta : vcsDelta.getRepoDeltas()) {
				VcsRepository vcsRepository = vcsRepositoryDelta.getRepository();

				List<VcsCommitItem> compactedCommitItems = vcsRepositoryDelta.getCompactedCommitItems();
				if (compactedCommitItems.size() == 0) {
					continue;
				}

				for (VcsCommitItem item : compactedCommitItems) {
					String contents = context.getPlatformVcsManager().getContents(item);
					URI uri = URIUtil.changePath(URI.create(vcsRepository.getUrl()), item.getPath());
					ISourceLocation loc = VF.sourceLocation(uri);
					IValue result = eval.call(new NullRascalMonitor(), module, function, loc, VF.string(contents));
					Type type = result.getType();
					Measurement test;

					if (type.isIntegerType()) {
						test = new IntegerMeasurement();
						((IntegerMeasurement) test).setValue(((IInteger) result).longValue());
					} else if (type.isRealType()) {
						test = new RealMeasurement();
						((RealMeasurement) test).setValue(((IReal) result).floatValue());
					} else if (type.isBoolType()) {
						test = new BooleanMeasurement();
						((BooleanMeasurement) test).setValue(((IBool) result).getValue());
					} else {
						throw new UnsupportedOperationException();
					}

					test.setMetric(metric.toASCIIString());
					test.setUri(uri.toASCIIString());
					db.getMeasurements().add(test);
				}

				db.sync();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getIdentifier() {
		return metric.toASCIIString();
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {

	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Collections.emptyList();
	}
}
