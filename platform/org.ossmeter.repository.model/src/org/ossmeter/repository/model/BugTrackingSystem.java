/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Davide Di Ruscio - Implementation,
 *    Juri Di Rocco - Implementation.
 *******************************************************************************/
package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;

// protected region custom-imports on begin
// protected region custom-imports end

public abstract class BugTrackingSystem extends Pongo {
	
	protected List<Person> persons = null;
	
	// protected region custom-fields-and-methods on begin
    /**
     * @return a string unique to each subclass of BugTrackingSystem (e.g.
     *         "bugzilla", "github")
     */
    public abstract String getBugTrackerType();

    /**
     * @return a string uniquely identifying an instance of a BugTrackingSystem
     *         (e.g. for GitHub, this could be 'A_GIT_USER/A_REPOSITORY_NAME')
     */
    public abstract String getInstanceId();

    @Override
    protected void notifyChanged() {
        dbObject.put("_ossmeterId", getInstanceId() + ':' + getBugTrackerType());

        super.notifyChanged();
    }

    /**
     * @return a string uniquely identifying a BugTrackingSystem instance within
     *         the OSSMeter environment
     */
    public String getOSSMeterId() {
        return parseString(dbObject.get("_ossmeterId") + "", null);
    }

    // protected region custom-fields-and-methods end
	
	public BugTrackingSystem() { 
		super();
		dbObject.put("persons", new BasicDBList());
		URL.setOwningType("org.ossmeter.repository.model.BugTrackingSystem");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public BugTrackingSystem setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	
	
	public List<Person> getPersons() {
		if (persons == null) {
			persons = new PongoList<Person>(this, "persons", false);
		}
		return persons;
	}
	
	
}