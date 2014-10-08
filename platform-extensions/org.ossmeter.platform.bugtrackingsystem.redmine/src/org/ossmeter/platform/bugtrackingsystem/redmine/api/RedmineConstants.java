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
