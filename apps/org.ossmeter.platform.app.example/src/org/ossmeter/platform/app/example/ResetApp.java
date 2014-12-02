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
package org.ossmeter.platform.app.example;

import com.mongodb.Mongo;

public class ResetApp {
	
	public static void main(String[] args) throws Exception {
		
		Mongo mongo = new Mongo();
		mongo.dropDatabase("ossmeter");
		mongo.dropDatabase("pdb_values");
		mongo.dropDatabase("pongo");
		mongo.dropDatabase("fedora");
		mongo.dropDatabase("epsilon");
		mongo.dropDatabase("Epsilon");
		mongo.dropDatabase("hamcrest");
		mongo.dropDatabase("jMonkeyEngine");
		mongo.dropDatabase("thunderbird");
		mongo.dropDatabase("fedora");
		mongo.dropDatabase("saf");
		mongo.dropDatabase("mojambo-grit");
		mongo.dropDatabase("emf");
		mongo.dropDatabase("xText");
		mongo.dropDatabase("toolsEmf");
		mongo.dropDatabase("eclipsePlatform");
		mongo.dropDatabase("pdb_values");
		mongo.dropDatabase("pongo");
		mongo.dropDatabase("modelinggmt");
		mongo.dropDatabase("eclipse");
		mongo.dropDatabase("modelinggmtamw");
		mongo.dropDatabase("Log4J");
		mongo.dropDatabase("BIRT");
		mongo.dropDatabase("SiriusForum");
	}
	
}
