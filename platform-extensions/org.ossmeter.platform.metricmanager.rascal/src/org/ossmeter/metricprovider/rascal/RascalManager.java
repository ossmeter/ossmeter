package org.ossmeter.metricprovider.rascal;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IMapWriter;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;
import org.ossmeter.platform.IMetricProvider;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.NullRascalMonitor;
import org.rascalmpl.interpreter.env.GlobalEnvironment;
import org.rascalmpl.interpreter.env.ModuleEnvironment;
import org.rascalmpl.interpreter.env.Pair;
import org.rascalmpl.interpreter.load.StandardLibraryContributor;
import org.rascalmpl.interpreter.result.AbstractFunction;
import org.rascalmpl.interpreter.result.ICallableValue;
import org.rascalmpl.values.ValueFactoryFactory;

public class RascalManager {
	private final IValueFactory VF = ValueFactoryFactory.getValueFactory();
	private final Evaluator eval = createEvaluator();
	
	private final Set<Bundle> metricBundles = new HashSet<>();
	
	// thread safe way of keeping a static instance
	private static class InstanceKeeper {
	  public static final RascalManager sInstance = new RascalManager();
	}
	
	public static final String MODULE = "org::ossmeter::metricprovider::Manager";
	
	/**
   * This code is taken from http://wiki.eclipse.org/BundleProxyClassLoader_recipe
   */
  private static class BundleClassLoader extends ClassLoader {
    private Bundle bundle;
    private ClassLoader parent;
      
    public BundleClassLoader(Bundle bundle) {
      this.bundle = bundle;
    }
    
    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
      return bundle.getResources(name);
    }
    
    @Override
    public URL findResource(String name) {
        return bundle.getResource(name);
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        return bundle.loadClass(name);
    }
    
    @Override
    public URL getResource(String name) {
      return (parent == null) ? findResource(name) : super.getResource(name);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
      Class<?> clazz = (parent == null) ? findClass(name) : super.loadClass(name, false);
      if (resolve)
        super.resolveClass(clazz);
      
      return clazz;
    }
  }
  
	
	public void configureRascalMetricProviders(Set<Bundle> providers) {
	  assert eval != null;
	  
		for (Bundle provider : providers) {
		  configureRascalMetricProvider(eval, provider);
		}
	}
	
  private Evaluator createEvaluator() {
	GlobalEnvironment heap = new GlobalEnvironment();
	ModuleEnvironment moduleRoot = new ModuleEnvironment("******ossmeter******", heap);
	  
	Evaluator eval = new Evaluator(VF, new PrintWriter(System.err), new PrintWriter(System.out), moduleRoot, heap);
	eval.getResolverRegistry().registerInput(new BundleURIResolver(eval.getResolverRegistry()));
    eval.addRascalSearchPathContributor(StandardLibraryContributor.getInstance());
    try {
    	Bundle currentBundle = Rasctivator.getContext().getBundle();
    	List<String> roots = new RascalBundleManifest().getSourceRoots(currentBundle);
		
    	for (String root : roots) {
          eval.addRascalSearchPath(currentBundle.getResource(root).toURI());
        }
	} catch (URISyntaxException e) {
		Rasctivator.logException("failed to add the current bundle path to rascal search path", e);
	}
    return eval;
  }
  
  public Evaluator getEvaluator() {
	  return eval;
  }

  private void configureRascalMetricProvider(Evaluator evaluator, Bundle bundle) {
    try {
      List<String> roots = new RascalBundleManifest().getSourceRoots(bundle);

      for (String root : roots) {
        evaluator.addRascalSearchPath(bundle.getResource(root).toURI());
      }

      evaluator.addClassLoader(new BundleClassLoader(bundle));
      configureClassPath(bundle, evaluator);
      
      metricBundles.add(bundle);
    }
    catch (Throwable e) {
       Rasctivator.logException("failed to configure metrics bundle " + bundle, e);
    }
  }
	
	 private void configureClassPath(Bundle bundle, Evaluator evaluator) {
	    List<URL> classPath = new LinkedList<URL>();
	    List<String> compilerClassPath = new LinkedList<String>();
	    collectClassPathForBundle(bundle, classPath, compilerClassPath);
	    configureClassPath(evaluator, classPath, compilerClassPath);
	  }
	  
	  private void configureClassPath(Evaluator parser, List<URL> classPath, List<String> compilerClassPath) {
	    // this registers the run-time path:
	    URL[] urls = new URL[classPath.size()];
	    classPath.toArray(urls);
	    URLClassLoader classPathLoader = new URLClassLoader(urls, RascalManager.class.getClassLoader());
	    parser.addClassLoader(classPathLoader);
	    
	    // this registers the compile-time path:
	    String ccp = "";
	    for (String elem : compilerClassPath) {
	      ccp += File.pathSeparatorChar + elem;
	    }
	    
	    parser.getConfiguration().setRascalJavaClassPathProperty(ccp.substring(1));
	  }
	  
	  private void collectClassPathForBundle(Bundle bundle, List<URL> classPath, List<String> compilerClassPath) {
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
	        collectClassPathForBundle(dep.getProviderWiring().getBundle(), classPath, compilerClassPath);
	      }
	    } 
	    catch (IOException e) {
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

  public void registerRascalMetricProvider(Bundle bundle) {
    configureRascalMetricProvider(eval, bundle);
  }

  private void addMetricProviders(Bundle bundle, List<IMetricProvider> providers) {
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
          
          ICallableValue overloadedFunc = (ICallableValue) module.getVariable(f.getName()).getValue();
          // TODO: friendly feedback in case of missing tags
          providers.add(new RascalMetricProvider(metricId, funcName, friendlyName, description, overloadedFunc));
        }
      }
    }
  }

  public List<IMetricProvider> getMetricProviders() {
    List<IMetricProvider> providers = new LinkedList<>();
    
    for (Bundle bundle : metricBundles) {
      addMetricProviders(bundle, providers);
    }
    
    return providers;
  }
  
  public void initialize(IValue... parameters) {
	eval.call(MODULE, parameters);
  }

  public IMap makeMap(Map<String, File> foldersMap) {
	IMapWriter result = VF.mapWriter();
	
	for (Entry<String, File> entry : foldersMap.entrySet()) {
		result.put(VF.sourceLocation(entry.getKey()), VF.sourceLocation(entry.getValue().getAbsolutePath()));
	}
	
	return result.done();
  }
}
