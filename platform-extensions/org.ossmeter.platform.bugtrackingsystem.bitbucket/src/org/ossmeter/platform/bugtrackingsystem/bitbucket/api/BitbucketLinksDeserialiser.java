package org.ossmeter.platform.bugtrackingsystem.bitbucket.api;

import java.io.IOException;
import java.util.Iterator;

import org.ossmeter.jackson.ExtendedJsonDeserialiser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

public class BitbucketLinksDeserialiser extends ExtendedJsonDeserialiser<BitbucketLinks>{

	@Override
	public BitbucketLinks deserialize(JsonParser parser,
			DeserializationContext context) throws IOException,
			JsonProcessingException {
		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);
		
		BitbucketLinks links = new BitbucketLinks();
		
		Iterator<String> it = node.fieldNames();
		while (it.hasNext()) {
			String key = it.next();
			JsonNode keyNode = node.get(key);
			JsonNode valueNode = keyNode.get("href");
			if (null != valueNode) {
				String value = valueNode.asText();
				if (null != value) {
					links.getValues().put(key, value);
				}

			}
		}
		
		return links;
	}

}
