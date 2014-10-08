package org.ossmeter.platform.bugtrackingsystem.bitbucket.api;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class BitbucketConstants {
	public static int DEFAULT_PAGE_SIZE = 50;
	public static int MAX_PAGE_SIZE = 50;
	
	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat
			.forPattern("YYYY-MM-dd HH:mm:ssZ");
}
