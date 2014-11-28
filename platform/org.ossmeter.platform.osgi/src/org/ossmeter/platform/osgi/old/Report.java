/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
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
