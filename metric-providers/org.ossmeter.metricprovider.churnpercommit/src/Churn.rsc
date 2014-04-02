module Churn

import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;

@metric{churnPerCommit}
@doc{Count churn}
@friendlyName{Counts number of lines added and deleted per commit}
map[str revision, str churn] churnPerCommit(ProjectDelta delta, map[str, loc] workingCopyFolders, map[str, loc] scratchFolders) {
  map[str revision, str churn] result = ();
  
  
  for (/VcsCommit commit <- delta) {
    int linesAdded = 0;
    int linesDeleted = 0;
    for (VcsCommitItem item <- commit.items) {
       visit(item.churns) {
         case \linesAdded(int i): {
           linesAdded += i;
         }
         case \linesDeleted(int i): {
           linesDeleted += i;
         }
       }
    }
    result[commit.revision] = "added = <linesAdded> : deleted = <linesDeleted>";
  }
  
  return result;
}