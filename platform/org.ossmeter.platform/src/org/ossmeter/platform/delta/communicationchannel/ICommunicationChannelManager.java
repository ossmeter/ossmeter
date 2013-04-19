package org.ossmeter.platform.delta.communicationchannel;

import org.ossmeter.platform.Date;
import org.ossmeter.repository.model.CommunicationChannel;

public interface ICommunicationChannelManager<T extends CommunicationChannel> {
	
	public boolean appliesTo(T communicationChannel);
	
	public CommunicationChannelDelta getDelta(T communicationChannel, Date date) throws Exception;
	
	public String getContents(T communicationChannel, CommunicationChannelArticle article) throws Exception;
	
	public Date getFirstDate(T communicationChannel) throws Exception;
	
}
