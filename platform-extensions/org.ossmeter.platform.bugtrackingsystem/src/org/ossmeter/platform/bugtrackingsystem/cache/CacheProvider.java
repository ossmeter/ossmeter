package org.ossmeter.platform.bugtrackingsystem.cache;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.ossmeter.repository.model.BugTrackingSystem;

public abstract class CacheProvider<T, K> {

	public abstract boolean changedOnDate(T item, Date date,
			BugTrackingSystem bugTracker);

	public abstract boolean changedSinceDate(T item, Date date,
			BugTrackingSystem bugTracker);

	public abstract K getKey(T item);

	public abstract void process(T item, BugTrackingSystem bugTracker);

	/**
	 * returns true if one of dates matches date.
	 * 
	 * @param date
	 * @param dates
	 * @return
	 */
	public static boolean findMatchOnDate(Date date, Date... dates) {
		for (Date d : dates) {
			if (null != d && DateUtils.isSameDay(date, d)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * returns true if one of dates occurred after date.
	 * 
	 * @param date
	 * @param dates
	 * @return
	 */
	public static boolean findMatchSinceDate(Date date, Date... dates) {
		for (Date d : dates) {
			if (null != d && d.after(date)) {
				return true;
			}
		}

		return false;
	}
}
