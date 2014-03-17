package org.ossmeter.platform.delta;

public class NoManagerFoundException extends Exception {

	protected String message;
	
	public NoManagerFoundException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
