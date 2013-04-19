package org.ossmeter.platform.cache.communicationchannel;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelArticle;

public class CommunicationChannelContentsCache implements ICommunicationChannelContentsCache {
	
	protected HTreeMap<CommunicationChannelArticle, String> map;
	
	public CommunicationChannelContentsCache() {
		initialiseDB();
	}
	
	protected void initialiseDB() {
		 map = DBMaker.newTempHashMap(); // TODO: Do we want to allocate local storage for this?
	}
	
	@Override
	public String getCachedContents(CommunicationChannelArticle article){
		return map.get(article);
	}
	
	// TODO: This needs to be bounded
	@Override
	public void putContents(CommunicationChannelArticle article, String contents) {
		map.put(article, contents);
	}
}
