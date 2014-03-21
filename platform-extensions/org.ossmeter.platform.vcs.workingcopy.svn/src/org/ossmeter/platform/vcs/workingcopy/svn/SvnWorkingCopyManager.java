package org.ossmeter.platform.vcs.workingcopy.svn;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.ossmeter.platform.vcs.svn.SvnUtil;
import org.ossmeter.platform.vcs.workingcopy.manager.WorkingCopyCheckoutException;
import org.ossmeter.platform.vcs.workingcopy.manager.WorkingCopyManager;
import org.ossmeter.repository.model.VcsRepository;
import org.ossmeter.repository.model.vcs.svn.SvnRepository;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc2.SvnCheckout;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;
import org.tmatesoft.svn.core.wc2.SvnTarget;

public class SvnWorkingCopyManager implements WorkingCopyManager {

  public SvnWorkingCopyManager() {
    // DO NOT REMOVE BECAUSE OSGI NEEDS IT
  }

  @Override
  public boolean appliesTo(VcsRepository repository) {
    return repository instanceof SvnRepository;
  }

//  @Override
  public void checkoutBroken(File workingDirectory, VcsRepository repository, String revision)
      throws WorkingCopyCheckoutException {
    try {
      SvnUtil.setupLibrary();
      final SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
      try {
          final SvnCheckout checkout = svnOperationFactory.createCheckout();
          checkout.setSingleTarget(SvnTarget.fromFile(workingDirectory));
          checkout.setSource(SvnTarget.fromURL(SVNURL.parseURIEncoded(repository.getUrl()), SVNRevision.create(Long.parseLong(revision))));
          checkout.run();
      } finally {
          svnOperationFactory.dispose();
      }
    } catch (NumberFormatException | SVNException e) {
      throw new WorkingCopyCheckoutException(repository, revision, e);
    }
  }
  
  @Override
  public void checkout(File workingDirectory, VcsRepository repository, String revision)
      throws WorkingCopyCheckoutException {
    try {
      // TODO: we'd rather use the SVNkit but that blocks indefinitely on loading classes (see above)
      Process p = Runtime.getRuntime().exec(new String[] { "svn", "checkout", "-r", revision, repository.getUrl(), workingDirectory.getAbsolutePath() });
      p.waitFor();
    } catch (IOException | InterruptedException e) {
      throw new WorkingCopyCheckoutException(repository, revision, e);
    }
    
  }
}
