package org.ossmeter.platform.bugtrackingsystem.redmine.api;


import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class RedmineSearchResult {
	@JsonProperty("total_count")
	private Integer totalCount;
	private Integer offset;
	private Integer limit;

	public Integer getTotalCount() {
		return totalCount;
	}

	public Integer getOffset() {
		return offset;
	}

	public Integer getLimit() {
		return limit;
	}
	
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
