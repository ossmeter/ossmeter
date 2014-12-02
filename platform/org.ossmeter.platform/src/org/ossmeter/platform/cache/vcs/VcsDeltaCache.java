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
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DBMaker;
import org.mapdb.Fun;
import org.ossmeter.platform.delta.vcs.VcsRepositoryDelta;
import org.ossmeter.repository.model.VcsRepository;

public class VcsDeltaCache implements IVcsDeltaCache {
	
	protected ConcurrentNavigableMap<Fun.Tuple3<String, String, String>, VcsRepositoryDelta> map;
	
	public VcsDeltaCache() {
		initialiseDB();
	}
	
	protected void initialiseDB() {
		 map = DBMaker.newTempTreeMap(); // TODO: Do we want to allocate local storage for this?
	}
	
	public VcsRepositoryDelta getCachedDelta(VcsRepository repository, String startRevision, String endRevision){
		return map.get(Fun.t3(repository.getUrl(),startRevision,endRevision));
	}
	
	// TODO: This needs to be bounded
	public void putDelta(VcsRepository repository, String startRevision, String endRevision, VcsRepositoryDelta delta) {
		map.put(Fun.t3(repository.getUrl(),startRevision,endRevision), delta);
	}
	
}
