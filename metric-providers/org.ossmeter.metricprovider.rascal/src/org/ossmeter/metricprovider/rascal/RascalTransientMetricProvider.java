package org.ossmeter.metricprovider.rascal;

import java.io.File;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IReal;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.ossmeter.metricprovider.rascal.trans.model.BooleanMeasurement;
import org.ossmeter.metricprovider.rascal.trans.model.IntegerMeasurement;
import org.ossmeter.metricprovider.rascal.trans.model.Measurement;
import org.ossmeter.metricprovider.rascal.trans.model.RascalMetrics;
import org.ossmeter.metricprovider.rascal.trans.model.RealMeasurement;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.vcs.VcsProjectDelta;
import org.ossmeter.platform.delta.vcs.VcsRepositoryDelta;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.VcsRepository;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.NullRascalMonitor;
import org.rascalmpl.interpreter.env.GlobalEnvironment;
import org.rascalmpl.interpreter.env.ModuleEnvironment;
import org.rascalmpl.interpreter.load.StandardLibraryContributor;
import org.rascalmpl.interpreter.load.URIContributor;
import org.rascalmpl.uri.ClassResourceInputOutput;
import org.rascalmpl.uri.IURIInputStreamResolver;
import org.rascalmpl.uri.URIUtil;
import org.rascalmpl.values.ValueFactoryFactory;
import org.eclipse.imp.pdb.facts.type.Type;

import com.mongodb.DB;

public class RascalTransientMetricProvider implements ITransientMetricProvider<RascalMetrics> {
	private final static GlobalEnvironment heap = new GlobalEnvironment();
	private final static ModuleEnvironment root = new ModuleEnvironment("******metrics******", heap);
	private final static IValueFactory VF = ValueFactoryFactory.getValueFactory();
	private final static OSSMeterURIResolver ossmStore = new OSSMeterURIResolver();
	private final static Evaluator eval = initEvaluator();

	private static Evaluator initEvaluator() {
		Evaluator eval = new Evaluator(VF, new PrintWriter(System.err), new PrintWriter(System.out), root, heap);
		IURIInputStreamResolver metrics = new ClassResourceInputOutput(eval.getResolverRegistry(), "metrics", RascalTransientMetricProvider.class, "");
		eval.getResolverRegistry().registerInput(metrics);
		eval.getResolverRegistry().registerInputOutput(ossmStore);
		eval.addRascalSearchPathContributor(new URIContributor(URIUtil.rootScheme("metrics")));
		eval.addRascalSearchPathContributor(StandardLibraryContributor.getInstance());
		eval.addRascalSearchPath(URIUtil.assumeCorrect("file:///Users/shahi/Documents/CWI/OSSMeter/Software/Software/source/metric-providers/org.ossmeter.metricprovider.rascal.examples/modules/"));
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
		File localStorage = new File(project.getStorage().getPath());
		ossmStore.addProject(project.getName(), localStorage);
		try {
			VcsProjectDelta vcsDelta = projectDelta.getVcsDelta();

			for (VcsRepositoryDelta vcsRepositoryDelta : vcsDelta.getRepoDeltas()) {
				VcsRepository vcsRepository = vcsRepositoryDelta.getRepository();
				//ISourceLocation rascalLoc = VF.sourceLocation(URIUtil.create("ossm", project.getName(), "/"));
				ISourceLocation rascalLoc = VF.sourceLocation(localStorage.getAbsolutePath());
				eval.call(new NullRascalMonitor(), module, "checkOutRepository", VF.string(vcsRepository.getUrl()), rascalLoc);
				eval.call(new NullRascalMonitor(), module, "createModel", rascalLoc);
				IValue result = eval.call(new NullRascalMonitor(), module, function);
				
				Type type = result.getType();
				List<Measurement> test =  new ArrayList<>();

				if (type.isInteger()) {
					test.add(makeIntegerMeasurement(result));
				} else if (type.isReal()) {
					test.add(makeRealMeasurement(result));
				} else if (type.isBool()) {
					test.add(makeBooleanMeasurement(result));
				} else if (type.isMap()){
					if (type.getKeyType().isString()) {
						// do not do anything at the moment
					}
					
				} else {
					throw new UnsupportedOperationException();
				}
				
				for (Iterator<Measurement> it = test.iterator(); it.hasNext(); ) {
					Measurement metricValue = it.next();
					metricValue.setMetric(metric.toASCIIString());
					metricValue.setUri(rascalLoc.getURI().toASCIIString());
					db.getMeasurements().add(metricValue);
				}
				
				db.sync();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Measurement makeIntegerMeasurement(IValue result) {
		Measurement test = new IntegerMeasurement();
		((IntegerMeasurement) test).setValue(((IInteger) result).longValue());
		return test;
	}
	
	private Measurement makeRealMeasurement(IValue result) {
		Measurement test = new RealMeasurement();
		((RealMeasurement) test).setValue(((IReal) result).floatValue());
		return test;
	}
	
	private Measurement makeBooleanMeasurement(IValue result) {
		Measurement test = new BooleanMeasurement();
		((BooleanMeasurement) test).setValue(((IBool) result).getValue());
		return test;
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

	@Override
	public String getShortIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFriendlyName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSummaryInformation() {
		// TODO Auto-generated method stub
		return null;
	}
}
