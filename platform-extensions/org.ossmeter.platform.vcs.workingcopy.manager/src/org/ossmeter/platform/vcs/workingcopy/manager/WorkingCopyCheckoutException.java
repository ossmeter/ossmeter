package org.ossmeter.platform.vcs.workingcopy.manager;

import org.ossmeter.repository.model.Project;

public class WorkingCopyCheckoutException extends Exception {
  private static final long serialVersionUID = 8393210027833758295L;
  private final Project project;
  private final String revision;

  public WorkingCopyCheckoutException(Project project, String revision) {
    super("could not checkout revision of " + project.getName() + " for revision " + revision);
    this.project = project;
    this.revision = revision;
  }
  
  public Project getProject() {
    return project;
  }
  
  public String getRevision() {
    return revision;
  }
}
