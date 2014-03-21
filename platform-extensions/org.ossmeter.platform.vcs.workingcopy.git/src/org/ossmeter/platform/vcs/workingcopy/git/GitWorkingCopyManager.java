package org.ossmeter.platform.vcs.workingcopy.git;

import java.io.File;
import java.io.IOException;

import org.ossmeter.platform.vcs.workingcopy.manager.WorkingCopyCheckoutException;
import org.ossmeter.platform.vcs.workingcopy.manager.WorkingCopyManager;
import org.ossmeter.repository.model.VcsRepository;
import org.ossmeter.repository.model.vcs.git.GitRepository;

public class GitWorkingCopyManager implements WorkingCopyManager {

  public GitWorkingCopyManager() {
    // DO NOT REMOVE
  }

  @Override
  public boolean appliesTo(VcsRepository repository) {
    return repository instanceof GitRepository;
  }

  @Override
  public void checkout(File workingDirectory, VcsRepository repository, String revision)
      throws WorkingCopyCheckoutException {
    try {
      if (workingDirectory.exists()) {
        // we assume we did it before and will now just pull
        Process p = Runtime.getRuntime().exec(new String[] { "git", "pull"}, new String[] { }, workingDirectory);
        p.waitFor();
      }
      else {
        // we clone
        // TODO: we'd rather use the SVNkit but that blocks indefinitely on loading classes (see above)
        Process p = Runtime.getRuntime().exec(new String[] { "git", "clone", repository.getUrl(), workingDirectory.getAbsolutePath() });
        p.waitFor();
      }
    } catch (IOException | InterruptedException e) {
      throw new WorkingCopyCheckoutException(repository, revision, e);
    }
  }
}
