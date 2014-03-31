module org::ossmeter::metricprovider::ProjectDelta

data ProjectDelta
  = projectDelta(datetime date, Project project, list[VcsRepositoryDelta] vcsProjectDelta)
  | \empty()
  ;
  
data Project
  = project(str name, list[VcsRepository] vcsRepositories)
  ;
  
data VcsRepository
  = vcsRepository(str url)
  ;
  
data VcsRepositoryDelta
  = vcsRepositoryDelta(VcsRepository repository, list[VcsCommit] commits, str lastRevision, list[Churn] churns)
  ;
  
data VcsCommit
  = vcsCommit(datetime date, str author, str message, list[VcsCommitItem] items, str revision)
  ;
  
data VcsCommitItem
  = vcsCommitItem(str path, VcsChangeType changeType)
  ;
  
data VcsChangeType
  = added()
  | deleted()
  | updated()
  | replaced()
  | unknown()
  ;
  
data Churn
  = linesAdded(int i)
  | linesDeleted(int i)
  ;
  
map[str, list[str]] getChangedItemsPerRepository(ProjectDelta delta) {
  list[str] emptyList = [];
  map[str, list[str]] result = ();
  for (/VcsRepositoryDelta vcrd <- delta) {
    result[vcrd.repository.url]? emptyList += [ fVCI.path | VcsCommitItem fVCI <- checkSanity([ vci | /VcsCommitItem vci <- vcrd ]) ];
  }
  return result;
}

set[VcsCommitItem] checkSanity(list[VcsCommitItem] items) {
  set[VcsCommitItem] result = {};
  for (VcsCommitItem item <- items) {
    result += item;
    if (\deleted() := item.changeType || \unknown() := item.changeType) {
      result -= { vci | VcsCommitItem vci <- items, vci.path == item.path };
    }
  }
  return result;
}