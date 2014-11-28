package com.googlecode.pongo.tests.blog;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.tests.blog.model.Comment;
import com.googlecode.pongo.tests.blog.model.Flag;
import com.googlecode.pongo.tests.blog.model.Post;
import com.googlecode.pongo.tests.blog.model.PostType;

public class EnumerationsTests extends BlogTests {
	
	@Test
	public void testInitialEnumValue() {
		
		Post post = createPosts("Post1").get(0);
		
		assertEquals(null, post.getType());
	}

	@Test
	public void testPersistedEnumValue() {
		
		Post post = createPosts("Post1").get(0);
		post.setType(PostType.Sticky);
		blog.sync();
		
		post = blog.getPosts().first();
		assertEquals(PostType.Sticky, post.getType());
	}
	
	@Test
	public void testCommentFlags() {
		Post post = createPosts("Post").get(0);
		Comment comment = new Comment();
		comment.getFlags().add(Flag.Offensive);
		comment.getFlags().add(Flag.Spam);
		post.getComments().add(comment);
		
		blog.sync();
		
		
	}
	
}
