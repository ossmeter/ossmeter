package com.googlecode.pongo.tests.blog;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.tests.blog.model.Post;

public class FindByTests extends BlogTests {
	
	@Test
	public void testFindOneBy() {
		
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
