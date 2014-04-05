module CoreCommitters

import org::ossmeter::metricprovider::Manager;
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
list[str] coreCommitters(ProjectDelta delta, map[str, loc] workingCopyFolders, map[str, loc] scratchFolders) {
  map[str author, int churn] committerChurn = churnPerCommitter(delta, workingCopyFolders, scratchFolders);
  map[str author, int churn] olderResult = ();
  
  loc coreCommittersHistory = |home:///ossmeter/<delta.project.name>/corecommitters.am3|;
  
  if (exists(coreCommittersHistory)) {
    olderResult = readBinaryValueFile(#map[str, int], coreCommittersHistory);
    for (str author <- olderResult) {
      committerChurn[author]? 0 += olderResult[author];
    }
  }
  
  writeBinaryValueFile(coreCommittersHistory, committerChurn);
    
  list[int] churns = reverse(sort(range(committerChurn)));
  map[int, set[str]] comparator = invert(committerChurn);
    
  println(comparator);
  
  return [author | authorChurn <- churns, author <- comparator[authorChurn]];
}
