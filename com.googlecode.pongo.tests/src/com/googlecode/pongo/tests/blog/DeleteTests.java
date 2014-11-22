package com.googlecode.pongo.tests.blog;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.tests.blog.model.Post;

public class DeleteTests extends BlogTests {
	
	
	@Test
	public void createAndDeletePost() {
		
		Post post1 = new Post();
		post1.setTitle("Post1");
		blog.getPosts().add(post1);
		
		Post post2 = new Post();
		post2.setTitle("Post2");
		blog.getPosts().add(post2);
		
		blog.sync();
		
		blog.getPosts().remove(post1);
		
		blog.sync();
		
		assertEquals(1, blog.getPosts().size());
		assertEquals("Post2", blog.getPosts().first().getTitle());
		
	}
	
	@Test
	public void modifyAndDeletePost() {
		
		Post post1 = new Post();
		post1.setTitle("Post1");
		blog.getPosts().add(post1);
		
		Post post2 = new Post();
		post2.setTitle("Post2");
		blog.getPosts().add(post2);
		
		blog.sync();
		
		Post postToRename = blog.getPosts().first();
		// Change the title of the first post to Post3
		postToRename.setTitle("Post3");
		blog.getPosts().remove(postToRename);
		System.err.println(blog.getPosts().getToDelete().size());
		blog.sync();
		
		assertEquals(1, blog.getPosts().size());
		assertFalse(blog.getPosts().first().getTitle().equals(postToRename.getTitle()));
	}
	
}
