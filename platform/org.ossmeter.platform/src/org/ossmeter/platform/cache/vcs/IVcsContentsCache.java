package org.ossmeter.platform.cache.vcs;

import org.ossmeter.platform.delta.vcs.VcsCommitItem;


public interface IVcsContentsCache {
	
	public String getCachedContents(VcsCommitItem item);
	
	public void putContents(VcsCommitItem item, String contents);
}
