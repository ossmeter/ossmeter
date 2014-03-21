package org.ossmeter.platform.vcs.workingcopy.manager;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.ossmeter.platform.logging.OssmeterLoggerFactory;
import org.ossmeter.platform.util.ExtensionPointHelper;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.VcsRepository;

public class WorkingCopyFactory {
  private static final String WORKING_COPY_DIRECTORY = "workingCopies";
  private static final Logger LOGGER = new OssmeterLoggerFactory().makeNewLoggerInstance("workingCopyManagerFactory");
  
  private static class InstanceKeeper {
    public static WorkingCopyFactory instance = new WorkingCopyFactory();
  }

  public static WorkingCopyFactory getInstance() {
    return InstanceKeeper.instance;
  }

  private WorkingCopyManager getWorkingCopyCreator(VcsRepository repository) throws WorkingCopyManagerUnavailable {
    for (IConfigurationElement confElement : ExtensionPointHelper.getConfigurationElementsForExtensionPoint("org.ossmeter.vcs.workingcopymanager")) {
      try {
         WorkingCopyManager c = (WorkingCopyManager) confElement.createExecutableExtension("manager");
         
         if (c.appliesTo(repository)) {
           return c;
         }
      } catch (CoreException e) {
        LOGGER.error("exception while searching for a working copy creator", e);
      }
    }
    
    throw new WorkingCopyManagerUnavailable(repository);
  }
  
  public List<File> checkout(Project project, String revision) throws WorkingCopyManagerUnavailable, WorkingCopyCheckoutException {
    File storage = new File(project.getStorage().getPath());
    File wc = new File(storage, WORKING_COPY_DIRECTORY);
    List<File> wcs = new LinkedList<>();
    
    for (VcsRepository repo : project.getVcsRepositories()) {
      WorkingCopyManager manager = getWorkingCopyCreator(repo);
      File checkout = new File(wc, repo.getUrl());
      manager.checkout(checkout, repo, revision);
      wcs.add(checkout);
    }
    
    return wcs;
  }
}
