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
package org.ossmeter.repository.model;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = VcsRepository.class, name="org.ossmeter.repository.model.VcsRepository"), 	@Type(value = org.ossmeter.repository.model.vcs.cvs.CvsRepository.class, name="org.ossmeter.repository.model.vcs.cvs.CvsRepository"),
	@Type(value = org.ossmeter.repository.model.vcs.git.GitRepository.class, name="org.ossmeter.repository.model.vcs.git.GitRepository"),
	@Type(value = org.ossmeter.repository.model.vcs.svn.SvnRepository.class, name="org.ossmeter.repository.model.vcs.svn.SvnRepository"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class VcsRepository extends NamedElement {

	protected List<Person> persons;
	protected String created_at;
	protected String updated_at;
	protected String url;
	
	public String getCreated_at() {
		return created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public String getUrl() {
		return url;
	}
	
	public List<Person> getPersons() {
		return persons;
	}
}
