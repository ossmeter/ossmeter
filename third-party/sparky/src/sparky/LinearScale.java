package sparky;
public class LinearScale extends Scale<Double> {
		
	// Domain is data
	// Range is canvas
	public LinearScale(double maxDomain, double minDomain, double maxRange, double minRange) {
		super(maxDomain, minDomain, maxRange, minRange);
	}
	
	/* (non-Javadoc)
	 * @see sparky.Scale#scale(double)
	 */
	@Override
	public int scale(Double value) {
		return (int)(((value-minDomain)/(maxDomain-minDomain)) * (maxRange-minRange) + minRange);
	}
}

//((15-10)/10)*5+10