package org.ossmeter.platform.cache.communicationchannel;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.ossmeter.repository.model.CommunicationChannel;


public interface ICommunicationChannelDeltaCache {
	
	public CommunicationChannelDelta getCachedDelta(String communicationChannelUrl, Date date);
	
	public void putDelta(String communicationChannelUrl, Date date, CommunicationChannelDelta delta);
}
