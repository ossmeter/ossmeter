package com.googlecode.pongo.tests.querying;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.googlecode.pongo.tests.blog.BlogTests;
import com.googlecode.pongo.tests.blog.model.AuthorCollection;
import com.googlecode.pongo.tests.blog.model.Blog;
import com.googlecode.pongo.tests.blog.model.MemberCollection;
import com.googlecode.pongo.tests.blog.model.PostCollection;
import com.mongodb.Mongo;

public class FindByTests extends BlogTests {
	
	PostCollection posts = null;
	AuthorCollection authors = null;
	MemberCollection members = null;
	Blog blog = null;
	Mongo mongo = null;
	
	@Test
	public void testFind() {
		
		createPosts("Post1", "Post2");
		blog.sync();
		
		assertEquals("Post1", blog.getPosts().findOneByTitle("Post1").getTitle());
	}
	
	@Test
	public void testSize() {
		
		createPosts("Post1", "Post2");
		blog.sync();
		assertEquals(2, blog.getPosts().size());
	}
	
	@Test
	public void testCountBy() {
		
		createPosts("Post1", "Post2", "Post1");
		blog.sync();
		assertEquals(2, blog.getPosts().countByTitle("Post1"));
	}
	
	
}
