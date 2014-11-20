package org.ossmeter.platform.cache.communicationchannel;
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DBMaker;
import org.mapdb.Fun;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;

public class CommunicationChannelDeltaCache implements ICommunicationChannelDeltaCache {
	
	protected ConcurrentNavigableMap<Fun.Tuple2<String, String>, CommunicationChannelDelta> map;
	
	public CommunicationChannelDeltaCache() {
		initialiseDB();
	}
	
	protected void initialiseDB() {
		 map = DBMaker.newTempTreeMap(); // TODO: Do we want to allocate local storage for this?
	}
	
	public CommunicationChannelDelta getCachedDelta(String communicationChannelUrl, Date date){
		return map.get(Fun.t2(communicationChannelUrl, date.toString()));
	}
	
	// TODO: This needs to be bounded
	synchronized public void putDelta(String communicationChannelUrl, Date date, CommunicationChannelDelta delta) {
		map.put(Fun.t2(communicationChannelUrl, date.toString()), delta);
	}
	
}
