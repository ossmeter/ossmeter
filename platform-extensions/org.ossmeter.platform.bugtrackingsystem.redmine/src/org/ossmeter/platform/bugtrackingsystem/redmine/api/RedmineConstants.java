/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.ossmeter.platform.bugtrackingsystem.redmine.api;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class RedmineConstants {

	// public static final DateTimeFormatter REQUEST_DATE_FORMATTER =
	// DateTimeFormat
	// .forPattern("YYYY-MM-dd");

	public static final int DEFAULT_PAGE_SIZE = 100;

	public static final DateTimeFormatter DATE_FORMATTER = ISODateTimeFormat
			.dateTimeNoMillis().withZoneUTC();
}
