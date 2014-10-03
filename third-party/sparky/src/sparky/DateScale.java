package sparky;

import java.util.Date;

public class DateScale extends Scale<Date> {
		
	
	protected double domainLength;
	
	// Domain is data
	// Range is canvas
	public DateScale(Date maxDomain, Date minDomain, double maxRange, double minRange) {
		super(maxDomain, minDomain, maxRange, minRange);
		
		domainLength = daysBetween(minDomain, maxDomain) ;
	}
	
	/* (non-Javadoc)
	 * @see sparky.Scale#scale(double)
	 */
	@Override
	public int scale(Date value) {
		return (int)(
				(daysBetween(minDomain, value) / domainLength) 
				* (maxRange-minRange) 
					+ minRange);
	}
	
	final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
	protected int daysBetween(Date earlier, Date later) {
		return (int) ((later.getTime() - earlier.getTime())/ DAY_IN_MILLIS );
	}
}