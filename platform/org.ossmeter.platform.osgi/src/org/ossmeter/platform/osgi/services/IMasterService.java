package org.ossmeter.platform.osgi.services;


public interface IMasterService {

	public void start() throws Exception;
	
	public void pause();
	
	public void shutdown();

	public void resume();
	
}