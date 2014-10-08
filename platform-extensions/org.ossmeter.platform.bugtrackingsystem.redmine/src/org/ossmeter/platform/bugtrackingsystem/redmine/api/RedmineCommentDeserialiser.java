package org.ossmeter.platform.bugtrackingsystem.redmine.api;

import java.io.IOException;

import org.ossmeter.jackson.ExtendedJsonDeserialiser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

class RedmineCommentDeserialiser extends
		ExtendedJsonDeserialiser<RedmineComment> {

	@Override
	public RedmineComment deserialize(JsonParser parser,
			DeserializationContext context) throws IOException,
			JsonProcessingException {

		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);

		RedmineComment comment = new RedmineComment();
		comment.setCommentId(getText(node, "id"));
		comment.setText(getText(node, "notes"));
		comment.setCreationTime(getDate(node, context, "created_on"));
		comment.setCreator(getText(node, "user", "name"));
		comment.setCreatorId(getInteger(node, "user", "id") );

		JsonNode detailsNode = node.get("details");
		if (null != detailsNode) {
			RedmineCommentDetails[] details = oc.treeToValue(detailsNode,
					RedmineCommentDetails[].class);
			for (RedmineCommentDetails d : details) {
				comment.getDetails().add(d);
			}
		}

		return comment;
	}

}
