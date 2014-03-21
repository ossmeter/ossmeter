package org.ossmeter.platform.vcs.workingcopy.manager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.ossmeter.platform.logging.OssmeterLoggerFactory;
import org.ossmeter.platform.util.ExtensionPointHelper;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.VcsRepository;

public class WorkingCopyFactory {
  private static final String WORKING_COPY_DIRECTORY = "workingCopies";
  private static final String MODEL_DIRECTORY = "workingModels";
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
        // TODO: this logger throws null pointer exceptions
//        LOGGER.error("exception while searching for a working copy creator", e);
        e.printStackTrace();
      }
    }
    
    throw new WorkingCopyManagerUnavailable(repository);
  }
  
  /**
   * Checks out a project at a certain revision. After this call for all repositories
   * associated with the current project a proper working copy will have been created.
   * 
   * This method returns references to directories where the working copies reside. 
   * The intention is to have clients never write in these directories such that the
   * working copies remain clean and such that the working copy managers may provide
   * efficient incremental updating without chance of merge conflicts. 
   * 
   * To store temporary data (models) associated with the working copies, this checkout
   * method also generates an empty folder for every repository of the project where
   * stuff can freely be written.
   * 
   * @param project   for which to check out all repositories
   * @param revision  the revision of the project
   * @return a map where the keys are directories containing working copies and the
   *         values are companion empty folders for storing extracted (model) data
   *         about the working copies.
   * @throws WorkingCopyManagerUnavailable if there is no registered means for creating a working copy for a certain VcsRepository.
   * @throws WorkingCopyCheckoutException  when a working copy manager generates an exception while producing the working copy.
   */
  public Map<File,File> checkout(Project project, String revision) throws WorkingCopyManagerUnavailable, WorkingCopyCheckoutException {
    File storage = new File(project.getStorage().getPath());
    File wc = new File(storage, WORKING_COPY_DIRECTORY);
    File md = new File(storage, MODEL_DIRECTORY);
    
    if (!wc.exists()) {
      wc.mkdirs();
    }
    
    Map<File,File> wcs = new HashMap<>();
    
    for (VcsRepository repo : project.getVcsRepositories()) {
      WorkingCopyManager manager = getWorkingCopyCreator(repo);
      String sub = encode(repo.getUrl());
      File checkout = new File(wc, sub);
      manager.checkout(checkout, repo, revision);
      
      File model = new File(md, sub);
      if (!model.exists()) {
        model.mkdirs();
      }
      
      wcs.put(checkout, model);
    }
    
    return wcs;
  }

  private String encode(String url) {
    StringBuilder b = new StringBuilder();
    
    for (char ch : url.toCharArray()) {
      if (Character.isLetterOrDigit(ch)) {
        b.append(ch);
      }
      else {
        b.append(String.format("_%x_", (int) ch));
      }
    }
    
    return b.toString();
  }
}
