package org.ossmeter.platform.vcs.workingcopy.manager;

public class Churn {
	private final String churnItemPath;
	private final int linesAdded;
	private final int linesDeleted;
	
	public Churn(String itemPath, int linesAdded, int linesDeleted) {
		churnItemPath = itemPath;
		this.linesAdded = linesAdded;
		this.linesDeleted = linesDeleted;
	}
	
	public String getPath() {
		return churnItemPath;
	}
	
	public int getLinesAdded() {
		return linesAdded;
	}
	
	public int getLinesDeleted() {
		return linesDeleted;
	}
}
