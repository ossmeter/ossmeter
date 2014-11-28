package org.ossmeter.repository.model.googlecode;

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
import org.ossmeter.repository.model.cc.wiki.*;
import org.ossmeter.repository.model.metrics.*;
import org.ossmeter.platform.factoids.*;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = GoogleDownload.class, name="org.ossmeter.repository.model.googlecode.GoogleDownload"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleDownload extends Object {

	protected List<GoogleLabel> labels;
	protected boolean starred;
	protected String fileName;
	protected String uploaded_at;
	protected String updated_at;
	protected String size;
	protected int downloadCounts;
	
	public boolean getStarred() {
		return starred;
	}
	public String getFileName() {
		return fileName;
	}
	public String getUploaded_at() {
		return uploaded_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public String getSize() {
		return size;
	}
	public int getDownloadCounts() {
		return downloadCounts;
	}
	
	public List<GoogleLabel> getLabels() {
		return labels;
	}
}
