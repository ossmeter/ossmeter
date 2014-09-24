module FileChurn

import org::ossmeter::metricprovider::ProjectDelta;


  
int churn(VcsCommitItem item) 
  = (0 | it + count | /linesAdded(count) := item)
  + (0 | it + count | /linesDeleted(count) := item)
  ;
  
