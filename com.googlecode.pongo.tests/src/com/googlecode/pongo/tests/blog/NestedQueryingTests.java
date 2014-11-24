package com.googlecode.pongo.tests.blog;

import org.junit.Test;

import com.googlecode.pongo.tests.blog.model.Comment;
import com.googlecode.pongo.tests.blog.model.Post;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import static org.junit.Assert.*;

public class NestedQueryingTests extends BlogTests {
	
	
	@Test
	public void testFindByMatchingComment() {
		
		Post post1 = new Post();
		blog.getPosts().add(post1);
		
		Comment comment1 = new Comment();
		comment1.setText("comment");
		post1.getComments().add(comment1);
		
		blog.sync();
		
		DBObject matchingQueryDbObject = new BasicDBObject("comments.text", "comment");
		assertTrue(blog.getPosts().getDbCollection().find(matchingQueryDbObject).hasNext());
		
	}
	
	@Test
	public void testFindByNonMatchingComment() {
		
		Post post1 = new Post();
		blog.getPosts().add(post1);
		
		Comment comment1 = new Comment();
		comment1.setText("comment");
		post1.getComments().add(comment1);
		
		blog.sync();
		
		DBObject nonMatchingQueryDbObject = new BasicDBObject("comments.text", "unknown-comment");
		assertFalse(blog.getPosts().getDbCollection().find(nonMatchingQueryDbObject).hasNext());
		
	}
}
