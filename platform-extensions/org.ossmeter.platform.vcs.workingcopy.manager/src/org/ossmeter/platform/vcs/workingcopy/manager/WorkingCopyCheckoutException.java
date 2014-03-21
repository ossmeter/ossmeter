package org.ossmeter.platform.vcs.workingcopy.manager;

import org.ossmeter.repository.model.VcsRepository;

public class WorkingCopyCheckoutException extends Exception {
  private static final long serialVersionUID = 8393210027833758295L;
  private final VcsRepository repo;
  private final String revision;

  public WorkingCopyCheckoutException(VcsRepository repo, String revision, Throwable cause) {
    super("could not checkout revision of " + repo.getUrl() + " for revision " + revision, cause);
    this.repo = repo;
    this.revision = revision;
  }
  
  public VcsRepository getRepository() {
    return repo;
  }
  
  public String getRevision() {
    return revision;
  }
}
