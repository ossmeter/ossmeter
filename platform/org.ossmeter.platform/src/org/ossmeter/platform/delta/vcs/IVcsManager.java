package org.ossmeter.platform.delta.vcs;

import org.ossmeter.platform.Date;
import org.ossmeter.repository.model.VcsRepository;

public interface IVcsManager {
	
	public boolean appliesTo(VcsRepository repository);
	
	public String getCurrentRevision(VcsRepository repository) throws Exception;
	
	public String getFirstRevision(VcsRepository repository) throws Exception;
	
	public int compareVersions(VcsRepository repository, String versionOne, String versionTwo) throws Exception;
	
	public VcsRepositoryDelta getDelta(VcsRepository repository, String startRevision) throws Exception;
	
	public VcsRepositoryDelta getDelta(VcsRepository repository, String startRevision, String endRevision) throws Exception;
	
	public String[] getRevisionsForDate(VcsRepository repository, Date date) throws Exception;
	
	public Date getDateForRevision(VcsRepository repository, String revision) throws Exception;
	
	public String getContents(VcsCommitItem file) throws Exception;
	
	public boolean validRepository(VcsRepository repository) throws Exception;
	
}
