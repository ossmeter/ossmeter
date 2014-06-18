module Churn

import org::ossmeter::metricprovider::ProjectDelta;
import IO;

@metric{churnPerCommit}
@doc{Count churn}
@friendlyName{Counts number of lines added and deleted per commit}
@appliesTo{generic()}
@historic
map[loc, int] churnPerCommit(ProjectDelta delta = \empty()) {
   println(delta);
   map[loc, int] result = ();
   for (/VcsRepositoryDelta vcsDelta := delta) {
    result += (vcsDelta.repository.url + co.revision : churn(co) | /VcsCommit co := vcsDelta);
   }
   return result;
}
  
int churn(VcsCommit item) 
  = (0 | it + count | /linesAdded(count) := item)
  + (0 | it + count | /linesDeleted(count) := item)
  ;