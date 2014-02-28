package org.ossmeter.platform.osgi.old;

import java.io.Serializable;

public class Report implements Serializable {

	private static final long serialVersionUID = -4378391871759087188L;
	
	final protected String project;
	final protected ReportKind reportKind;
	final protected String message;
	
	public Report(String project, ReportKind reportKind, String message) {
		this.project = project;
		this.reportKind = reportKind;
		this.message = message;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getProject() {
		return project;
	}

	public ReportKind getReportKind() {
		return reportKind;
	}

	public String getMessage() {
		return message;
	}
}
