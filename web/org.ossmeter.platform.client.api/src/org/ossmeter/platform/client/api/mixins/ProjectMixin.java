package org.ossmeter.platform.client.api.mixins;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoCollection;
import com.mongodb.DBObject;

public abstract class ProjectMixin {
	@JsonIgnore DBObject dbObject;
	@JsonIgnore Pongo container;
	@JsonIgnore String containingFeature;
	@JsonIgnore PongoCollection pongoCollection;
}
