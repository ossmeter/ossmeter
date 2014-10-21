module Churn

import org::ossmeter::metricprovider::ProjectDelta;
import IO;

@metric{churnPerCommit}
@doc{Count churn}
@friendlyName{Counts number of lines added and deleted per commit}
@appliesTo{generic()}
@historic{}
map[loc, int] churnPerCommit(ProjectDelta delta = \empty()) {
   map[loc, int] result = ();
   for (/VcsRepositoryDelta vcsDelta := delta) {
    result += (vcsDelta.repository.url + co.revision : churn(co) | /VcsCommit co := vcsDelta);
   }
   return result;
}
  
@metric{churnPerCommitter}
@doc{Count churn per committer}
@friendlyName{Counts number of lines added and deleted per committer}
@appliesTo{generic()}
@historic{}
map[loc author, int churn] churnPerCommitter(ProjectDelta delta = \empty())
  = (|author:///| + co.author : churn(co) | /VcsCommit co := delta)
  ;

@metric{churnPerFile}
@doc{Count churn}
@friendlyName{Counts number of lines added and deleted per file}
@appliesTo{generic()}
@historic{}
map[loc file, int churn] churnPerFile(ProjectDelta delta = \empty())
  = (rd.repository.url + co.path : churn(co) | /VcsRepositoryDelta rd := delta, /VcsCommitItem co := rd)
  ;
      
int churn(node item) 
  = (0 | it + count | /linesAdded(count) := item)
  + (0 | it + count | /linesDeleted(count) := item)
  ;

  
@metric{commitBehavior}
@doc{Are commits big or small? Spread out or focused?}  
Factoid commitBehavior() {

} 