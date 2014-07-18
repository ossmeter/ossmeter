package org.ossmeter.platform.bugtrackingsystem.bitbucket.api;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class BitbucketSession {
	// https://confluence.atlassian.com/display/BITBUCKET/Using+the+Bitbucket+REST+APIs
	private static final String BITBUCKET_API_URL = "https://bitbucket.org/api/1.0";

	// 2013-05-30 10:39:21+00:00

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern("YYYY-MM-dd HH:mm:ssZ");

	private Client client;
	private WebResource webResource;

	public BitbucketSession() {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
				Boolean.TRUE);
		client = Client.create(clientConfig);
		webResource = client.resource(BITBUCKET_API_URL);
	}

	public BitbucketRepository getRepository(String user, String name) {
		return webResource.path("repositories").path(user).path(name)
				.accept("application/json").get(BitbucketRepository.class);
	}

	public BitbucketIssues getIssues(BitbucketIssueQuery query)
			throws BitbucketException {
		enforceMaximumLimitValue(query.getLimit());

		String limit = Integer.toString(query.getLimit());
		String start = Integer.toString(query.getStart());

		WebResource wr = webResource.path("repositories").path(query.getUser())
				.path(query.getRepository()).path("issues")
				.queryParam("limit", limit).queryParam("start", start);

		if (null != query.getSearch()) {
			wr = wr.queryParam("search", query.getSearch());
		}

		for (String status : query.getStatuses()) {
			wr = wr.queryParam("status", status);
		}

		for (String priority : query.getPriorities()) {
			wr = wr.queryParam("priority", priority);
		}

		if (null != query.getSince()) {
			wr = wr.queryParam("sort", "-utc_last_updated");
		}

		BitbucketIssues issues = wr.accept("application/json").get(
				BitbucketIssues.class);

		if (null != query.getSince()) {
			List<BitbucketIssue> filteredIssues = new ArrayList<BitbucketIssue>();
			for (BitbucketIssue issue : issues.getIssues()) {
				DateTime issueUpdated = DateTime.parse(
						issue.getUtc_last_updated(), dateTimeFormatter);
				if (issueUpdated.isAfter(query.getSince())) {
					filteredIssues.add(issue);
				}
			}

			issues = new BitbucketIssues(issues.getFilter(),
					issues.getSearch(), filteredIssues);
		}

		return issues;
	}

	public List<BitbucketIssueComment> getIssueComments(
			BitbucketIssueQuery query) throws BitbucketException {
		List<BitbucketIssueComment> comments = new ArrayList<BitbucketIssueComment>();

		String user = query.getUser();
		String repository = query.getRepository();

		BitbucketIssueQuery q = query.shallowCopy().setLimit(50);
		BitbucketIssues issues;
		int page=0;
		
		do {
			q.setPage(page);
			issues = getIssues(q);
			for (BitbucketIssue issue : issues) {
				List<BitbucketIssueComment> issueComments = getIssueComments(
						user, repository, issue.getLocal_id());
				for (BitbucketIssueComment ic : issueComments) {
					DateTime issueUpdated = DateTime.parse(
							ic.getUtc_updated_on(), dateTimeFormatter);
					if (issueUpdated.isAfter(query.getSince())) {
						ic.setIssue(issue);
						comments.add(ic);
					}
				}
			}
			page++;
		} while (!issues.getIssues().isEmpty());

		return comments;
	}

	public BitbucketIssue getIssue(String user, String repository, int id) {
		String idString = Integer.toString(id);

		return webResource.path("repositories").path(user).path(repository)
				.path("issues").path(idString).get(BitbucketIssue.class);
	}

	public List<BitbucketIssueComment> getIssueComments(String user,
			String repository, int id) {
		String idString = Integer.toString(id);
		List<BitbucketIssueComment> comments = webResource.path("repositories")
				.path(user).path(repository).path("issues").path(idString)
				.path("comments")
				.get(new GenericType<List<BitbucketIssueComment>>() {
				});
		return comments;
	}

	private void enforceMaximumLimitValue(int limit) throws BitbucketException {
		if (limit > 50) {
			throw new BitbucketException(
					"The maximum value allowed for limit is 50");
		}
	}

	public static void main(String[] args) throws BitbucketException {
		BitbucketSession bitbucket = new BitbucketSession();
		BitbucketIssueQuery query;
		BitbucketIssues issues;

		// Get the issues updated in the past couple of months
		query = new BitbucketIssueQuery("jmurty", "jets3t").setSince(
				new DateTime().minusMonths(2)).setLimit(50);
		issues = bitbucket.getIssues(query);
		System.out.println(issues);		
		
		// Get the issue comments updated in the past couple of months
		query = new BitbucketIssueQuery("jmurty", "jets3t").setSince(
				new DateTime().minusMonths(1)).setLimit(50);
		List<BitbucketIssueComment> comments = bitbucket.getIssueComments(query);
		for (BitbucketIssueComment comment: comments) {
			System.out.println( comment.getIssue().getLocal_id() );
		}
		

		// Get information about a repository. A repository is defined by a name
		// and its owner
		BitbucketRepository repo = bitbucket.getRepository("nactemdev", "test");
		System.out.println(repo);

		// Search for issues in project 'jets3t' owned by 'jmurty' using keywod
		// 'folder'
		// NOTE: will return default 15 results at most. See paged example below
		// on how to get all.
		query = new BitbucketIssueQuery("jmurty", "jets3t").setSearch("folder");
		issues = bitbucket.getIssues(query);
		System.out.println(issues);

		// Search for issues in project 'jets3t' owned by 'jmurty' with the
		// status 'open'
		// NOTE: will return default 15 results at most. See paged example below
		// on how to get all.
		query = new BitbucketIssueQuery("jmurty", "jets3t")
				.addStatus(Bitbucket.STATUS_OPEN);
		issues = bitbucket.getIssues(query);
		System.out.println(issues);

		// Paged example. Search for issues with minor priority. This is how you
		// should get issues, as you will
		// always get pages of results.
		query = new BitbucketIssueQuery("jmurty", "jets3t").addPriority(
				Bitbucket.PRIORITY_MINOR).setLimit(50);
		int page = 0;
		do {
			query.setPage(page);
			issues = bitbucket.getIssues(query);
			System.out.println(issues);
			page++;
		} while (!issues.getIssues().isEmpty());

		// Get information about a single issue
		BitbucketIssue issue = bitbucket.getIssue("jmurty", "jets3t", 9);
		System.out.println(issue);

		// Get the comments associated with an issue
		comments = bitbucket.getIssueComments(
				"jmurty", "jets3t", 9);
		for (BitbucketIssueComment comment : comments) {
			System.out.println("---Comment---");
			System.out.println(comment);
			System.out.println("-------------\n");

		}

	}
}
