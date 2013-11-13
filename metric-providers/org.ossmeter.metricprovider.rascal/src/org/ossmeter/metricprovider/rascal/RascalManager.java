package org.ossmeter.metricprovider.rascal;

import java.io.PrintWriter;
import java.util.HashMap;

import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
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

public class RascalManager {
	private static RascalManager _instance;
	
	private final static GlobalEnvironment heap = new GlobalEnvironment();
	private final static ModuleEnvironment root = new ModuleEnvironment("******metrics******", heap);
	private final static IValueFactory VF = ValueFactoryFactory.getValueFactory();
//	private final static OSSMeterURIResolver ossmStore = new OSSMeterURIResolver();
	private final static Evaluator eval = initEvaluator();
	
	private static HashMap<String, ProjectRascalManager> managedProjects = new HashMap<>();
	
	private final String module = "Manager";
	
	public class ProjectRascalManager {
		public HashMap<String, HashMap<String, IValue>> fileModelsPerCommit = new HashMap<>();
		
		public void checkOutRevision(String revision, String repositoryURL, String localPath) {
			if (!fileModelsPerCommit.containsKey(revision)) {
				eval.call(new NullRascalMonitor(), module, "checkOutRepository", VF.string(repositoryURL), VF.integer(revision), VF.sourceLocation(localPath));
				fileModelsPerCommit.put(revision, new HashMap<String, IValue>());
			}
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
	
	private static Evaluator initEvaluator() {
		Evaluator eval = new Evaluator(VF, new PrintWriter(System.err), new PrintWriter(System.out), root, heap);
		IURIInputStreamResolver metrics = new ClassResourceInput(eval.getResolverRegistry(), "metrics", RascalManager.class, "");
		eval.getResolverRegistry().registerInput(metrics);
		URIContributor moduleContributor = new URIContributor(URIUtil.rootScheme("metrics"));
		eval.addRascalSearchPathContributor(moduleContributor);
		eval.addRascalSearchPathContributor(StandardLibraryContributor.getInstance());
		eval.addRascalSearchPath(URIUtil.assumeCorrect("file:///Users/shahi/Documents/CWI/OSSMeter/Software/OSSMETER/source/metric-providers/org.ossmeter.metricprovider.rascal/modules"));
		return eval;
	}
	
	private RascalManager() {
		eval.doImport(new NullRascalMonitor(), module);
	}

	public static ProjectRascalManager getInstance(String project) {
		if (_instance == null)
			_instance = new RascalManager();
		if (!managedProjects.containsKey(project)) {
			managedProjects.put(project, _instance.new ProjectRascalManager());
		}
		return managedProjects.get(project);
	}
	
	public static void clearManagerData() {
		managedProjects.clear();
	}
	
	public static void importModule(String module) {
		eval.doImport(new NullRascalMonitor(), module);
	}
	
	public static void clearProjectData() {
		for (ProjectRascalManager m : managedProjects.values()) {
			m.clearManagerDataForProject();
		}
	}
}
