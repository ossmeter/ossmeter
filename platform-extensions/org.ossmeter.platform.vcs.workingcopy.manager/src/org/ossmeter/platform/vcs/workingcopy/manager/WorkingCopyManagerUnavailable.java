package org.ossmeter.platform.vcs.workingcopy.manager;

import org.ossmeter.repository.model.VcsRepository;

public class WorkingCopyManagerUnavailable extends Exception {
  private static final long serialVersionUID = -4749729537006181987L;
  private final VcsRepository repo;

  public WorkingCopyManagerUnavailable(VcsRepository repo) {
    super("Working copy manager unavailable for " + repo);
    this.repo = repo;
  }
  
  public VcsRepository getRepository() {
    return repo;
  }
}
