package org.ossmeter.platform.cache.vcs;
import org.ossmeter.platform.delta.vcs.VcsRepositoryDelta;
import org.ossmeter.repository.model.VcsRepository;


public interface IVcsDeltaCache {
	
	public VcsRepositoryDelta getCachedDelta(VcsRepository repository, String startVersion, String endVersion);
	
	public void putDelta(VcsRepository repository, String startVersion, String endVersion, VcsRepositoryDelta delta);
}
