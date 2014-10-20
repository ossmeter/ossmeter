module NumberOfCommitters

import org::ossmeter::metricprovider::ProjectDelta;

import Set;

@metric{NumberOfCommittersperFile}
@doc{Count the number of committers that have touced a file}
@friendlyName{Number of Committers per file}
@appliesTo{generic()}
map[loc file, int numberOfCommitters] countCommittersPerFile(ProjectDelta delta = \empty()) {
	  commPerFile = committersPerFile(delta);
	  return (f : size(commPerFile[f]) | f <- commPerFile);
}

map[loc, set[str]] committersPerFile(ProjectDelta delta) {
  map[loc file, set[str] committers] result = ();
  set[str] emptySet = {};
  for (/VcsRepositoryDelta vcrd <- delta) {
    loc repo = vcrd.repository.url;
    for (/VcsCommit vc <- delta, vc.author != "null") {
      for (VcsCommitItem vci <- vc.items) {
        // Need to check that the committer is not already counted
        result[repo+vci.path]? emptySet += {vc.author};
      }
    }
  }
  
  return result;
}
