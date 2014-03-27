module Churn

import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;

@metric{churnPerCommit}
@doc{Count churn}
@friendlyName{Counts number of lines added and deleted per commit}
map[str revision, str churn] churnPerCommit(ProjectDelta delta, map[str, loc] workingCopyFolders, map[str, loc] scratchFolders) {
  map[str revision, str churn] result = ();
  
  
  for (/VcsCommit commit <- delta) {
    int added = 0;
    int deleted = 0;
    for (VcsCommitItem vci <- commit.items) {
      for (Churn lc <- vci.lineChurn) {
        if (\linesAdded(int i) := lc) {
          added += i;
        }
        if (\linesDeleted(int i) := lc) {
          deleted += i;
        }
      }
    }
    result[commit.revision] = "Lines added = <added> : Lines deleted = <deleted>";
  }
  
  return result;
}