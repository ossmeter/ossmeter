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
package com.tecnalia.ossmeter.model;

public class EclipseProject {
	
	public final String name;
	public final String url;
	public final int activeCommiters;
	public final int activeOrganizations;
	
	public EclipseProject(String name, String url, int activeCommiters,int activeOrganizations){
		this.name=name;
		this.url=url;
		this.activeCommiters=activeCommiters;
		this.activeOrganizations=activeOrganizations;
	}
	

}
