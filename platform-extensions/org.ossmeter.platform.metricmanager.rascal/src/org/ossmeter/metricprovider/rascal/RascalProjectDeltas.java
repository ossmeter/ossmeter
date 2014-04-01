package org.ossmeter.metricprovider.rascal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IDateTime;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.eclipse.imp.pdb.facts.type.TypeStore;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.vcs.VcsChangeType;
import org.ossmeter.platform.delta.vcs.VcsCommit;
import org.ossmeter.platform.delta.vcs.VcsCommitItem;
import org.ossmeter.platform.delta.vcs.VcsRepositoryDelta;
import org.ossmeter.platform.vcs.workingcopy.manager.Churn;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.VcsRepository;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.NullRascalMonitor;
import org.rascalmpl.values.ValueFactoryFactory;

public class RascalProjectDeltas {
  private final TypeStore store = new TypeStore();
  private final IValueFactory values = ValueFactoryFactory.getValueFactory();
  private final TypeFactory TF = TypeFactory.getInstance();
  public static final String MODULE = "org::ossmeter::metricprovider::ProjectDelta";
  private Map<VcsCommit, List<Churn>> churns;
  
  public RascalProjectDeltas(Evaluator eval) {
	if (!eval.getHeap().existsModule(MODULE)) {
		eval.doImport(new NullRascalMonitor(), MODULE);
	}
	store.extendStore(eval.getHeap().getModule(MODULE).getStore());
  }

  public IConstructor convert(final ProjectDelta delta, Map<VcsCommit, List<Churn>> churnPerCommit) {
	List<IValue> children = new ArrayList<>();
	this.churns = churnPerCommit;
	
	children.add(convert(delta.getDate()));
	children.add(convert(delta.getProject()));
	children.add(convert(delta.getVcsDelta().getRepoDeltas()));
	
	return createConstructor("ProjectDelta", "projectDelta", children.toArray(new IValue[0]));
  }

  private IConstructor createConstructor(String adtName, String constructorName, IValue... children) {
	Type cons = store.lookupConstructor(store.lookupAbstractDataType(adtName), constructorName, TF.tupleType(children));
	
	return values.constructor(cons, children);
  }
  
  private IDateTime convert(Date date) {
	return values.datetime(date.toJavaDate().getTime());
  }
  
  private IConstructor convert(Project project) {
	List<IValue> children = new ArrayList<>();
	
	children.add(convert(project.getName()));
	children.add(convert(project.getVcsRepositories()));
	
	return createConstructor("Project", "project", children.toArray(new IValue[0]));
  }

  private IList convert(List<?> repoDeltas) {
	IListWriter parameter = values.listWriter();

	for (Iterator<?> it = repoDeltas.iterator(); it.hasNext(); ) {
	  Object nextItem = it.next();
	  if (nextItem instanceof VcsRepository) {
		parameter.append(convert((VcsRepository) nextItem));
	  } else if (nextItem instanceof VcsRepositoryDelta) {
		parameter.append(convert((VcsRepositoryDelta) nextItem));
	  } else if (nextItem instanceof VcsCommit) {
		parameter.append(convert((VcsCommit) nextItem));
	  } else if (nextItem instanceof VcsCommitItem) {
		parameter.append(convert((VcsCommitItem) nextItem));
	  }
	}
	
	return parameter.done();
  }
  
  private IConstructor convert(VcsRepository repo) {
	return values.constructor(store.lookupConstructor(store.lookupAbstractDataType("VcsRepository"), "vcsRepository", TF.tupleType(TF.stringType())), convert(repo.getUrl()));
  }
  
  private IConstructor convert(VcsRepositoryDelta vcsRepoDelta) {
	List<IValue> children = new ArrayList<>();
		
	children.add(convert(vcsRepoDelta.getRepository()));
	children.add(convert(vcsRepoDelta.getCommits()));
	children.add(convert(vcsRepoDelta.getLatestRevision()));
		
	return createConstructor("VcsRepositoryDelta", "vcsRepositoryDelta", children.toArray(new IValue[0]));
  }
  
  private IConstructor convert(VcsCommit commit) {
	List<IValue> children = new ArrayList<>();
		
	children.add(values.datetime(commit.getJavaDate().getTime()));
	children.add(convert(commit.getAuthor()));
	children.add(convert(commit.getMessage()));
	children.add(convert(commit.getItems()));
	children.add(convert(commit.getRevision()));
			
	return createConstructor("VcsCommit", "vcsCommit", children.toArray(new IValue[0]));
  }
  
  private IString convert(String toConvert) {
	if (toConvert == null) {
	  return values.string("null");
	}
	return values.string(toConvert);
  }
  
  private IConstructor convert(VcsCommitItem commitItem) {
	List<IValue> children = new ArrayList<>();
	
	children.add(convert(commitItem.getPath()));
	children.add(convert(commitItem.getChangeType()));
	children.add(createChurn(churns.get(commitItem.getCommit()), commitItem.getPath()));
				
	return createConstructor("VcsCommitItem", "vcsCommitItem", children.toArray(new IValue[0]));
  }

  private IConstructor convert(VcsChangeType changeType) {
	return values.constructor(store.lookupConstructor(store.lookupAbstractDataType("VcsChangeType"), changeType.name().toLowerCase(), TF.tupleEmpty()));
  }

  public IList createChurn(List<Churn> commitChurns, String itemPath) {
	IListWriter result = values.listWriter();
	
	for (Churn c : commitChurns) {
		if (c.getPath().equals(itemPath)) {
			result.append(createConstructor("Churn", "linesAdded", values.integer(c.getLinesAdded())));
			result.append(createConstructor("Churn", "linesDeleted", values.integer(c.getLinesDeleted())));
			break;
		}
	}
	
	return result.done();
  }
}
