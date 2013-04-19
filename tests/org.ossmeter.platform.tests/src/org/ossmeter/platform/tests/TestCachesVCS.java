package org.ossmeter.platform.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.ossmeter.platform.cache.vcs.IVcsContentsCache;
import org.ossmeter.platform.cache.vcs.IVcsDeltaCache;
import org.ossmeter.platform.cache.vcs.VcsContentsCache;
import org.ossmeter.platform.cache.vcs.VcsDeltaCache;
import org.ossmeter.platform.delta.vcs.VcsChangeType;
import org.ossmeter.platform.delta.vcs.VcsCommit;
import org.ossmeter.platform.delta.vcs.VcsCommitItem;
import org.ossmeter.platform.delta.vcs.VcsRepositoryDelta;
import org.ossmeter.repository.model.VcsRepository;

public class TestCachesVCS {

	@Test 
	public void testMissPutHit_Delta_MapDB() {
		missPutHit_Delta(new VcsDeltaCache());
	}
	
	@Test 
	public void testMissPutHit_Contents_MapDB() {
		missPutHit_Contents(new VcsContentsCache());
	}

	protected void missPutHit_Delta(IVcsDeltaCache cache) {
		VcsRepository repo = mock(VcsRepository.class);
		when(repo.getId()).thenReturn("id");
		VcsRepositoryDelta delta = mock(VcsRepositoryDelta.class);
		
		// Miss
		assertNull(cache.getCachedDelta(repo, "1", "10"));
		
		// Put
		cache.putDelta(repo, "1", "10", delta);
		
		// Hit
		assertNotNull(cache.getCachedDelta(repo, "1", "10"));
	}

	protected void missPutHit_Contents(IVcsContentsCache cache) {
		VcsCommitItem item = mock(VcsCommitItem.class);
		VcsCommit commit = mock(VcsCommit.class);
		
		when(item.getChangeType()).thenReturn(VcsChangeType.ADDED);
		when(item.getPath()).thenReturn("/foo/bar");
		when(item.getCommit()).thenReturn(commit);
		
		String contents = "The quick brown fox jumped over the lazy dog.";		
		
		// Miss
		assertNull(cache.getCachedContents(item));
		
		// Put
		cache.putContents(item, contents);
		
		// Hit
		assertNotNull(cache.getCachedContents(item));
	}
}
