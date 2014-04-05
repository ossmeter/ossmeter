module NumberOfFilesPerCommit

import List;
import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;

@metric{filesPerCommit}
@doc{Counts the number of files per commit}
@friendlyName{Number of files per commit}
map[str revision, int count] numberOfFilesPerCommit(ProjectDelta delta, map[loc, loc] workingCopy, map[loc, loc] scratch) {
  map[str revision, int count] result = ();
  
  for (/VcsCommit vc <- delta) {
    result[vc.revision]? 0 += size(vc.items);
  }
  
  return result;
}