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
package org.ossmeter.platform.client.api.cache;

import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DBMaker;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Provides two caches for sparks:
 * 	1. Caches the image file
 * 	2. Caches the JSON (which references the first cache)
 * 
 * @author jimmy
 *
 */
public class SparkCache {
	
	private static SparkCache cache;
	public static SparkCache getSparkCache() {
		if (cache == null) {
			cache = new SparkCache();
		}
		return cache;
	}

	protected ConcurrentNavigableMap<String, byte[]> sparkMap;
	protected ConcurrentNavigableMap<String, String> dataMap;
	
	private SparkCache() {
		sparkMap = DBMaker.newTempTreeMap();
		dataMap = DBMaker.newTempTreeMap();
	}

	public synchronized byte[] getSpark(String sparkId) {
		return sparkMap.get(sparkId);
	}
	
	public synchronized void putSpark(String sparkId, byte[] img) {
		sparkMap.put(sparkId, img);
	}
	
	public synchronized String getSparkData(String query) {
		return dataMap.get(query);
	}
	
	public synchronized void putSparkData(String query, JsonNode obj) {
		dataMap.put(query, obj.toString());
	}
}
