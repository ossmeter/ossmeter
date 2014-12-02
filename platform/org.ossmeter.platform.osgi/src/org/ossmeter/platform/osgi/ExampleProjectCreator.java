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
package org.ossmeter.platform.osgi;

import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.SchedulingInformation;

import com.mongodb.Mongo;

//FIXME: Move to its own project.
public class ExampleProjectCreator {

	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		Platform platform = new Platform(mongo);
		
		Project project = new Project();
		project.setName("Foo");
		project.setShortName("Foo");
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		
		project = new Project();
		project.setName("Bar");
		project.setShortName("Bar");
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		
		project = new Project();
		project.setName("Whizz");
		project.setShortName("Whizz");
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		
		project = new Project();
		project.setName("Baz");
		project.setShortName("Baz");
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		
		project = new Project();
		project.setName("Boop");
		project.setShortName("Boop");
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);

		platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().add(new SchedulingInformation());
		
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
		mongo.close();
	}
}
