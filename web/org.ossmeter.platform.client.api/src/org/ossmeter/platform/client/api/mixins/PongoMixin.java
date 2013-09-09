package org.ossmeter.platform.client.api.mixins;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoCollection;
import com.mongodb.DBObject;

@JsonAutoDetect( fieldVisibility = JsonAutoDetect.Visibility.NONE, 
				getterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY, 
				setterVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class PongoMixin  {

	@JsonIgnore
	abstract DBObject getDbObject();
	
	@JsonIgnore
	abstract String getId();
	
	@JsonIgnore
	abstract String getType();
	
	@JsonIgnore
	abstract Pongo getContainer();

	@JsonIgnore
	abstract String getContainingFeature();
	
	@JsonIgnore
	abstract PongoCollection getPongoCollection();
	
	@JsonIgnore
	abstract String getPongoPath();
	
	@JsonIgnore
	abstract boolean isReferencable();
}