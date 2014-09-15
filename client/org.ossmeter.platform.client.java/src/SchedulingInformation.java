//package model;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = SchedulingInformation.class, name="SchedulingInformation"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class SchedulingInformation extends Object {

	protected List<String> currentLoad;
	protected String workerIdentifier;
	protected boolean isMaster;
	
	public String getWorkerIdentifier() {
		return workerIdentifier;
	}
	public boolean getIsMaster() {
		return isMaster;
	}
	
	public List<String> getCurrentLoad() {
		return currentLoad;
	}
}
