package org.ossmeter.platform.vcs.workingcopy.manager;

import java.io.File;

import org.ossmeter.repository.model.VcsRepository;

public interface WorkingCopyManager {
  /**
   * @return true iff this working copy manager can manage to check out a working copy for this repository.
   */
  boolean appliesTo(VcsRepository repository);

  /**
   * Makes sure that after running this code in workingDirectory an up-to-date and 
   * clean check out is available.
   * 
   * @param workingdirectory  where to check out the working copy
   * @param repository        from where to get the code
   * @param revision 
   * @throws WorkingCopyCheckoutException in case something goes awry
   */
  void checkout(File workingDirectory, VcsRepository repository, String revision) throws WorkingCopyCheckoutException;
}
