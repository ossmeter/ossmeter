package org.ossmeter.metricprovider.rascal;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;
import org.ossmeter.platform.Date;
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
	private final static GlobalEnvironment heap = new GlobalEnvironment();
	private final static ModuleEnvironment root = new ModuleEnvironment("******ossmeter******", heap);
	private final static IValueFactory VF = ValueFactoryFactory.getValueFactory();
	private final static Evaluator eval = createEvaluator();
	
	private final Set<Bundle> metricBundles = new HashSet<>();
	private final Map<String, ProjectRascalManager> managedProjects = new HashMap<>();
	
	// thread safe way of keeping a static instance
	private static class InstanceKeeper {
	  public static final RascalManager sInstance = new RascalManager();
	}
	
	private final String module = "Manager";
	
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
  
	public class ProjectRascalManager {
		public HashMap<String, HashMap<String, IValue>> fileModelsPerCommit = new HashMap<>();
		private Date lastChecked;
		
		public void checkOutRevision(Date currentDate, String revision, String repositoryURL, String localPath) {
			if (lastChecked != null) {
				if (currentDate.compareTo(lastChecked) > 0) {
					clearProjectData();
				}
			}
			if (!fileModelsPerCommit.containsKey(revision)) {
				eval.call(new NullRascalMonitor(), module, "checkOutRepository", VF.string(repositoryURL), VF.integer(revision), VF.sourceLocation(localPath));
				fileModelsPerCommit.put(revision, new HashMap<String, IValue>());
			}
			lastChecked = currentDate;
		}
		
		public ISourceLocation makeLocation(String fileURL) {
			return VF.sourceLocation(fileURL);
		}
		
		public IValue getModel(String revision, String fileURL, String localURL) {
			HashMap<String, IValue> fileModels;
			IValue result;
			if (fileModelsPerCommit.containsKey(revision)) {
				fileModels = fileModelsPerCommit.get(revision);
				if (fileModels.containsKey(fileURL)) {
					return fileModels.get(fileURL);
				}
			} else {
				fileModels = new HashMap<>();
			}
			
			result = eval.call(new NullRascalMonitor(), module, "createFileM3", VF.sourceLocation(localURL));
			fileModels.put(fileURL, result);
			fileModelsPerCommit.put(revision, fileModels);
			return result;
		}
		
		public boolean isValidModel(IValue fileM3) {
			return ((IBool)eval.call(new NullRascalMonitor(), module, "isValid", fileM3)).getValue();
		}
		
		public void clearManagerDataForProject() {
			fileModelsPerCommit.clear();
		}

		public IValue callRascal(String module, String function, IValue... parameters) {
			return eval.call(new NullRascalMonitor(), module, function, parameters);
		}
	}
	
	public void configureRascalMetricProviders(Set<Bundle> providers) {
	  assert eval != null;
	  
		for (Bundle provider : providers) {
		  configureRascalMetricProvider(eval, provider);
		}
	}
	
	private static Evaluator createEvaluator() {
	  Evaluator eval = new Evaluator(VF, new PrintWriter(System.err), new PrintWriter(System.out), root, heap);
	  eval.getResolverRegistry().registerInput(new BundleURIResolver(eval.getResolverRegistry()));
    eval.addRascalSearchPathContributor(StandardLibraryContributor.getInstance());
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
    catch (URISyntaxException e) {
       Rasctivator.logException("failed to configure Rascal bundle " + bundle, e);
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
	
	public ProjectRascalManager getInstance(String project) {
		RascalManager instance = getInstance();
		
    if (!instance.managedProjects.containsKey(project)) {
			instance.managedProjects.put(project, instance.new ProjectRascalManager());
		}
		
		return instance.managedProjects.get(project);
	}
	
	public void clearManagerData() {
		getInstance().managedProjects.clear();
	}
	
	public void importModule(String module) {
		eval.doImport(new NullRascalMonitor(), module);
	}
	
	public void clearProjectData() {
		for (ProjectRascalManager m : managedProjects.values()) {
			m.clearManagerDataForProject();
		}
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

  public void addMetricProviders(Bundle bundle, List<IMetricProvider> providers) {
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
}
