package org.ossmeter.metricprovider.downloadcounter.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class DownloadCounter extends PongoDB {
	
	public DownloadCounter() {}
	
	public DownloadCounter(DB db) {
		setDb(db);
	}
	
	protected DownloadCollection downloads = null;
	
	
	
	public DownloadCollection getDownloads() {
		return downloads;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		downloads = new DownloadCollection(db.getCollection("downloads"));
		pongoCollections.add(downloads);
	}
}