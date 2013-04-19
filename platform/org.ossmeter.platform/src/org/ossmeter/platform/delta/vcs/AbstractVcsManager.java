package org.ossmeter.platform.delta.vcs;

import org.ossmeter.repository.model.VcsRepository;

public abstract class AbstractVcsManager implements IVcsManager {
	
	@Override
	public VcsRepositoryDelta getDelta(VcsRepository repository, String startRevision) throws Exception {
		return getDelta(repository, startRevision, getCurrentRevision(repository));
	}
	
}
