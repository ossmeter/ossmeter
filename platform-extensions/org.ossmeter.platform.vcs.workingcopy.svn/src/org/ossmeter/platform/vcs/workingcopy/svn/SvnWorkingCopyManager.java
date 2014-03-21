package org.ossmeter.platform.vcs.workingcopy.svn;

import java.io.File;

import org.ossmeter.platform.vcs.svn.SvnUtil;
import org.ossmeter.platform.vcs.workingcopy.manager.WorkingCopyCheckoutException;
import org.ossmeter.platform.vcs.workingcopy.manager.WorkingCopyManager;
import org.ossmeter.repository.model.VcsRepository;
import org.ossmeter.repository.model.vcs.svn.SvnRepository;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;

public class SvnWorkingCopyManager implements WorkingCopyManager {

  public SvnWorkingCopyManager() {
    // DO NOT REMOVE BECAUSE OSGI NEEDS IT
  }

  @Override
  public boolean appliesTo(VcsRepository repository) {
    return repository instanceof SvnRepository;
  }

  @Override
  public void checkout(File workingDirectory, VcsRepository repository, String revision)
      throws WorkingCopyCheckoutException {
    try {
      SvnUtil.setupLibrary();
      SVNRepository svn = SvnUtil.connectToSVNInstance(repository.getUrl());
      svn.checkout(Long.parseLong(revision), workingDirectory.getAbsolutePath(), true, null);
    } catch (NumberFormatException | SVNException e) {
      throw new WorkingCopyCheckoutException(repository, revision, e);
    }
  }
}
