package org.ossmeter.platform.vcs.svn;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Map;

import org.ossmeter.platform.Date;
import org.ossmeter.platform.delta.vcs.AbstractVcsManager;
import org.ossmeter.platform.delta.vcs.VcsChangeType;
import org.ossmeter.platform.delta.vcs.VcsCommit;
import org.ossmeter.platform.delta.vcs.VcsCommitItem;
import org.ossmeter.platform.delta.vcs.VcsRepositoryDelta;
import org.ossmeter.repository.model.SvnRepository;
import org.ossmeter.repository.model.VcsRepository;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.io.SVNRepository;

public class SvnManager extends AbstractVcsManager {
	
	@Override
	public boolean appliesTo(VcsRepository repository) {
		return repository instanceof SvnRepository;
	}

	protected SVNRepository getSVNRepository(SvnRepository repository) {
		SvnUtil.setupLibrary();
		SVNRepository svnRepository = SvnUtil.connectToSVNInstance(repository.getUrl());
		return svnRepository;
	}
	
	@Override
	public VcsRepositoryDelta getDelta(VcsRepository repository, String startRevision, String endRevision) throws Exception {
		SvnRepository _svnRepository = (SvnRepository) repository;
		SVNRepository svnRepository = getSVNRepository(_svnRepository);

		VcsRepositoryDelta delta = new VcsRepositoryDelta();
		delta.setRepository(repository);
		
		if (!startRevision.equals(endRevision)) {
			Collection<?> c = svnRepository.log(new String[]{""}, null, Long.valueOf(startRevision), Long.valueOf(endRevision), true, true);

			for (Object o : c) {
				SVNLogEntry svnLogEntry = (SVNLogEntry) o;

				VcsCommit commit = new VcsCommit();
				
				commit.setAuthor(svnLogEntry.getAuthor());
				commit.setMessage(svnLogEntry.getMessage());
				commit.setRevision(svnLogEntry.getRevision() + "");
				commit.setDelta(delta);
				delta.getCommits().add(commit);
				
				Map<String, SVNLogEntryPath> changedPaths = svnLogEntry.getChangedPaths();
				for (String path : changedPaths.keySet()) {
					SVNLogEntryPath svnLogEntryPath = changedPaths.get(path);

					if (svnLogEntryPath.getKind() == SVNNodeKind.FILE) {
						
						VcsCommitItem commitItem = new VcsCommitItem();
						commit.getItems().add(commitItem);
						commitItem.setCommit(commit);
						commitItem.setPath(path);
						
						if (svnLogEntryPath.getType() == 'A') {
							commitItem.setChangeType(VcsChangeType.ADDED);
						} else if (svnLogEntryPath.getType() == 'M') {
							commitItem.setChangeType(VcsChangeType.UPDATED);
						} else if (svnLogEntryPath.getType() == 'D') {
							commitItem.setChangeType(VcsChangeType.DELETED);
						} else if (svnLogEntryPath.getType() == 'R') {
							commitItem.setChangeType(VcsChangeType.REPLACED);
						} else {
							System.err.println("Found unrecognised svn log entry type: " + svnLogEntryPath.getType());
							commitItem.setChangeType(VcsChangeType.UNKNOWN);
						}
					}
				}
			}
		}

		return delta;
	}
	
	@Override
	public String getContents(VcsCommitItem item) throws Exception {
		
		SVNRepository repository = getSVNRepository((SvnRepository) item.getCommit().getDelta().getRepository());
		
		SVNProperties fileProperties = new SVNProperties();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		repository.getFile(item.getPath(), Long.valueOf(item.getCommit().getRevision()), fileProperties, baos);
        
		//TODO: Store mimetype?
		//TODO: Think about adding a notion of a filter
//		String mimeType = fileProperties.getStringValue(SVNProperty.MIME_TYPE);
     
        StringBuffer sb = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())));
		String line;
		while((line =reader.readLine())!= null ){
			//TODO: Think about a platform-wide new line character
			sb.append(line + "\r\n");
		}
		return sb.toString();
	}

	@Override
	public String getCurrentRevision(VcsRepository repository) throws Exception {
		return getSVNRepository((SvnRepository) repository).getLatestRevision() + "";
	}
	
	/**
	 * TODO: Cache the log?
	 */
	@Override
	public String getFirstRevision(VcsRepository repository) throws Exception {
		SVNRepository svnRepository = getSVNRepository((SvnRepository) repository);
		Collection<?> c = svnRepository.log(new String[]{""}, null, 0, Long.valueOf(getCurrentRevision(repository)), true, true);
		
		for (Object o : c) {
			return String.valueOf(((SVNLogEntry) o).getRevision());
		}
		return null;
	}
	
	@Override
	public int compareVersions(VcsRepository repository, String versionOne, String versionTwo) throws Exception {
		return (Long.valueOf(versionOne).compareTo(Long.valueOf(versionTwo)));
	}

	/**
	 * TODO: Is there a more efficient implementation? (simple cache?)
	 */
	@Override
	public String[] getRevisionsForDate(VcsRepository repository, Date date) throws Exception {
		String[] revs = new String[2];
		
		SvnRepository _svnRepository = (SvnRepository) repository;
		SVNRepository svnRepository = getSVNRepository(_svnRepository);
		
		Collection<?> c = svnRepository.log(new String[]{""}, null, 0, Long.valueOf(getCurrentRevision(repository)), true, true);
		boolean foundStart = false;
		SVNLogEntry svnLogEntry;
		
		for (Object o : c) {
			svnLogEntry = (SVNLogEntry) o;
			int dateComparison = date.compareTo(svnLogEntry.getDate());
			
			if (!foundStart && dateComparison == 0) {
				revs[0] = String.valueOf(svnLogEntry.getRevision());
				revs[1] = String.valueOf(svnLogEntry.getRevision());
				foundStart = true;
			} else if (foundStart && dateComparison == 0) {
				revs[1] = String.valueOf(svnLogEntry.getRevision());
			} else if (dateComparison < 0) { // Future
				break;
			}
		}
		
		return revs;
	}

	/**
	 */
	@Override
	public Date getDateForRevision(VcsRepository repository, String revision) throws Exception {
		SvnRepository _svnRepository = (SvnRepository) repository;
		SVNRepository svnRepository = getSVNRepository(_svnRepository);
		
		Collection<?> c = svnRepository.log(new String[]{""}, null, 0, Long.valueOf(getCurrentRevision(repository)), true, true);
		SVNLogEntry svnLogEntry;
		
		for (Object o : c) {
			svnLogEntry = (SVNLogEntry) o;
			if (svnLogEntry.getRevision() == Long.valueOf(revision)) {
				return new Date(svnLogEntry.getDate());
			}
		}
		return null;
	}
}
