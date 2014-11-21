package com.googlecode.pongo.tests.blog;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.tests.blog.model.Author;
import com.googlecode.pongo.tests.blog.model.Comment;
import com.googlecode.pongo.tests.blog.model.Post;
import com.googlecode.pongo.tests.blog.model.Stats;

public class UnsupportedFeaturesTests extends BlogTests {
	
	@Test
	public void testMoveCommentWithoutSync() {
		
		// When a comment is moved from a post to another
		// it should be removed by the first post (val reference)
		
		Post post1 = new Post();
		Post post2 = new Post();
		
		Comment comment = new Comment();
		
		post1.getComments().add(comment);
		post2.getComments().add(comment);
		
		assertEquals(0, post1.getComments().size());
		assertEquals(1, post2.getComments().size());
		
	}

}
