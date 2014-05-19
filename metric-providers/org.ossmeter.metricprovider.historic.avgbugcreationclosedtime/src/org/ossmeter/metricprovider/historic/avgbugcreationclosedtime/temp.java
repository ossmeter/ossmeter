package org.ossmeter.metricprovider.historic.avgbugcreationclosedtime;

import org.apache.commons.lang.time.DurationFormatUtils;

public class temp {

	private static final long SECONDS_DAY = 24 * 60 * 60;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long seconds = 27734400;
		long durations = 126;
		long avgDuration = seconds / durations;
		int days = (int) (avgDuration / SECONDS_DAY);
		String formatted = DurationFormatUtils.formatDuration(avgDuration % SECONDS_DAY, "HH:mm:ss:SS");
		System.out.println(seconds + " / " + durations + " = " + avgDuration);
		System.out.println(avgDuration + " / " + SECONDS_DAY + " = " + days);
		System.out.println(avgDuration + " % " + SECONDS_DAY + " = " + avgDuration % SECONDS_DAY);
		System.out.println(days + ":" + formatted);
	}

}
