package org.ossmeter.metricprovider.rascal;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.exceptions.FactTypeUseException;
import org.eclipse.imp.pdb.facts.io.StandardTextReader;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeStore;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.logging.OssmeterLogger;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.NullRascalMonitor;
import org.rascalmpl.interpreter.env.GlobalEnvironment;
import org.rascalmpl.interpreter.env.ModuleEnvironment;
import org.rascalmpl.interpreter.env.Pair;
import org.rascalmpl.interpreter.load.StandardLibraryContributor;
import org.rascalmpl.interpreter.result.AbstractFunction;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.values.ValueFactoryFactory;

public class RascalManager {
	private static final String EXTRACTOR_TAG_AST = "ASTExtractor";
	private static final String EXTRACTOR_TAG_M3 = "M3Extractor";
	private final static IValueFactory VF = ValueFactoryFactory.getValueFactory();
	private final Evaluator eval = createEvaluator();

	private final Set<Bundle> registeredBundles = new HashSet<>();

	
	// thread safe way of keeping a static instance
	private static class InstanceKeeper {
		public static final RascalManager sInstance = new RascalManager();
	}

	public class Extractor {
		public ModuleEnvironment module;
		public AbstractFunction function;
		public String language;
		
		public Extractor(AbstractFunction fun, ModuleEnvironment env, String lang) throws Exception {
			if (lang == null || lang.length() == 0) {
				throw new Exception("Invalid language");
			}
			
			function = fun;
			module = env;
			language = lang;
			
			if (!fun.hasTag("memo")) {
				// TODO enable once we can distinguish between tags and annotations
				//throw new IllegalArgumentException("Rascal M3 extractor functions should have an @memo tag.");
			}
		}
		
		public IValue call(ISourceLocation projectLocation, IConstructor delta, IMap workingCopyFolders, IMap scratchFolders) {
			Result<IValue> result = function.call(new NullRascalMonitor(),
					new Type[]{projectLocation.getType(), delta.getType(), workingCopyFolders.getType(), scratchFolders.getType()},
					new IValue[]{projectLocation, delta, workingCopyFolders, scratchFolders}, null);
			// TODO error handling
			return result.getValue();
		}
	}
	
	private final Set<Extractor> m3Extractors = new HashSet<>();
	private final Set<Extractor> astExtractors = new HashSet<>();
	
	public static final String MODULE = "org::ossmeter::metricprovider::Manager";

	public void configureRascalMetricProviders(Set<Bundle> providers) {
		assert eval != null;

		for (Bundle provider : providers) {
			configureRascalPath(eval, provider);
		}
	}

	private Evaluator createEvaluator() {
		GlobalEnvironment heap = new GlobalEnvironment();
		ModuleEnvironment moduleRoot = new ModuleEnvironment("******ossmeter******", heap);

		OssmeterLogger log = (OssmeterLogger) OssmeterLogger.getLogger("Rascal console");
		log.addConsoleAppender(OssmeterLogger.DEFAULT_PATTERN);
		LoggerWriter stderr = new LoggerWriter(true, log);
		LoggerWriter stdout = new LoggerWriter(false, log);
		
		Evaluator eval = new Evaluator(VF, stderr, stdout, moduleRoot, heap);
		
		eval.setMonitor(new RascalMonitor(log));
		eval.getResolverRegistry().registerInput(new BundleURIResolver(eval.getResolverRegistry()));
		eval.addRascalSearchPathContributor(StandardLibraryContributor.getInstance());
		try {
			Bundle currentBundle = Rasctivator.getContext().getBundle();
			List<String> roots = new RascalBundleManifest()
					.getSourceRoots(currentBundle);

			for (String root : roots) {
				eval.addRascalSearchPath(currentBundle.getResource(root)
						.toURI());
			}
		} catch (URISyntaxException e) {
			Rasctivator.logException("failed to add the current bundle path to rascal search path", e);
		}
		return eval;
	}

	public Evaluator getEvaluator() {
		return eval;
	}

	private void configureRascalPath(Evaluator evaluator, Bundle bundle) {
		if (registeredBundles.contains(bundle)) {
			return;
		}
		
		registeredBundles.add(bundle);
		
		try {
			RascalBundleManifest mf = new RascalBundleManifest();
			
			List<String> dependencies = mf.getRequiredBundles(bundle);
			if (dependencies != null) {
				for (String bundleName : dependencies) {
					Bundle dep = Platform.getBundle(bundleName);
					if (dep != null) {
						configureRascalPath(evaluator, dep);
					} else {
						throw new BundleException("Bundle " + bundleName + " not found.");
					}
				}
			}
			
			for (String root : mf.getSourceRoots(bundle)) {
				evaluator.addRascalSearchPath(bundle.getResource(root).toURI());
			}

			evaluator.addClassLoader(new BundleClassLoader(bundle));
			configureClassPath(bundle, evaluator);
		} catch (Throwable e) {
			Rasctivator.logException("failed to configure metrics bundle "
					+ bundle, e);
		}
	}

	private void configureClassPath(Bundle bundle, Evaluator evaluator) {
		List<URL> classPath = new LinkedList<URL>();
		List<String> compilerClassPath = new LinkedList<String>();
		collectClassPathForBundle(bundle, classPath, compilerClassPath);
		configureClassPath(evaluator, classPath, compilerClassPath);
	}

	private void configureClassPath(Evaluator parser, List<URL> classPath,
			List<String> compilerClassPath) {
		// this registers the run-time path:
		URL[] urls = new URL[classPath.size()];
		classPath.toArray(urls);
		URLClassLoader classPathLoader = new URLClassLoader(urls,
				RascalManager.class.getClassLoader());
		parser.addClassLoader(classPathLoader);

		// this registers the compile-time path:
		String ccp = "";
		for (String elem : compilerClassPath) {
			ccp += File.pathSeparatorChar + elem;
		}

		parser.getConfiguration().setRascalJavaClassPathProperty(
				ccp.substring(1));
	}

	private void collectClassPathForBundle(Bundle bundle, List<URL> classPath,
			List<String> compilerClassPath) {
		try {
			File file = FileLocator.getBundleFile(bundle);
			URL url = file.toURI().toURL();

			if (classPath.contains(url)) {
				return; // kill infinite loop
			}

			classPath.add(0, url);
			compilerClassPath.add(0, file.getAbsolutePath());

			BundleWiring wiring = bundle.adapt(BundleWiring.class);

			for (BundleWire dep : wiring.getRequiredWires(null)) {
				collectClassPathForBundle(dep.getProviderWiring().getBundle(),
						classPath, compilerClassPath);
			}
		} catch (IOException e) {
			Rasctivator.logException("error while tracing dependencies", e);
		}
	}

	public static RascalManager getInstance() {
		return InstanceKeeper.sInstance;
	}

	public void importModule(String module) {
		eval.doImport(new NullRascalMonitor(), module);
	}

	public static String makeRelative(String base, String extension) {
		StringBuilder result = new StringBuilder();
		List<String> baseSegments = Arrays.asList(base.split("/"));
		String[] extensionSegments = extension.split("/");
		for (String ext : extensionSegments) {
			if (!baseSegments.contains(ext)) {
				result.append(extension.substring(extension.indexOf(ext)));
				break;
			}
		}
		return result.toString();
	}

	private void addMetricProviders(Bundle bundle, List<IMetricProvider> providers, Set<String> extractedLanguages) {
		RascalBundleManifest mf = new RascalBundleManifest();
		String moduleName = mf.getMainModule(bundle);

		if (!eval.getHeap().existsModule(moduleName)) {
			importModule(moduleName);
		}

		ModuleEnvironment module = eval.getHeap().getModule(moduleName);

		for (Pair<String, List<AbstractFunction>> func : module.getFunctions()) {
			final String funcName = func.getFirst();

			for (final AbstractFunction f : func.getSecond()) {
				// TODO: add some type checking on the arguments
				if (f.hasTag("metric")) {
					String metricName = f.getTag("metric");
					String metricId = bundle.getSymbolicName() + "." + metricName;
					String friendlyName = f.getTag("friendlyName");
					String description = f.getTag("doc");
					String language = f.getTag("appliesTo");
					Map<String,String> uses = getUses(f);
					
					if (!extractedLanguages.contains(language)) {
						eval.getStdOut().println("Warning: metric " + f.toString() + " not loaded, no extractors available for language " + language);
						continue;
					}

					if (f.getReturnType().toString().equals("Factoid")) {
						providers.add(new RascalFactoidProvider(bundle.getSymbolicName(), metricId, funcName, friendlyName, description, f, uses));
					}
					else { 
						RascalMetricProvider m = new RascalMetricProvider(bundle.getSymbolicName(), metricId, funcName, friendlyName, description, f, uses); 
					
						providers.add(m);

						if (f.hasTag("historic")) {
							providers.add(new RascalMetricHistoryWrapper(m));
						}
					}
				}
			}
		}
	}
	
	private Map<String,String> getUses(AbstractFunction f) {
		try {
			StandardTextReader reader = new StandardTextReader();
			Map<String,String> map = new HashMap<>();
			
			if (f.hasTag("uses")) {
				IMap m = (IMap) reader.read(VF, new StringReader(f.getTag("uses")));

				for (IValue key : m) {
					map.put(((IString) key).getValue(), ((IString) m.get(key)).getValue());
				}
				
				return map;
			}
		} catch (FactTypeUseException | IOException e) {
			Rasctivator.logException("could not parse uses tag", e);
		}
		
		return Collections.emptyMap();
	}

	public Set<Extractor> getM3Extractors() {
		return m3Extractors;
	}
	
	public Set<Extractor> getASTExtractors() {
		return astExtractors;
	}
	
	public TypeStore getExtractedTypes() {
		TypeStore store = new TypeStore();
		
		for (Extractor e : m3Extractors) {
			store.extendStore(e.function.getEnv().getStore());
		}
		for (Extractor e : astExtractors) {
			store.extendStore(e.function.getEnv().getStore());
		}
		
		return store; 
	}

	public synchronized List<IMetricProvider> getMetricProviders() {
		List<IMetricProvider> providers = new LinkedList<>();
		
		Set<Bundle> extractorBundles = new HashSet<>();
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint("ossmeter.rascal.extractor");
		if (extensionPoint != null) {
			for (IExtension element : extensionPoint.getExtensions()) {
				String name = element.getContributor().getName();
				Bundle bundle = Platform.getBundle(name);
				configureRascalPath(eval, bundle);
				extractorBundles.add(bundle);
			}
		}
		
		for (Bundle bundle : extractorBundles) {
			addExtractors(bundle);
		}
		
		Set<String> extractedLanguages = new HashSet<>();
		for (Extractor e : m3Extractors) {
			extractedLanguages.add(e.language);
		}
		for (Extractor e : astExtractors) {
			extractedLanguages.add(e.language);
		}

		Set<Bundle> metricBundles = new HashSet<>();
		extensionPoint = Platform.getExtensionRegistry().getExtensionPoint("ossmeter.rascal.metricprovider");
		
		if (extensionPoint != null) {
			for (IExtension element : extensionPoint.getExtensions()) {
				String name = element.getContributor().getName();
				Bundle bundle = Platform.getBundle(name);
				configureRascalPath(eval, bundle);
				metricBundles.add(bundle);
			}
		}

		for (Bundle bundle : metricBundles) {
			addMetricProviders(bundle, providers, extractedLanguages);
		}

		return providers;
	}

	public void initialize(IValue... parameters) {
		// eval.call("initialize", parameters);
		eval.call("initialize", MODULE, null, parameters);
	}

	public void addExtractors(Bundle bundle) {
		configureRascalPath(eval, bundle);
		RascalBundleManifest mf = new RascalBundleManifest();
		String moduleName = mf.getMainModule(bundle);

		if (!eval.getHeap().existsModule(moduleName)) {
			importModule(moduleName);
		}

		ModuleEnvironment module = eval.getHeap().getModule(moduleName);

		for (Pair<String, List<AbstractFunction>> func : module.getFunctions()) {
			for (final AbstractFunction f : func.getSecond()) {
				try {
					if (f.hasTag(EXTRACTOR_TAG_M3)) {
						m3Extractors.add(new Extractor(f, module, f.getTag(EXTRACTOR_TAG_M3)));
					} else if (f.hasTag(EXTRACTOR_TAG_AST)) {
						astExtractors.add(new Extractor(f, module, f.getTag(EXTRACTOR_TAG_AST)));								
					}
				} catch (Exception e) {
					eval.getStdErr().println("Error while loading extractor " + f.toString());
					e.printStackTrace(eval.getStdErr());
				}
			}
		}
	}
}
