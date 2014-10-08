package org.ossmeter.platform.bugtrackingsystem.jira.api;

import java.io.IOException;

import org.ossmeter.jackson.ExtendedJsonDeserialiser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

public class JiraCommentDeserialiser extends ExtendedJsonDeserialiser<JiraComment> {

    @Override
    public JiraComment deserialize(JsonParser parser,
            DeserializationContext context) throws IOException,
            JsonProcessingException {
        
        
        ObjectCodec oc = parser.getCodec();
        JsonNode node = oc.readTree(parser);

        JiraComment comment = new JiraComment();

        comment.setCommentId(getText(node, "id"));
        comment.setCreationTime(getDate(node, context, "created"));
        comment.setCreator(getText(node, "author", "name"));
        comment.setText(getText(node, "body"));
        comment.setUpdateAuthor(getText(node, "updateAuthor", "name"));
        comment.setUpdateDate(getDate(node, context, "updated"));
        comment.setUrl(getText(node, "self"));

        return comment;

    }

}
