package com.googlecode.pongo.tests.blog;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.googlecode.pongo.tests.blog.model.Comment;
import com.googlecode.pongo.tests.blog.model.Post;

public class MultiValuedContainmentReferencesTests extends BlogTests {

	@Test
	public void testNoDuplicatesInContainmentReference() {
		Post post = new Post();
		Comment comment = new Comment();
		post.getComments().add(comment);
		post.getComments().add(comment);
		assertEquals(post.getComments().size(), 1);
	}
	
	@Test
	public void testAddMultipleToContainmentReference() {
		Post post = new Post();
		post.getComments().add(new Comment());
		post.getComments().add(new Comment());
		assertEquals(post.getComments().size(), 2);
	}

}
