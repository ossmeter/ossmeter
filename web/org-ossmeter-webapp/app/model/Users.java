package model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class Users extends PongoDB {
	
	public Users() {}
	
	public Users(DB db) {
		setDb(db);
	}
	
	protected UserCollection users = null;
	protected TokenCollection tokens = null;
	
	
	
	public UserCollection getUsers() {
		return users;
	}
	
	public TokenCollection getTokens() {
		return tokens;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		users = new UserCollection(db.getCollection("users"));
		pongoCollections.add(users);
		tokens = new TokenCollection(db.getCollection("tokens"));
		pongoCollections.add(tokens);
	}
}