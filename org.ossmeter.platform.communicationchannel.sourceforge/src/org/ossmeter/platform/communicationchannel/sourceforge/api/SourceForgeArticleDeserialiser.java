/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.ossmeter.platform.communicationchannel.sourceforge.api;

import java.io.IOException;

import org.ossmeter.jackson.ExtendedJsonDeserialiser;
import org.ossmeter.platform.communicationchannel.sourceforge.api.SourceForgeAttachment;
import org.ossmeter.platform.communicationchannel.sourceforge.api.SourceForgeConstants;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

public class SourceForgeArticleDeserialiser extends ExtendedJsonDeserialiser<SourceForgeArticle>{

	static int articleNumber = 1;
	
    @Override
    public SourceForgeArticle deserialize(JsonParser parser,
            DeserializationContext context) throws IOException,
            JsonProcessingException {

        ObjectCodec oc = parser.getCodec();
        JsonNode node = oc.readTree(parser);
        
        JsonNode attachmentsNode = node.path("attachments");
        SourceForgeAttachment[] attachments = oc.treeToValue(attachmentsNode, SourceForgeAttachment[].class);
        
        SourceForgeArticle comment = new SourceForgeArticle();
        comment.setArticleNumber(articleNumber++);
        comment.setSubject(getText(node, "subject"));
        comment.setText(getText(node, "text"));
        comment.setUser(getText(node,"author"));
        comment.setAttachments(attachments);
        comment.setArticleId(getText(node, "slug"));
        comment.setDate(getDate(node, SourceForgeConstants.RESPONSE_DATE_FORMATTER, "timestamp"));
        comment.setUpdateDate(getDate(node, SourceForgeConstants.RESPONSE_DATE_FORMATTER, "last_edited"));
        
        return comment;
    }

}
