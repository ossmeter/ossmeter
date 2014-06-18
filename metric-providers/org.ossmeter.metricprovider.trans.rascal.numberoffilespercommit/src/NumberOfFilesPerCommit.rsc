module NumberOfFilesPerCommit

import List;
import org::ossmeter::metricprovider::ProjectDelta;

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