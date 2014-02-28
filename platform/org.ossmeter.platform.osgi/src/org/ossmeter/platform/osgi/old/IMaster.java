package org.ossmeter.platform.osgi.old;


public interface IMaster {
	public void receiveReport(IWorker worker, Report report);
}
