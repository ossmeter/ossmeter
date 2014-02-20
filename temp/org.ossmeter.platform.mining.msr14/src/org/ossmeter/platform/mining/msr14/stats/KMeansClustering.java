package org.ossmeter.platform.mining.msr14.stats;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.ossmeter.platform.mining.msr14.model.Biodiversity;
import org.ossmeter.platform.mining.msr14.model.User;

import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class KMeansClustering {
	public static void main(String[] args) {
		try {
			Mongo mongo = new Mongo(new ServerAddress("localhost", 12345));
			Biodiversity bio = new Biodiversity(mongo.getDB("biodiversity_1"));
			bio.setClearPongoCacheOnSync(true);
			
			List<User> users = new ArrayList<User>(); // Massively inefficient
			int usss =0;
			for (User u : bio.getUsers()) {
				if (u.getCommitAdditions()==0 && u.getCommitCount()==0 && u.getCommitDeletions()==0
						&& u.getNumberOfCommitComments()==0 && u.getNumberOfIssues()==0 && u.getNumberOfIssueComments()==0
						&& u.getNumberOfPullRequests()==0 && u.getNumberOfPullRequestComments()==0){
					continue;
				}

				users.add(u);
				usss++;
							
				if (usss > 5000) break;
			}
			
			KMeansClustering kmeans = new KMeansClustering();
			HashMap<Centroid, List<User>> clusters = kmeans.compute(4, users);
			
			FileWriter writer = new FileWriter(new File("groups.csv"));
			writer.write("group,numberOfCommits,numberOfChanges,numberOfAdditions,numberOfDeletions,"
					+ "numberOfCommitsAsAuthor,numberOfCommitsAsCommitter,numberOfIssues,numberOfIssueComments,"
					+ "numberOfPullRequests,numberOfPullRequestComments,numberOfCommitComments,numberOfForks+\n");
			int group = 0;
			for (Centroid c : clusters.keySet()) {
				List<User> us = clusters.get(c);
				
				for (User u :us) {
					writer.write(group + ",");
					writer.write(u.getCommitCount()+ ",");
					writer.write(u.getCommitTotalChanges()+ ",");
					writer.write(u.getCommitAdditions()+ ",");
					writer.write(u.getCommitDeletions()+ ",");
					writer.write(u.getCommitsAsAuthor()+ ",");
					writer.write(u.getCommitsAsCommitter()+ ",");
					writer.write(u.getNumberOfIssues()+ ",");
					writer.write(u.getNumberOfIssueComments()+ ",");
					writer.write(u.getNumberOfPullRequests()+ ",");
					writer.write(u.getNumberOfPullRequestComments()+ ",");
					writer.write(u.getNumberOfCommitComments()+ ",");
					writer.write(u.getNumberOfForks()+"\n");
				}
				group++;
				writer.flush();
			}
			writer.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public HashMap<Centroid, List<User>> compute(int k, List<User> users) {
		HashMap<Centroid, List<User>> clusters = null;
		List<Centroid> centroids = createInitialCentroids(k, users);
		
		System.out.println("Initial centroids:");
		for (Centroid c:centroids) System.out.println(c.toString());
		
		int maxIterations = 15;
		while (maxIterations-- > 0) {
			clusters = formClusters(centroids, users);
			
			System.out.println("Centroids:");
			for (Centroid c:clusters.keySet()) {
				System.out.println(c.toString() + " size: " + clusters.get(c).size());
			}

			List<Centroid> newCentroids = recomputeCentroids(clusters);
			
			if (compareCentroids(centroids, newCentroids)){
				break;
			}
			centroids = newCentroids;
		}
		return clusters;
	}
	
	/**
	 * Randomly selects k users to be the initial centroids.
	 * @param k
	 * @param users
	 * @return
	 */
	protected List<Centroid> createInitialCentroids(int k, List<User> users) {
		Random random = new Random();
		List<Centroid> centroids = new ArrayList<Centroid>();
		
		while (centroids.size() < k) {
			User user = users.get(random.nextInt(users.size()));
			Centroid c = new Centroid();
			c.numberOfCommits = user.getCommitCount();
			c.numberOfChanges = user.getCommitTotalChanges();
			c.numberOfAdditions = user.getCommitAdditions();
			c.numberOfDeletions = user.getCommitDeletions();
			c.numberOfCommitsAsAuthor = user.getCommitsAsAuthor();
			c.numberOfCommitsAsCommitter = user.getCommitsAsCommitter();
			c.numberOfIssues = user.getNumberOfIssues();
			c.numberOfIssueComments = user.getNumberOfIssueComments();
			c.numberOfPullRequests = user.getNumberOfPullRequests();
			c.numberOfPullRequestComments = user.getNumberOfPullRequestComments();
			c.numberOfCommitComments = user.getNumberOfCommitComments();
			c.numberOfForks = user.getNumberOfForks();
			centroids.add(c);
		}
		return centroids;
	}
	
	protected HashMap<Centroid, List<User>> formClusters(List<Centroid> centroids, List<User> users) {
		HashMap<Centroid, List<User>> clusters = new HashMap<Centroid, List<User>>();
		
		for (User u : users) {
			double distanceToClosest = Double.MAX_VALUE;
			Centroid closest = null;
			for (Centroid c : centroids) {
				double distance = calculateDistanceToCentroid(u, c);
				if (distance < distanceToClosest) {
					distanceToClosest = distance;
					closest = c;
				}
			}
			
			if (clusters.containsKey(closest)) {
				clusters.get(closest).add(u);
			} else {
				List<User> us = new ArrayList<User>();
				us.add(u);
				clusters.put(closest, us);
			}
		}
		
		return clusters	;
	}
	
	protected List<Centroid> recomputeCentroids(HashMap<Centroid, List<User>> clusters) {
		List<Centroid> newCentroids = new ArrayList<Centroid>();
		
		for(Centroid c : clusters.keySet()) {
			List<User> points = clusters.get(c);
			
			c = new Centroid();
			
			for (User user : points) {
				c.numberOfCommits += user.getCommitCount();
				c.numberOfChanges += user.getCommitTotalChanges();
				c.numberOfAdditions += user.getCommitAdditions();
				c.numberOfDeletions += user.getCommitDeletions();
				c.numberOfCommitsAsAuthor += user.getCommitsAsAuthor();
				c.numberOfCommitsAsCommitter += user.getCommitsAsCommitter();
				c.numberOfIssues += user.getNumberOfIssues();
				c.numberOfIssueComments += user.getNumberOfIssueComments();
				c.numberOfPullRequests += user.getNumberOfPullRequests();
				c.numberOfPullRequestComments += user.getNumberOfPullRequestComments();
				c.numberOfCommitComments += user.getNumberOfCommitComments();
				c.numberOfForks += user.getNumberOfForks();
			}
			
			c.numberOfCommits /= points.size();
			c.numberOfChanges /= points.size();
			c.numberOfAdditions /= points.size();
			c.numberOfDeletions /= points.size();
			c.numberOfCommitsAsAuthor /= points.size();
			c.numberOfCommitsAsCommitter /= points.size();
			c.numberOfIssues /= points.size();
			c.numberOfIssueComments /= points.size();
			c.numberOfPullRequests /= points.size();
			c.numberOfPullRequestComments /= points.size();
			c.numberOfCommitComments /= points.size();
			c.numberOfForks /= points.size();
			
			newCentroids.add(c);
		}
		
		return newCentroids;
	}
	
	protected double calculateDistanceToCentroid(User user, Centroid centroid) {
		double result =  Math.sqrt(
				(centroid.numberOfCommits - user.getCommitCount())^2 +
				(centroid.numberOfChanges - user.getCommitTotalChanges())^2 +
				(centroid.numberOfAdditions - user.getCommitAdditions())^2 +
				(centroid.numberOfDeletions - user.getCommitDeletions())^2 +
				(centroid.numberOfCommitsAsAuthor - user.getCommitsAsAuthor())^2 +
				(centroid.numberOfCommitsAsCommitter - user.getCommitsAsCommitter())^2 +
				(centroid.numberOfIssues - user.getNumberOfIssues())^2 +
				(centroid.numberOfIssueComments - user.getNumberOfIssueComments())^2 +
				(centroid.numberOfPullRequests - user.getNumberOfPullRequests())^2 +
				(centroid.numberOfPullRequestComments - user.getNumberOfPullRequestComments())^2 +
				(centroid.numberOfCommitComments - user.getNumberOfCommitComments())^2 +
				(centroid.numberOfForks - user.getNumberOfForks())^2
				);
		if (Double.isNaN(result)) result = 0;
		return result;
	}
	
	/**
	 * 
	 * @param old
	 * @return true if they match.
	 */
	protected boolean compareCentroids(List<Centroid> prev, List<Centroid> curr) {

		List<Integer> foundPrevs = new ArrayList<Integer>();
		
		for (Centroid c : curr) {
			boolean found = false;
			for (int i=0; i < prev.size(); i++) {
				if (foundPrevs.contains(i)) continue;
				Centroid d = prev.get(i);

				if (d.numberOfAdditions == c.numberOfAdditions &&
						d.numberOfChanges == c.numberOfChanges &&
						d.numberOfCommitComments == c.numberOfCommitComments &&
						d.numberOfCommits == c.numberOfCommits &&
						d.numberOfCommitsAsAuthor == c.numberOfCommitsAsAuthor &&
						d.numberOfCommitsAsCommitter == c.numberOfCommitsAsCommitter &&
						d.numberOfDeletions == c.numberOfDeletions &&
						d.numberOfForks == c.numberOfForks &&
						d.numberOfIssueComments == c.numberOfIssueComments &&
						d.numberOfIssues == c.numberOfIssues &&
						d.numberOfPullRequestComments == c.numberOfPullRequestComments &&
						d.numberOfPullRequests == c.numberOfPullRequests) {
					found = true;
					foundPrevs.add(i);
					break;
				}
			
			}
			if (!found) return false;
		}
		return true;
	}
	
	class Centroid {
		int numberOfCommits;
		int numberOfChanges;
		int numberOfAdditions;
		int numberOfDeletions;
		int numberOfCommitsAsAuthor;
		int numberOfCommitsAsCommitter;
		int numberOfIssues;
		int numberOfIssueComments;
		int numberOfPullRequests;
		int numberOfPullRequestComments;
		int numberOfCommitComments;
		int numberOfForks;
		
		@Override
		public String toString() {
			return "Centroid: [" +  numberOfCommits + ", " + numberOfChanges + ", " +  numberOfAdditions + ", " +  numberOfDeletions + ", " + 
					numberOfCommitsAsAuthor + ", " + numberOfCommitsAsCommitter + ", " +  numberOfIssues + ", " +  numberOfIssueComments + ", " + 
					numberOfPullRequests + ", " +  numberOfPullRequestComments + ", " +  numberOfCommitComments + ", " +  numberOfForks +"]";
		}
	}
}
