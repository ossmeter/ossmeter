module FileChurn

import org::ossmeter::metricprovider::Manager;
import org::ossmeter::metricprovider::ProjectDelta;

@metric{churnPerFile}
@doc{Count churn}
@friendlyName{Counts number of lines added and deleted per file}
map[str file, str churn] churnPerFile(ProjectDelta delta, map[str, loc] workingCopyFolders, map[str, loc] scratchFolders)
  = (co.path : churn(co) | /VcsCommitItem co := delta)
  ;
  
int churn(VcsCommitItem item) 
  = (0 | it + count | /linesAdded(count) := item)
  + (0 | it + count | /linesDeleted(count) := item)
  ;
  
//map[str file, str churn] result = ();
//  
//  for (/VcsCommitItem vci <- delta) {
//    int linesAdded = 0;
//    int linesDeleted = 0;
//    visit(vci.churns) {
//      case \linesAdded(int i): {
//        linesAdded = i;
//      }
//      case \linesDeleted(int i): {
//        linesDeleted = i;
//      }
//    }
//    result[vci.path] = "added = <linesAdded> : deleted = <linesDeleted>";
//  }
//  
//  return result;
//}