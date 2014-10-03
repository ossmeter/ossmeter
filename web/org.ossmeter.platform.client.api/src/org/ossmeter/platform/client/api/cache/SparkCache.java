package org.ossmeter.platform.client.api.cache;

import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DBMaker;

import com.fasterxml.jackson.databind.node.ObjectNode;

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
	protected ConcurrentNavigableMap<String, ObjectNode> dataMap;
	
	private SparkCache() {
		sparkMap = DBMaker.newTempTreeMap();
		dataMap = DBMaker.newTempTreeMap();
	}

	public byte[] getSpark(String sparkId) {
		return sparkMap.get(sparkId);
	}
	
	public void putSpark(String sparkId, byte[] img) {
		sparkMap.put(sparkId, img);
	}
	
	public ObjectNode getSparkData(String query) {
		return dataMap.get(query);
	}
	
	public void putSparkData(String query, ObjectNode obj) {
		dataMap.put(query, obj);
	}
}
