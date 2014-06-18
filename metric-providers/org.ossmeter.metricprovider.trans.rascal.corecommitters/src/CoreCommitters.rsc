module CoreCommitters

import org::ossmeter::metricprovider::ProjectDelta;
import CommitterChurn;

import ValueIO;
import IO;
import Map;
import Set;
import List;

@metric{coreCommitters}
@doc{Finds the core committers per file based on the churn they produce}
@friendlyName{Core committers}
@appliesTo{generic()}
list[str] coreCommitters(ProjectDelta delta = \empty()) {
  map[loc author, int churn] committerChurn = churnPerCommitter(delta=delta);
  map[loc author, int churn] olderResult = ();
  
  loc coreCommittersHistory = |home:///ossmeter/<delta.project.name>/corecommitters.am3|; // TODO remove cache, use helper metric instead 
  
  if (exists(coreCommittersHistory)) {
    olderResult = readBinaryValueFile(#map[loc, int], coreCommittersHistory);
    for (loc author <- olderResult) {
      committerChurn[author]? 0 += olderResult[author];
    }
  }
  
  writeBinaryValueFile(coreCommittersHistory, committerChurn);
    
  list[int] churns = reverse(sort(range(committerChurn)));
  map[int, set[loc]] comparator = invert(committerChurn);
  
  return [author.path | authorChurn <- churns, author <- comparator[authorChurn]];
}
