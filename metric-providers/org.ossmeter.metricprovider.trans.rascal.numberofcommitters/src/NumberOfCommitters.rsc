module NumberOfCommitters

import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;

import Set;

@metric{NumberOfCommittersperFile}
@doc{Count the number of committers that have touced a file}
@friendlyName{Number of Committers per file}
map[str file, int numberOfCommitters] countCommittersPerFile(ProjectDelta delta, map[loc, loc] workingCopyFolders, map[loc, loc] scratchFolders) {
  map[str, set[str]] result = committersPerFile(delta, workingCopyFolders, scratchFolders);
  
  return (key : size(result[key]) | key <- result);
}

map[str, set[str]] committersPerFile(ProjectDelta delta, map[loc, loc] workingCopyFolders, map[loc, loc] scratchFolders) {
  map[str file, set[str] committers] result = ();
  set[str] emptySet = {};
  for (/VcsCommit vc <- delta, vc.author != "null") {
    for (VcsCommitItem vci <- vc.items) {
      // Need to check that the committer is not already counted
      result[vci.path]? emptySet += {vc.author};
    }
  }
  
  return result;
}