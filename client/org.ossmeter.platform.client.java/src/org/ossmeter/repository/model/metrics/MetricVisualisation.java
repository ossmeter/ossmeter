package org.ossmeter.repository.model.metrics;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import org.ossmeter.repository.model.redmine.*;
import org.ossmeter.repository.model.vcs.svn.*;
import org.ossmeter.repository.model.cc.forum.*;
import org.ossmeter.repository.model.bts.bugzilla.*;
import org.ossmeter.repository.model.cc.nntp.*;
import org.ossmeter.repository.model.vcs.cvs.*;
import org.ossmeter.repository.model.eclipse.*;
import org.ossmeter.repository.model.googlecode.*;
import org.ossmeter.repository.model.vcs.git.*;
import org.ossmeter.repository.model.sourceforge.*;
import org.ossmeter.repository.model.github.*;
import org.ossmeter.repository.model.*;
import org.ossmeter.repository.model.metrics.*;
import org.ossmeter.platform.factoids.*;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = MetricVisualisation.class, name="org.ossmeter.repository.model.metrics.MetricVisualisation"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetricVisualisation extends Object {

	protected String _id;
	protected String type;
	protected String description;
	protected String x;
	protected String y;
	protected Datatable datatable;
	
	public String get_id() {
		return _id;
	}
	public String getType() {
		return type;
	}
	public String getDescription() {
		return description;
	}
	public String getX() {
		return x;
	}
	public String getY() {
		return y;
	}
	
}
