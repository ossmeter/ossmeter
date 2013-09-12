package org.ossmeter.platform.client.api;


public class Util {
	public static String generateErrorMessage(String request, String message) {
		return  "{\"request\" : " + request + ", " +
				"\"status\" : \"error\"," +
				"\"message\" : \"" + escape(message) + "\"}";
	}
	
	public static String escape(String msg) {
		System.err.println(msg);
		msg.replaceAll("\"", "\\\"");
		return msg;
	}
}
