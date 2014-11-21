package com.googlecode.pongo.tests.blog.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class Comment extends Pongo {
	
	protected List<Comment> replies = null;
	protected List<Member> liked = null;
	protected List<Member> disliked = null;
	protected List<Flag> flags = null;
	protected Author author = null;
	
	
	public Comment() { 
		super();
		dbObject.put("author", new BasicDBObject());
		dbObject.put("replies", new BasicDBList());
		dbObject.put("liked", new BasicDBList());
		dbObject.put("disliked", new BasicDBList());
		dbObject.put("flags", new BasicDBList());
	}
	
	public String getText() {
		return parseString(dbObject.get("text")+"", "");
	}
	
	public Comment setText(String text) {
		dbObject.put("text", text + "");
		notifyChanged();
		return this;
	}
	
	public List<Flag> getFlags() {
		if (flags == null) {
			flags = new PrimitiveList<Flag>(this, (BasicDBList) dbObject.get("flags"));
		}
		return flags;
	}
	
	public List<Comment> getReplies() {
		if (replies == null) {
			replies = new PongoList<Comment>(this, "replies", true);
		}
		return replies;
	}
	public List<Member> getLiked() {
		if (liked == null) {
			liked = new PongoList<Member>(this, "liked", false);
		}
		return liked;
	}
	public List<Member> getDisliked() {
		if (disliked == null) {
			disliked = new PongoList<Member>(this, "disliked", false);
		}
		return disliked;
	}
	
	public Comment setAuthor(Author author) {
		if (this.author != author) {
			if (author == null) {
				dbObject.put("author", new BasicDBObject());
			}
			else {
				createReference("author", author);
			}
			this.author = author;
			notifyChanged();
		}
		return this;
	}
	
	public Author getAuthor() {
		if (author == null) {
			author = (Author) resolveReference("author");
		}
		return author;
	}
	
}