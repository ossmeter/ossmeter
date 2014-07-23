package org.ossmeter.platform.bugtrackingsystem.bitbucket.api;

import java.io.IOException;

import org.ossmeter.jackson.ExtendedJsonDeserialiser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

public class BitbucketPullRequestRepositoryDeserialiser extends ExtendedJsonDeserialiser<BitbucketPullRequestRepository>{

	@Override
	public BitbucketPullRequestRepository deserialize(JsonParser parser,
			DeserializationContext context) throws IOException,
			JsonProcessingException {
		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);

		BitbucketPullRequestRepository repo = new BitbucketPullRequestRepository();
		
		repo.setBranchName(getText(node, "branch","name"));
		repo.setCommitHash(getText(node, "commit", "hash"));
		repo.setRepositoryFullName(getText(node, "repository", "full_name"));
		repo.setRepositoryName(getText(node, "repository", "name"));
		
		
		return repo;
	}

}
