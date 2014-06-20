module CommitterChurn

import org::ossmeter::metricprovider::ProjectDelta;

@metric{churnPerCommitter}
@doc{Count churn per committer}
@friendlyName{Counts number of lines added and deleted per committer}
@appliesTo{generic()}
map[loc author, int churn] churnPerCommitter(ProjectDelta delta = \empty())
  = (|author:///| + co.author : churn(co) | /VcsCommit co := delta)
  ;
  
int churn(VcsCommit item) 
  = (0 | it + count | /linesAdded(count) := item)
  + (0 | it + count | /linesDeleted(count) := item)
  ;
