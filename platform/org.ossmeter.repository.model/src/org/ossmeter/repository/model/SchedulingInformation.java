package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class SchedulingInformation extends Pongo {
	
	protected List<WorkerNode> nodes = null;
	
	
	public SchedulingInformation() { 
		super();
		dbObject.put("nodes", new BasicDBList());
		MASTERNODEIDENTIFIER.setOwningType("org.ossmeter.repository.model.SchedulingInformation");
	}
	
	public static StringQueryProducer MASTERNODEIDENTIFIER = new StringQueryProducer("masterNodeIdentifier"); 
	
	
	public String getMasterNodeIdentifier() {
		return parseString(dbObject.get("masterNodeIdentifier")+"", "");
	}
	
	public SchedulingInformation setMasterNodeIdentifier(String masterNodeIdentifier) {
		dbObject.put("masterNodeIdentifier", masterNodeIdentifier);
		notifyChanged();
		return this;
	}
	
	
	public List<WorkerNode> getNodes() {
		if (nodes == null) {
			nodes = new PongoList<WorkerNode>(this, "nodes", true);
		}
		return nodes;
	}
	
	
}