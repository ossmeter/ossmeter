/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 	Davide Di Ruscio- Implementation.
 *******************************************************************************/
package org.ossmeter.metricprovider.trans.importer.googlecode;

import java.util.List;

import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectExecutionInformation;
import org.ossmeter.repository.model.googlecode.GoogleCodeProject;
import org.ossmeter.repository.model.googlecode.importer.GoogleCodeImporter;
import org.ossmeter.repository.model.importer.exception.WrongUrlException;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class GoogleCodeImporterProvider implements ITransientMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.trans.googlecodeimporter";
	
	protected MetricProviderContext context;
	
	/**
	 * List of MPs that are used by this MP. These are MPs who have specified that 
	 * they 'provide' data for this MP.
	 */
	protected List<IMetricProvider> uses;
	
	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return IDENTIFIER;
	}

	@Override
	public String getShortIdentifier() {
		// TODO Auto-generated method stub
		return "googlecodeimporter";
	}

	@Override
	public String getFriendlyName() {
		// TODO Auto-generated method stub
		return "GoogleCode importer";
	}

	@Override
	public String getSummaryInformation() {
		// TODO Auto-generated method stub
		return "This provider enable to update a projects calling a importProject from google code importer";
	}

	@Override
	public boolean appliesTo(Project project) {
		return (project instanceof GoogleCodeProject) ? true : false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;

	}

	@Override
	public List<String> getIdentifiersOfUses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public PongoDB adapt(DB db) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void measure(Project project, ProjectDelta delta, PongoDB db) {
		GoogleCodeProject ep = null;
		
		GoogleCodeImporter epi = new GoogleCodeImporter();
		Platform platform = Platform.getInstance();
		try
		{
			ep = epi.importProject(project.getShortName(), platform);
			if (ep == null)
			{
				if(project.getExecutionInformation() == null)
					project.setExecutionInformation(new ProjectExecutionInformation());
				project.getExecutionInformation().setInErrorState(false);
			}
			
		}catch (WrongUrlException e){
			if(project.getExecutionInformation() == null)
				project.setExecutionInformation(new ProjectExecutionInformation());
			project.getExecutionInformation().setInErrorState(false);
		}
			
		
		
	}
}
