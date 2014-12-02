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

import org.ossmeter.platform.delta.vcs.VcsCommitItem;


public interface IVcsContentsCache {
	
	public String getCachedContents(VcsCommitItem item);
	
	public void putContents(VcsCommitItem item, String contents);
}
