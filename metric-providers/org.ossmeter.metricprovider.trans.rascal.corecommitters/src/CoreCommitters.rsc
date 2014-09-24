module CoreCommitters

import org::ossmeter::metricprovider::ProjectDelta;
import CommitterChurn;

import ValueIO;
import IO;
import Map;
import Set;
import List;

@metric{coreCommitters}
@doc{Finds the core committers based on the churn they produce}
@friendlyName{Core committers}
@appliesTo{generic()}
@uses = ("org.ossmeter.metricprovider.trans.rascal.churnpercommitter.churnPerCommitter.historic" : "history")
list[loc] coreCommitters(rel[datetime, map[loc, int]] history = {}) {
  //NOTE: pongo stores items are sets so this metric breaks
  println(history);
  map[loc author, int churn] totalChurnPerAuthor = ();
  for (<_, historyMap> <- history) {
    for (author <- historyMap) {
      totalChurnPerAuthor[author] ? 0 += historyMap[author];
    }
  }
  println(totalChurnPerAuthor);
  list[int] churns = reverse(sort(range(totalChurnPerAuthor)));
  println(churns);
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
