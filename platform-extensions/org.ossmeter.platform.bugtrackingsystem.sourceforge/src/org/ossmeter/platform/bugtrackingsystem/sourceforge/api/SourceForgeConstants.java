package org.ossmeter.platform.bugtrackingsystem.sourceforge.api;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;
import org.joda.time.format.ISODateTimeFormat;

class SourceForgeConstants {
    public static final int DEFAULT_PAGE_SIZE = 100;

    public static final DateTimeFormatter REQUEST_DATE_FORMATTER = ISODateTimeFormat
            .dateTime();

    public static final DateTimeFormatter RESPONSE_DATE_FORMATTER;

    static {

        final DateTimeParser optional = new DateTimeFormatterBuilder()
                .appendLiteral('.').appendFractionOfSecond(1, 6).toParser();

        RESPONSE_DATE_FORMATTER = new DateTimeFormatterBuilder()
                .appendYear(4, 4).appendLiteral('-').appendMonthOfYear(2)
                .appendLiteral('-').appendDayOfMonth(2).appendLiteral(' ')
                .appendHourOfDay(2).appendLiteral(':').appendMinuteOfHour(2)
                .appendLiteral(':').appendSecondOfMinute(2)
                .appendOptional(optional).toFormatter();
    }

    public static final String LAST_MODIFIED_QUERY_FIELD = "mod_date_dt";
    
    public static final String CREATED_DATE_QUERY_FIELD = "created_date";
}
