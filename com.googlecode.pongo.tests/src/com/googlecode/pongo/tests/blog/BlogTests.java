package com.googlecode.pongo.tests.blog;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.tests.blog.model.AuthorCollection;
import com.googlecode.pongo.tests.blog.model.Blog;
import com.googlecode.pongo.tests.blog.model.MemberCollection;
import com.googlecode.pongo.tests.blog.model.Post;
import com.googlecode.pongo.tests.blog.model.PostCollection;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class BlogTests {

	protected PostCollection posts = null;
	protected AuthorCollection authors = null;
	protected MemberCollection members = null;
	protected Blog blog = null;
	protected Mongo mongo = null;
	
	@Before
	public void setup() throws Exception {
		if (mongo == null) {
			mongo = new Mongo();
		}
		
		mongo.dropDatabase("blog");
		blog = new Blog(mongo.getDB("blog"));
		blog.setClearPongoCacheOnSync(true);
		
		posts = blog.getPosts();
		authors = blog.getAuthors();
		members = blog.getMembers();
		
	}
		
	@After
	public void teardown() {
		PongoFactory.getInstance().clear();
	}
	
	protected List<Post> createPosts(String... titles) {
		ArrayList<Post> posts = new ArrayList<Post>();
		for (String title : titles) {
			Post post = new Post();
			post.setTitle(title);
			blog.getPosts().add(post);
			posts.add(post);
		}
		return posts;
	}
	
}
