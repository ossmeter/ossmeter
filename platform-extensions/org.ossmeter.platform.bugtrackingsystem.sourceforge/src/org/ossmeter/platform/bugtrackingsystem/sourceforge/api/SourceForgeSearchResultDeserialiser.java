package org.ossmeter.platform.bugtrackingsystem.sourceforge.api;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class SourceForgeSearchResultDeserialiser extends
        JsonDeserializer<SourceForgeSearchResult> {

    @Override
    public SourceForgeSearchResult deserialize(JsonParser parser,
            DeserializationContext context) throws IOException,
            JsonProcessingException {
        ObjectCodec oc = parser.getCodec();
        JsonNode node = oc.readTree(parser);

        SourceForgeSearchResult result = new SourceForgeSearchResult();

        result.setCount(node.get("count").asInt());

        Iterator<JsonNode> tickets = node.path("tickets").iterator();
        while (tickets.hasNext()) {
            JsonNode ticket = tickets.next();
            result.addTicketId(ticket.get("ticket_num").asInt());
        }

        return result;
    }

}
