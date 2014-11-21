package com.googlecode.pongo.tests.blog;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.tests.blog.model.Comment;
import com.googlecode.pongo.tests.blog.model.Post;

public class SingleValuedAttributesTests extends BlogTests {

	@Test
	public void testPostTitleChanged() {
		Post post = new Post();
		post.setTitle("Before");
		blog.getPosts().add(post);
		blog.sync();
		
		post.setTitle("After");
		blog.sync();
		
		assertEquals("After", posts.first().getTitle());
	}
	
	@Test
	public void testCommentTextChanged() {
		Post post = new Post();

		Comment comment = new Comment();
		comment.setText("Before");
		post.getComments().add(comment);
		
		blog.getPosts().add(post);
		blog.sync();
		
		comment.setText("After");
		blog.sync();
		
		assertEquals("After", posts.first().getComments().get(0).getText());
	}
	
	
}
