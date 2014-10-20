module Churn

import org::ossmeter::metricprovider::ProjectDelta;
import Relation;
import List;

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

@metric{filesPerCommit}
@doc{Counts the number of files per commit}
@friendlyName{Number of files per commit}
@appliesTo{generic()}
map[loc, int] numberOfFilesPerCommit(ProjectDelta delta = \empty()) {
  map[loc, int] result = ();
  
  for (/VcsRepositoryDelta vcrd <- delta) {
    for (/VcsCommit vc <- delta) {
      result[vcrd.repository.url + vc.revision]? 0 += size(vc.items);
    }
  }
  
  return result;
}
  
@metric{coreCommitters}
@doc{Finds the core committers based on the churn they produce}
@friendlyName{Core committers}
@appliesTo{generic()}
@uses = ("org.ossmeter.metricprovider.trans.rascal.churnpercommitter.churnPerCommitter.historic" : "history")
list[loc] coreCommitters(rel[datetime, map[loc, int]] history = {}) {
  //NOTE: pongo stores items are sets so this metric breaks

  map[loc author, int churn] totalChurnPerAuthor = ();
  for (<_, historyMap> <- history) {
    for (author <- historyMap) {
      totalChurnPerAuthor[author] ? 0 += historyMap[author];
    }
  }
  list[int] churns = reverse(sort(range(totalChurnPerAuthor)));
  map[int, set[loc]] comparator = invert(totalChurnPerAuthor);
  
  return [author | authorChurn <- churns, author <- comparator[authorChurn]];
}

@metric{coreCommitersChurn}
@doc{Find the core committers and the churn they have produced}
@friendlyName{Churn per core committer}
@appliesTo{generic()}
@uses = ("org.ossmeter.metricprovider.trans.rascal.churnpercommitter.churnPerCommitter" : "committerChurn")
map[loc, int] coreCommittersChurn(map[loc, int] prev = (), map[loc, int] committerChurn = ()) {
  map[loc, int] result = ();
  
  for (/map[loc, int] prevMap := prev) {
    for (author <- prevMap) {
      result[author] ? 0 += prevMap[author];
    }
  }
  
  for (author <- committerChurn) {
    result[author] ? 0 += committerChurn[author];
  }
  
  return result;
}
  
