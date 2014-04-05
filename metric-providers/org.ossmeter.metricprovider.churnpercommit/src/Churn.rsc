module Churn

import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;

@metric{churnPerCommit}
@doc{Count churn}
@friendlyName{Counts number of lines added and deleted per commit}
map[str revision, int churn] churnPerCommit(ProjectDelta delta, map[str, loc] workingCopyFolders, map[str, loc] scratchFolders)
  = (co.revision : churn(co) | /VcsCommit co := delta)
  ;
  
int churn(VcsCommit item) 
  = (0 | it + count | /linesAdded(count) := item)
  + (0 | it + count | /linesDeleted(count) := item)
  ;