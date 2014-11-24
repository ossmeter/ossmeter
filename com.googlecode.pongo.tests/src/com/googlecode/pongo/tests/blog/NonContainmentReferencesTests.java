package com.googlecode.pongo.tests.blog;

import org.junit.Test;

import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.tests.blog.model.Author;
import com.googlecode.pongo.tests.blog.model.Comment;
import com.googlecode.pongo.tests.blog.model.Member;
import com.googlecode.pongo.tests.blog.model.Post;
import com.googlecode.pongo.tests.blog.model.Stats;

import static junit.framework.Assert.*;

public class NonContainmentReferencesTests extends BlogTests {
	
	@Test(expected=IllegalStateException.class)
	public void testNonReferenceable() {
		Post post = new Post();
		post.setAuthor(new Author());
	}
	
	@Test
	public void testReferenceable() {
		Post post = new Post();
		Author author = new Author();
		blog.getAuthors().add(author);
		
		post.setAuthor(author);
		assertEquals(post.getAuthor(), author);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testNonReferenceableInManyReference() {
		
		Comment comment = new Comment();
		Member member = new Member();
		
		comment.getLiked().add(member);
		
	}
	
	@Test
	public void testReferencealbeInManyReference() {
		Post post = new Post();
		Comment comment = new Comment();
		post.getComments().add(comment);
		
		Member member = new Member();
		blog.getMembers().add(member);
		comment.getLiked().add(member);
		blog.getPosts().add(post);
		
		assertEquals(1, comment.getLiked().size());
		
	}
		
	@Test
	public void testUnsetNonContainmentFeature() {
		Post post = new Post();
		Author author = new Author();
		blog.getAuthors().add(author);
		post.setAuthor(author);
		post.setAuthor(null);
		assertEquals(null, post.getAuthor());
	}
	
	@Test
	public void testSyncAuthorChange() {
		
		Post post = new Post();
		Author author1 = new Author();
		author1.setName("Before");
		blog.getAuthors().add(author1);
		post.setAuthor(author1);
		blog.getPosts().add(post);
		
		blog.sync();
		
		Author author2 = new Author();
		author2.setName("After");
		blog.getAuthors().add(author2);
		post.setAuthor(author2);
		
		blog.sync();
		
		assertEquals("After", blog.getPosts().first().getAuthor().getName());
	}
	
}
