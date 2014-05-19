package org.ossmeter.metricprovider.historic.totaldownloadcounter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class TotalDownloadCounter extends Pongo {
	
	
	
	public TotalDownloadCounter() { 
		super();
	}
	
	public int getDownloads() {
		return parseInteger(dbObject.get("downloads")+"", 0);
	}
	
	public TotalDownloadCounter setDownloads(int downloads) {
		dbObject.put("downloads", downloads + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}