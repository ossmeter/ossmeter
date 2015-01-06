/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.ossmeter.platform.client.api;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.UUID;

import org.ossmeter.platform.Configuration;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.ProjectRepositoryManager;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.VcsRepository;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;
import org.ossmeter.repository.model.vcs.git.GitRepository;
import org.ossmeter.repository.model.vcs.svn.SvnRepository;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.Mongo;

public class ProjectCreationResource extends ServerResource {
	
	public static void main(String[] args) throws Exception {
		String j = "{\"name\":\"hi\",\"homepage\":\"hi\",\"description\":\"hi\",\"vcs\":[{\"name\":\"hi\",\"url\":\"hi\",\"type\":\"git\"}],\"bts\":[{\"product\":\"hi\",\"url\":\"hi\",\"component\":\"hi\"}],\"communication_channels\":[{\"name\":\"hi\",\"url\":\"hi\",\"newsgroup\":\"hi\"}]}";

		ObjectMapper mapper = new ObjectMapper();
		JsonNode json = mapper.readTree(j);
		
		Project project = new Project();
		project.setName(json.get("name").asText());
		project.setHomePage(json.get("homepage").asText());
		project.setDescription(json.get("description").asText());
		
		for (JsonNode vcs : (ArrayNode)json.get("vcs")) {
			VcsRepository repo = null;
			if (vcs.get("type").asText().equals("git")) {
				repo = new SvnRepository();
			} else if (vcs.get("type").asText().equals("svn")) {
				repo = new GitRepository();
			}
			repo.setName(vcs.get("name").asText());
			repo.setUrl(vcs.get("url").asText());
			project.getVcsRepositories().add(repo);
		}
		for (JsonNode cc : (ArrayNode)json.get("communication_channels")) {
			NntpNewsGroup newsgroup = new NntpNewsGroup();
			newsgroup.setName(cc.get("name").asText());
			newsgroup.setUrl(cc.get("url").asText());
			project.getCommunicationChannels().add(newsgroup);
		}
		for (JsonNode bts : (ArrayNode)json.get("bts")) {
			Bugzilla bugs = new Bugzilla();
			bugs.setProduct(bts.get("product").asText());
			bugs.setUrl(bts.get("url").asText());
			bugs.setComponent(bts.get("component").asText());
			project.getBugTrackingSystems().add(bugs);
		}
	}

	@Post
	public Representation createProject(Representation entity) {
		Mongo mongo = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			// WARNING: This is a _DESTRUCTIVE_ read. It took me AGES to realise this and it isn't in the Restlet javadoc. I hate you Restlet.
			String j = entity.getText(); 
			
			JsonNode json = mapper.readTree(j);
			
			// Translate into a Project object. FIXME
			Project project = new Project();
			project.setName(json.get("name").asText());
			project.setHomePage(json.get("homepage").asText());
			project.setDescription(json.get("description").asText());
			
			for (JsonNode vcs : (ArrayNode)json.get("vcs")) {
				VcsRepository repo = null;
				if (vcs.get("type").asText().equals("git")) {
					repo = new SvnRepository();
				} else if (vcs.get("type").asText().equals("svn")) {
					repo = new GitRepository();
				}
				repo.setName(vcs.get("name").asText());
				repo.setUrl(vcs.get("url").asText());
				project.getVcsRepositories().add(repo);
			}
			for (JsonNode cc : (ArrayNode)json.get("communication_channels")) {
				NntpNewsGroup newsgroup = new NntpNewsGroup();
				newsgroup.setName(cc.get("name").asText());
				newsgroup.setUrl(cc.get("url").asText());
				project.getCommunicationChannels().add(newsgroup);
			}
			for (JsonNode bts : (ArrayNode)json.get("bts")) {
				Bugzilla bugs = new Bugzilla();
				bugs.setProduct(bts.get("product").asText());
				bugs.setUrl(bts.get("url").asText());
				bugs.setComponent(bts.get("component").asText());
				project.getBugTrackingSystems().add(bugs);
			}	// TODO: Validate all channels.

			// TODO: Generate shortName
			project.setShortName(ProjectRepositoryManager.generateUniqueId(project));
			
			try {
				mongo = Configuration.getInstance().getMongoConnection();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
				getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
				return Util.generateErrorMessageRepresentation(generateRequestJson(mapper, null), "The API was unable to connect to the database.");
			}
			Platform platform = new Platform(mongo);
			
			// TODO: Check it doesn't already exist
			System.out.println("Adding new project");
			platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
			platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();
			
			StringRepresentation rep = new StringRepresentation("{'status':'success'}");
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.SUCCESS_CREATED);
			return rep;

		} catch (IOException e) {
			e.printStackTrace(); // TODO
			StringRepresentation rep = new StringRepresentation("");
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return rep;
		} finally {
			if (mongo != null) mongo.close();
		}
	}
	
	@JsonFilter("foofilter")
	class Foo {}
	
	protected JsonNode generateRequestJson(ObjectMapper mapper, String projectName) {
		ObjectNode n = mapper.createObjectNode();
		n.put("project", projectName);
		return n;
	}
}
