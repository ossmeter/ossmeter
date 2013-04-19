package org.ossmeter.platform.cache.vcs;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.ossmeter.platform.delta.vcs.VcsCommitItem;

public class VcsContentsCache implements IVcsContentsCache {
	
	protected HTreeMap<VcsCommitItem, String> map;
	
	public VcsContentsCache() {
		initialiseDB();
	}
	
	protected void initialiseDB() {
		 map = DBMaker.newTempHashMap(); // TODO: Do we want to allocate local storage for this?
	}
	
	@Override
	public String getCachedContents(VcsCommitItem item){
		return map.get(item);
	}
	
	// TODO: This needs to be bounded
	@Override
	public void putContents(VcsCommitItem item, String contents) {
		map.put(item, contents);
	}
}
