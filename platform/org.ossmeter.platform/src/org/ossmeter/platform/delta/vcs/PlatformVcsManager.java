package org.ossmeter.platform.delta.vcs;

import java.util.List;

import org.ossmeter.platform.Date;
import org.ossmeter.platform.cache.vcs.IVcsContentsCache;
import org.ossmeter.platform.cache.vcs.IVcsDeltaCache;
import org.ossmeter.platform.cache.vcs.VcsContentsCache;
import org.ossmeter.platform.cache.vcs.VcsDeltaCache;
import org.ossmeter.repository.model.VcsRepository;

public abstract class PlatformVcsManager extends AbstractVcsManager {
	
	protected List<IVcsManager> vcsManagers;
	protected IVcsDeltaCache deltaCache;
	protected IVcsContentsCache contentsCache;
	
	abstract public List<IVcsManager> getVcsManagers();

	@Override
	public String getCurrentRevision(VcsRepository repository) throws Exception {
		IVcsManager manager = getVcsManager(repository);
		if (manager != null) {
			return manager.getCurrentRevision(repository);
		}
		return null;
	}
	
	@Override
	public String getFirstRevision(VcsRepository repository) throws Exception {
		IVcsManager manager = getVcsManager(repository);
		if (manager != null) {
			return manager.getFirstRevision(repository);
		}
		return null;
	}
	
	@Override
	public int compareVersions(VcsRepository repository, String versionOne, String versionTwo) throws Exception {
		IVcsManager manager = getVcsManager(repository);
		if (manager != null) {
			return manager.compareVersions(repository, versionOne, versionTwo);
		}
		return -1; // TODO: fair?
	}

	@Override
	public String[] getRevisionsForDate(VcsRepository repository, Date date) throws Exception {
		IVcsManager manager = getVcsManager(repository);
		if (manager != null) {
			return manager.getRevisionsForDate(repository, date);
		}
		return null;
	}

	@Override
	public Date getDateForRevision(VcsRepository repository, String revision) throws Exception {
		IVcsManager manager = getVcsManager(repository);
		if (manager != null) {
			return manager.getDateForRevision(repository, revision);
		}
		return null;
	}
	
	@Override
	public boolean appliesTo(VcsRepository repository) {
		return getVcsManager(repository) != null;
	}

	protected IVcsManager getVcsManager(VcsRepository repository) {
		for (IVcsManager vcsManager : getVcsManagers()) {
			if (vcsManager.appliesTo(repository)) {
				return vcsManager;
			}
		}
		throw new RuntimeException("No vcs manager found for repository " + repository);
	}
	
	@Override
	public VcsRepositoryDelta getDelta(VcsRepository repository, String startVersion, String endVersion)  throws Exception {
		VcsRepositoryDelta cache = getDeltaCache().getCachedDelta(repository, startVersion, endVersion);
		if (cache != null) {
			return cache;
		}
		
		IVcsManager vcsManager = getVcsManager(repository);
		if (vcsManager != null) {
			VcsRepositoryDelta delta = vcsManager.getDelta(repository, startVersion, endVersion);
			getDeltaCache().putDelta(repository, startVersion, endVersion, delta);
			return delta;
		}
		return null;
	}

	@Override
	public String getContents(VcsCommitItem item) throws Exception {
		String cache = getContentsCache().getCachedContents(item);
		if (cache != null) {
			return cache;
		}
		
		IVcsManager vcsManager = getVcsManager(item.getCommit().getDelta().getRepository());
		if (vcsManager != null) {
			String contents =  vcsManager.getContents(item);
			getContentsCache().putContents(item, contents);
			return contents;
		}
		return null;
	}
	
	public IVcsContentsCache getContentsCache() {
		if (contentsCache == null) {
			contentsCache = new VcsContentsCache();
		}
		return contentsCache;
	}
	
	public IVcsDeltaCache getDeltaCache() {
		if (deltaCache == null) {
			deltaCache = new VcsDeltaCache();
		}
		return deltaCache;		
	}
}
