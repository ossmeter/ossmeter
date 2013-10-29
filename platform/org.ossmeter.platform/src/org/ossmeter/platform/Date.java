package org.ossmeter.platform;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Date {
	
	protected java.util.Date _date;
	
	public Date() {
		_date = new java.util.Date();
	}
	
	/**
	 * 
	 * @param date YYYMMDD
	 * @throws ParseException
	 */
	public Date(String date) throws ParseException {
		// YYYYMMDD
		DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		_date = formatter.parse(date);
	}
	
	public Date(long epoch) {
		this(new java.util.Date(epoch));
	}
	
	public Date(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		this._date = c.getTime();
	}
	
	/**
	 * @param days
	 * @return
	 */
	public Date addDays(int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(_date);
		c.add(Calendar.DATE, days);
		_date = c.getTime(); // necessary?
		return this;
	}
	
	public String toString() {
		DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(_date);
	}
	
	public java.util.Date toJavaDate() {
		return _date;
	}
	
	/*
	 * @return >0 means 'date' is in the past; <0 means 'date' is in the future;
	 * ==0 means 'date' is the same.
	 */
	public int compareTo(java.util.Date date) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(_date);
		
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);
		
		return c1.compareTo(c2);
	}
	
	public int compareTo(Date date) {
		return compareTo(date.toJavaDate());
	}
	
	/**
	 * @param start INCLUSIVE
	 * @param end INCLUSIVE
	 * @return
	 */
	public static Date[] range(Date start, Date end) {
		List<Date> dates = new ArrayList<Date>();
		
		Calendar c = Calendar.getInstance();
		c.setTime(start.toJavaDate());
		
		java.util.Date d = c.getTime();
		dates.add(new Date(d));
		
		while (d.before(end.toJavaDate())) {
			c.add(Calendar.DATE, 1);
			d = c.getTime();
			dates.add(new Date(d)); // Need to ensure this is returning a different object each time.
		}
		
		return dates.toArray(new Date[dates.size()]);
	}
	
	public static long duration(Date earlier, Date later) {
		
		if (earlier.compareTo(later) > 0)
			return duration(later, earlier);

        Calendar earlierCalendar = Calendar.getInstance();
        earlierCalendar.setTime(earlier.toJavaDate());

        Calendar laterCalendar   = Calendar.getInstance();
        laterCalendar.setTime(later.toJavaDate());

        return laterCalendar.getTimeInMillis() - earlierCalendar.getTimeInMillis();
	}
	
}
