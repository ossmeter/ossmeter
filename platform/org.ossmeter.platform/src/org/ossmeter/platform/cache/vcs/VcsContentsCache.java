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
package org.ossmeter.platform.cache.vcs;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.ossmeter.platform.delta.vcs.VcsCommitItem;

public class VcsContentsCache implements IVcsContentsCache {
	
	protected HTreeMap<VcsCommitItem, String> map;
	
	public VcsContentsCache() {
		initialiseDB();
	}
	
	protected void initialiseDB() {
		 map = DBMaker.newTempHashMap(); // TODO: Do we want to allocate local storage for this?
	}
	
	@Override
	public String getCachedContents(VcsCommitItem item){
		return map.get(item);
	}
	
	// TODO: This needs to be bounded
	@Override
	public void putContents(VcsCommitItem item, String contents) {
		map.put(item, contents);
	}
}
