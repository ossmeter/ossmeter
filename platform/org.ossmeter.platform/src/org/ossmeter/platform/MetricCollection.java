package org.ossmeter.platform;

import java.util.Iterator;

import com.googlecode.pongo.runtime.PongoCollection;
import com.mongodb.DBCollection;

public class MetricCollection<T extends Object>  extends PongoCollection{

	public MetricCollection(DBCollection dbCollection) {
		super(dbCollection);
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
