/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jurgen Vinju - Implementation.
 *******************************************************************************/
package org.ossmeter.metricprovider.rascal.trans.model;

import java.util.List;

import com.googlecode.pongo.runtime.PongoList;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;
import com.mongodb.BasicDBList;


public class SetMeasurement extends Measurement {
	
	protected List<Measurement> value = null;
	
	
	public SetMeasurement() { 
		super();
		dbObject.put("value", new BasicDBList());
		super.setSuperTypes("org.ossmeter.metricprovider.rascal.trans.model.Measurement");
		URI.setOwningType("org.ossmeter.metricprovider.rascal.trans.model.SetMeasurement");
	}
	
	public static StringQueryProducer URI = new StringQueryProducer("uri"); 
	
	
	
	
	public List<Measurement> getValue() {
		if (value == null) {
			value = new PongoList<Measurement>(this, "value", true);
		}
		return value;
	}
	
	
}