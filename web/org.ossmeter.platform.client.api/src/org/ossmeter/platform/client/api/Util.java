package org.ossmeter.platform.client.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class Util {
	public static JsonNode generateErrorMessage(JsonNode request, String message) {
		ObjectMapper mapper = new ObjectMapper(); // TODO: Shall we have a single mapper for all?
		ObjectNode m = mapper.createObjectNode();
		
		m.put("request", request);
		m.put("status", "error");
		m.put("message", escape(message));
		
		return m;
	}
	
	public static String escape(String msg) {
		System.err.println(msg);
		msg.replaceAll("\"", "\\\"");
		return msg;
	}
}
