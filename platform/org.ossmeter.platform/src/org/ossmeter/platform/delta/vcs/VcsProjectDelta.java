/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.ossmeter.platform.delta.vcs;

import java.util.ArrayList;
import java.util.List;

import org.ossmeter.platform.Date;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.VcsRepository;

public class VcsProjectDelta {
	
	protected List<VcsRepositoryDelta> repoDeltas = new ArrayList<VcsRepositoryDelta>();
	
	public VcsProjectDelta(Project project, Date date, IVcsManager vcsManager) throws Exception {
		for (VcsRepository repo : project.getVcsRepositories()) {
			String[] revs = vcsManager.getRevisionsForDate(repo, date);
			
			if (revs == null || revs.length == 0) continue;
			
			boolean foundNull = false;
			for (String r : revs) {
				if (r == null) {
					foundNull = true;
					break;
				}
			}
			if (foundNull) continue;
			
//			if (revs.length != 2 || revs[0] == null || revs[1] == null) return;
			
//			repoDeltas.add(vcsManager.getDelta(repo, revs[0], revs[1]));
			repoDeltas.add(vcsManager.getDelta(repo, revs[0], revs[revs.length-1]));
		}
	}
	
	public List<VcsRepositoryDelta> getRepoDeltas() {
		return repoDeltas;
	}
	
	public void setRepoDeltas(List<VcsRepositoryDelta> repoDeltas) {
		this.repoDeltas = repoDeltas;
	}
}
