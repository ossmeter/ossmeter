package com.googlecode.pongo.tests.blog;

import org.junit.Test;

import com.googlecode.pongo.tests.blog.model.Author;
import com.googlecode.pongo.tests.blog.model.Comment;
import com.googlecode.pongo.tests.blog.model.Post;

public class CompositeTests extends BlogTests {
	
	@Test
	public void testPostsAuthorsAndComments() {
		
	Post post = new Post();
	post.setTitle("A post");
	
	Author author = new Author();
	author.setName("Joe Doe");
	authors.add(author);
	post.setAuthor(author);
	
	Comment comment = new Comment();
	comment.setText("A comment");
	post.getComments().add(comment);
	
	Comment reply = new Comment();
	reply.setText("A reply");
	comment.getReplies().add(reply);
	
	posts.add(post);
	blog.sync();
	
	}
	
}
