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
  = vcsRepositoryDelta(VcsRepository repository, list[VcsCommit] commits, str lastRevision)
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
  
map[str, list[str]] getChangedItemsPerRepository(ProjectDelta delta) {
  list[str] emptyList = [];
  map[str, list[str]] result = ();
  for (/VcsRepositoryDelta vcrd <- delta) {
    result[vcrd.repository.url]? emptyList += [ vci.path | /VcsCommitItem vci <- vcrd ];
  }
  return result;
}