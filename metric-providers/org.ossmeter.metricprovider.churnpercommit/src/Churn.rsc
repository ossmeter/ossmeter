module Churn

import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;

@metric{churnPerCommit}
@doc{Count churn}
@friendlyName{Counts number of lines added and deleted per commit}
map[str revision, str churn] churnPerCommit(ProjectDelta delta, map[str, loc] workingCopyFolders, map[str, loc] scratchFolders) {
  map[str revision, str churn] result = ();
  
  
  for (/VcsRepositoryDelta vcsRepoDelta <- delta) {
    str lastRevision = vcsRepoDelta.commits[-1].revision;
    int added = 0;
    int deleted = 0;
    for (Churn churn <- vcsRepoDelta.churns) {
      if (\linesAdded(int i) := churn) {
        added += i;
      }
      if (\linesDeleted(int i) := churn) {
        deleted += i;
      }
    }
    result[lastRevision] = "Lines added = <added> : Lines deleted = <deleted>";
  }
  
  return result;
}