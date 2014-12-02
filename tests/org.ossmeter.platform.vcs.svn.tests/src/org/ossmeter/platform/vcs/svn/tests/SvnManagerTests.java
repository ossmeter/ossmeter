/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.ossmeter.platform.vcs.svn.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.delta.vcs.PlatformVcsManager;
import org.ossmeter.platform.delta.vcs.ServiceVcsManager;
import org.ossmeter.platform.delta.vcs.VcsCommit;
import org.ossmeter.platform.delta.vcs.VcsCommitItem;
import org.ossmeter.platform.delta.vcs.VcsRepositoryDelta;
import org.ossmeter.platform.vcs.svn.SvnManager;
import org.ossmeter.repository.model.vcs.svn.SvnRepository;

public class SvnManagerTests {

	protected SvnRepository getRepo() {
		SvnRepository repo = mock(SvnRepository.class);
		when(repo.getUrl()).thenReturn("http://pongo.googlecode.com/svn");
		return repo;
	}
	
//	@Test
	public void testPrintContents() throws Exception {
		
		SvnRepository repository = new SvnRepository();
		repository.setUrl("http://pongo.googlecode.com/svn");
		
		PlatformVcsManager platformVcsManager = new ServiceVcsManager();
		platformVcsManager.getVcsManagers().add(new SvnManager());
		
		VcsRepositoryDelta delta = platformVcsManager.getDelta(repository, "95"/*, "101"*/);
		
		for (VcsCommit commit : delta.getCommits()) {
			System.err.println(commit.getAuthor() + " -> " + commit.getMessage() + " : " + commit.getRevision());
			for (VcsCommitItem item : commit.getItems()) {
				//System.err.println("\t" + item.getPath() + " -> " + item.getChangeType());
				System.err.println(platformVcsManager.getContents(item));
			}
		}
	}
	
	@Test
	public void testAppliesTo() {
		SvnManager manager = new SvnManager();
		assertTrue(manager.appliesTo(mock(SvnRepository.class)));
		//assertFalse(manager.appliesTo(mock(GitRepository.class)));
	}
	
	@Test 
	public void testGetDelta() throws Exception {
		SvnManager manager = new SvnManager();
		SvnRepository repo = mock(SvnRepository.class);
		when(repo.getUrl()).thenReturn("http://pongo.googlecode.com/svn");
		
		VcsRepositoryDelta delta = manager.getDelta(repo, "0", "1");
		assertEquals(2, delta.getCommits().size());
	}
	
	@Test
	public void testGetCurrentRevision() {
		// ?
		
	}
	
	@Test
	public void testGetFirstRevision() throws Exception {
		SvnManager manager = new SvnManager();
		SvnRepository repo = getRepo();
		
		String rev = manager.getFirstRevision(repo);
		assertEquals("0", rev);
	}
	
	@Test
	public void testGetRevisionsForDate() throws Exception {
		SvnManager manager = new SvnManager();
		SvnRepository repo = getRepo();
		
		Date date = new Date("20130212");
		
		String[] revs = manager.getRevisionsForDate(repo, date);
		
		assertEquals(2, revs.length);
		assertEquals("81", revs[0]);
		assertEquals("83", revs[1]);
	}
	
	@Test
	public void testGetDateForRevision() throws Exception {
		SvnManager manager = new SvnManager();
		SvnRepository repo = getRepo();
		
		Date date = manager.getDateForRevision(repo, "81");
		
		assertEquals("20130212", date.toString());
	}
	
	@Test
	public void testValidateRepository() throws Exception {
		SvnManager manager = new SvnManager();
		SvnRepository repo = getRepo();
		
		assertTrue(manager.validRepository(repo));
		
		SvnRepository repo2 = new SvnRepository();
		repo2.setUrl("fooooo");
		assertFalse(manager.validRepository(repo2));
	}
}
